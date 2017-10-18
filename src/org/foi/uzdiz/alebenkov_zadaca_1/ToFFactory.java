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

    HashMap<String, String[]> mjesta;
    HashMap<String, String[]> senzori;
    HashMap<String, String[]> aktuatori;

    public ToFFactory(HashMap<String, HashMap<String, String[]>> podaci) {
        System.out.println("Konstruktor ToF Factory-a");

        this.mjesta = (HashMap<String, String[]>) podaci.get("mjesta").clone();
        this.senzori = podaci.get("senzori");
        this.aktuatori = podaci.get("aktuatori");
    }

    @Override
    public Mjesto kreirajMjesto(String nazivMjesta) {
        Mjesto mjesto = null;
        System.out.println("Builder: Kreiram mjesto " + nazivMjesta);
        String[] podaciOmjestu = this.mjesta.get(nazivMjesta);
        mjesto = new Mjesto(nazivMjesta, Integer.parseInt(podaciOmjestu[1]), Integer.parseInt(podaciOmjestu[2]), Integer.parseInt(podaciOmjestu[3]));
        for (int i = 0; i < mjesto.brojSenzora; i++) {
            mjesto.setSenzor(kreirajSenzor(mjesto.tip));
        }
        return mjesto;
    }

    @Override
    public Senzor kreirajSenzor(int tip) {
        for (String[] value: this.senzori.values()){
            if(Integer.parseInt(value[1]) == tip || Integer.parseInt(value[1]) == 2){
                return new Senzor(value[0], Integer.parseInt(value[1]), Integer.parseInt(value[2]), Float.parseFloat(value[3]), Float.parseFloat(value[4]),value[5]);
            }
        }
        return null;
    }

    @Override
    public Uredjaj kreirajAktuator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
