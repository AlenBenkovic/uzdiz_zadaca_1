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
public class ToFFactory implements AbstractFactory {

    HashMap<String, HashMap<String, String[]>> podaci;

    public ToFFactory(HashMap<String, HashMap<String, String[]>> podaci) {
        this.podaci = podaci;
        System.out.println("Konstruktor ToF Factory-a");
    }

    @Override
    public Mjesto kreirajMjesto(String nazivMjesta) {
        System.out.println("Builder: Kreiram mjesto " + nazivMjesta);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public Uredjaj kreirajSenzor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Uredjaj kreirajAktuator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
