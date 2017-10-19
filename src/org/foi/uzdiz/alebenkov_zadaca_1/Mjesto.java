/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author abenkovic
 */
public class Mjesto {

    public String naziv;
    public int tip;
    public int brojSenzora;
    public int brojAktuatora;
    public ArrayList<Senzor> senzori = new ArrayList<>();
    public ArrayList<Aktuator> aktuatori = new ArrayList<>();

    public Mjesto(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
    }

    public void setSenzor(Senzor senzor) {
        this.senzori.add(senzor);
    }

    public void setAktuator(Aktuator aktuator) {
        this.aktuatori.add(aktuator);
    }
    
    public void removeSenzor(Senzor senzor){
        System.out.println("Brisem " + senzor.naziv);
        this.senzori.remove(senzor);
    }    
    
    public void removeAktuator(Aktuator aktuator){
        this.senzori.remove(aktuator);
    } 

    public void makniOnemogucene() {
        for (Iterator<Senzor> iterator = senzori.iterator(); iterator.hasNext();) {
            Senzor next = iterator.next();
            if (next.onemogucen) {
                System.out.println("Brišem " + next.naziv);
                iterator.remove();
            }

        }

        for (Iterator<Aktuator> iterator = aktuatori.iterator(); iterator.hasNext();) {
            Aktuator next = iterator.next();
            if (next.onemogucen) {
                System.out.println("Brišem " + next.naziv);
                iterator.remove();
            }

        }
    }

}
