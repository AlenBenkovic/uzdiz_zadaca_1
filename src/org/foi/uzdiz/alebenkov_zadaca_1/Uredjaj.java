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
public abstract class Uredjaj {

    public String naziv;
    public int tip;
    public int vrsta;
    public float min;
    public float max;
    public String komentar;

    public boolean onemogucen = false;
    public int brojProvjera = 0;

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

    public boolean getStatus() {
        double random = new Random(540).nextDouble();
        if (random < 0.9) {
            return true;
        } else {
            return false;
        }
    }

}
