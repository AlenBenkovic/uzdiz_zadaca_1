/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.Random;
import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;

/**
 *
 * @author abenkovic
 */
public abstract class Uredjaj implements Cloneable {

    public String naziv;
    public int tip;
    public int vrsta;
    public float min;
    public float max;
    public String komentar;

    public boolean onemogucen = false;
    public int neuspjesneProvjere = 0;

    FoiLogger logs = FoiLogger.getInstance();

    public Uredjaj(String naziv, int tip, int vrsta, float min, float max, String komentar) {
        this.naziv = naziv;
        this.tip = tip;
        this.vrsta = vrsta;
        this.min = min;
        this.max = max;
        this.komentar = komentar;
    }

    public boolean isOnemogucen() {
        return onemogucen;
    }

    public void setOnemogucen(boolean onemogucen) {
        this.onemogucen = onemogucen;
    }

    public boolean getStatus() {
        Random rn = new Random();
        int random = rn.nextInt(101);
        if (random >= 90) {
            this.neuspjesneProvjere++;

        }
        return random < 90;
    }

    public boolean inicijalizacija() {
        this.logs.log("Inicijaliziram uređaj " + this.naziv, "info");
        this.onemogucen = false;
        this.neuspjesneProvjere = 0;
        Random rn = new Random();
        int random = rn.nextInt(101);
        return random < 90;
    }

    @Override
    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
        }

        return clone;
    }

}
