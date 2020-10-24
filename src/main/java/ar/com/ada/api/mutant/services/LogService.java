package ar.com.ada.api.mutant.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Async
    public void logDebug(String registro) {
        Date fecha = new Date();

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss.S");

        String dateStr = formatter.format(fecha);

        System.out.println(dateStr + " - T# " + Thread.currentThread().getId() + " -" + registro);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
