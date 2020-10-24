package ar.com.ada.api.mutant.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.entities.Mutant;
import ar.com.ada.api.mutant.models.request.SampleRequest;
import ar.com.ada.api.mutant.models.response.GenericResponse;
import ar.com.ada.api.mutant.models.response.StatsResponse;
import ar.com.ada.api.mutant.services.LogService;
import ar.com.ada.api.mutant.services.MutantService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ea.async.Async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MutantController {

    @Autowired
    MutantService mutantService;

    @Autowired
    LogService logService;

    @PostMapping("/mutant")
    public ResponseEntity<?> createMutant(@RequestBody SampleRequest req) {

        GenericResponse gr = new GenericResponse();
        if (!this.mutantService.isValid(req.dna)) {
            gr.isOk = false;
            gr.message = "Invalid format";
            return ResponseEntity.badRequest().body(gr);
        }

        if (this.mutantService.exists(req.dna)) {
            gr.isOk = false;
            gr.message = "Already registered";
            return ResponseEntity.badRequest().body(gr);
        }

        Mutant mutant = this.mutantService.registerSample(req.dna, req.name);

        if (mutant != null) {
            gr.isOk = true;
            gr.message = "Hello mutant!";
            return ResponseEntity.ok(gr);
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    static {
        Async.init();
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() throws InterruptedException, ExecutionException {
        this.logService.logDebug("Entrando al controller");
        StatsResponse stats = new StatsResponse();

        this.logService.logDebug("Contando Humanos");

        CompletableFuture<Long> countHumanDNAResult = CompletableFuture
                .supplyAsync(() -> this.mutantService.countHumans());
        while (!countHumanDNAResult.isDone()) {
            System.out.println("Esperando terminar de contar humanos");
            Thread.sleep(1000); //NUNCA PONER ESTO DE VERDAD EN UNA WEB
        }

        stats.countHumanDNA = (long) (countHumanDNAResult.get()); //this.mutantService.countHumans();

        System.out.println("Soy el Thread del controller " + Thread.currentThread().getId());
        System.out.println("Cantidad de humanos " + stats.countHumanDNA);
        this.logService.logDebug("Imprimiendo Fruta");

        this.mutantService.imprimirComoVamos();

        this.logService.logDebug("Contando Mutantes");

        //Este en en forma sincronica
        stats.countMutantDNA = this.mutantService.countMutants();

        //Asincronico
        Future<Long> countMutanDNAResult = this.mutantService.countMutantsAsync();

        while (!countMutanDNAResult.isDone()) {
            System.out.println("Esperando terminar de contar humanos");
            Thread.sleep(1000); //NUNCA PONER ESTO DE VERDAD EN UNA WEB
        }

        stats.countMutantDNA = (long) (countMutanDNAResult.get());

        System.out.println("Cantidad de mutantes " + stats.countMutantDNA);

        CompletableFuture<Long> countTotalDNAResult = CompletableFuture
                .supplyAsync(() -> this.mutantService.countAll());

        stats.totalCount = Async.await(countTotalDNAResult);

        System.out.println("Cantidad Total " + stats.totalCount);

        stats.ratio = stats.countMutantDNA * 1.00d / stats.countHumanDNA * 1.00d;

        //Espero estos segundos para que terminen los otros metodos de impresion
        //para demostracion
        Thread.sleep(10000); //NUNCA PONER ESTO DE VERDAD EN UNA WEB

        int ratio = (int) (stats.ratio * 100);
        stats.ratio = ratio * 1.0d / 100;

        logService.logDebug("Enviando data al cliente");
        return ResponseEntity.ok(stats);
    }

}
