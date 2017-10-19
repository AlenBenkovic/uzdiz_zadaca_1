/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.Random;

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
    public int brojProvjera = 0;

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

    public int getBrojProvjera() {
        return brojProvjera;
    }

    public void setBrojProvjera(int brojProvjera) {
        this.brojProvjera = brojProvjera;
    }

    public boolean inicijalizacija() {
        Random rn = new Random();
        int random = rn.nextInt(101);
        if (random < 90) {
            return true;
        } else {
            return false;
        }
    }

    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }

}
