/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author abenkovic
 */
public class ToF {

    public ToF(ToFBuilder builder) {
        System.out.println("Kreiram ToF");
    }

    public static class ToFBuilder {

        HashMap<String, Mjesto> mjesta = new HashMap<>();
        ArrayList<Uredjaj> senzori = new ArrayList<>();
        ArrayList<Uredjaj> aktuatori = new ArrayList<>();
        String[] args = null;

        public ToFBuilder(String[] args) {
            this.args = args;
        }

        

        public ToFBuilder postaviUredjaje() {
            AbstractFactory factory = new ToFFactory();
            this.senzori = factory.kreirajUredjaje(this.args[2], true);
            this.aktuatori = factory.kreirajUredjaje(this.args[3], false);
            this.mjesta = factory.kreirajMjesta(this.args[1]);

            //dva mjesta ne mogu se nikako pojaviti zbog HashMape
            //for (String nazivMjesta : this.podaci.get("mjesta").keySet()) {
                //this.mjesta.put(nazivMjesta, factory.kreirajMjesto(nazivMjesta));
            //}
            return this;
        }

        public ToFBuilder inicijalizacija() {
            System.out.println("Inicijalizacija");

            return this;
        }

        public ToF build() {
            return new ToF(this);
        }

    }
}
