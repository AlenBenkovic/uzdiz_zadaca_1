/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.HashMap;

/**
 *
 * @author abenkovic
 */
public class Mjesto {
    private String naziv;
    private int tip;
    private int brojSenzora;
    private int brojAktuatora;
    private HashMap<String, Senzor> senzor;
    private HashMap<String, Aktuator> aktuator;

    public Mjesto(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
    }

    public Senzor getSenzor(String naziv) {
        return senzor.get(naziv);
    }

    public Aktuator getAktuator(String naziv) {
        return aktuator.get(naziv);
    }

    public void setSenzor(Senzor senzor) {
        this.senzor.put(senzor.naziv, senzor);
    }

    public void setAktuator(Aktuator aktuator) {
        this.aktuator.put(aktuator.naziv, aktuator);
    }
        
    

}
