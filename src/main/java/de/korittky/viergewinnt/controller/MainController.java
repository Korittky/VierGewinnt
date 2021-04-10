package de.korittky.viergewinnt.controller;

import de.korittky.viergewinnt.KI;
import de.korittky.viergewinnt.model.Brett;
import de.korittky.viergewinnt.model.Farbe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private Brett brett;


    @GetMapping({"/init", "/"})
    public ModelAndView init() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("brett");
        brett = new Brett();
        mv.getModel().put("brett", brett.getModel());
        mv.getModel().put("naechsteFarbe", brett.getAktuelleFarbe());
        mv.getModel().put("score", brett.getScore());
        mv.getModel().put("zuege", brett.getZuege());

        return mv;
    }

    @GetMapping("/replay/{slots}")
    public ModelAndView replay(@PathVariable int[] slots) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("brett");
        brett = new Brett();
        for (int i : slots) {
            brett.spieleZug(i);
            brett.naechsterSpieler();
        }
        mv.getModel().put("brett", brett.getModel());
        mv.getModel().put("naechsteFarbe", brett.getAktuelleFarbe());
        mv.getModel().put("score", brett.getScore());
        mv.getModel().put("zuege", brett.getZuege());

        return mv;
    }

    @GetMapping("/wirf/{slot}")
    public ModelAndView wirf(@PathVariable int slot, ModelAndView modelMap) {
        ModelAndView mv = new ModelAndView();
        brett.spieleZug(slot);
        mv.setViewName("brett");
        mv.getModel().put("score", brett.getScore());
        mv.getModel().put("zuege", brett.getZuege());
        if (brett.pruefeGewinn(brett.getFarbe(),slot)) {
            mv.getModel().put("naechsteFarbe", "leer");
            mv.getModel().put("gewonnen", brett.getFarbe().name());
        } else {
            brett.naechsterSpieler();
            mv.getModel().put("naechsteFarbe", brett.getAktuelleFarbe());

            if (brett.getFarbe()==Farbe.gelb) { // Computerzug
                int bestSlot = KI.besterZug(brett);
                mv.getModel().put("score", brett.getScore());
                return wirf(bestSlot, modelMap);
            }

        }
        mv.getModel().put("brett", brett.getModel());

        return mv;
    }

}
