package ar.com.ada.api.mutant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.com.ada.api.mutant.entities.Mutant;
import ar.com.ada.api.mutant.services.MutantService;

@Controller
public class MutantMVCController {

    @Autowired
    MutantService mutantService;

    @GetMapping("/mutant/firstMutant")
    public String firstMutant(Model model) {
        Mutant m = mutantService.firstMutant();

        String view = "firstMutant";
        if (m == null) {
            view = "noMutants";
        } else {
            model.addAttribute("mutantName", m.getName());

            model.addAttribute("mutantUniqueHash", m.getUniqueHash());

            model.addAttribute("mutant", m);
        }

        return view;

    }

    //@GetMapping("/mutant/view?id={id}")
    //@GetMapping("/mutant/delete?id={id}")
    //@GetMapping("/mutant/modify?id={id}")
    //@GetMapping("/mutant/new")

    //@PostMapping("/mutant/modify?id={id}")

}
