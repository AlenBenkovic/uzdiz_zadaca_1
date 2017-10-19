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
    public String naziv;
    public int tip;
    public int brojSenzora;
    public int brojAktuatora;
    public HashMap<String, Senzor> senzori = new HashMap<>();
    public HashMap<String, Aktuator> aktuatori = new HashMap<>();

    public Mjesto(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
    }

    public Senzor getSenzor(String naziv) {
        return senzori.get(naziv);
    }

    public Aktuator getAktuator(String naziv) {
        return aktuatori.get(naziv);
    }

    public void setSenzor(Senzor senzor) {
        this.senzori.put(senzor.naziv, senzor);
    }

    public void setAktuator(Aktuator aktuator) {
        this.aktuatori.put(aktuator.naziv, aktuator);
    }
           
    

}
