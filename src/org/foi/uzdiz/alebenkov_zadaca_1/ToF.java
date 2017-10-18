/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        HashMap<String, HashMap<String, String[]>> podaci = new HashMap<>();
        HashMap<String, Mjesto> mjesta = new HashMap<>();

        public ToFBuilder(String[] args) {
            System.out.println("Konstruktor ToF buildera");
            System.out.println("Ucitavam senzore...");
            this.podaci.put("senzori", this.ucitajPodatke(args[2]));

            System.out.println("Ucitavam aktuatore...");
            this.podaci.put("aktuatori", this.ucitajPodatke(args[3]));

            System.out.println("Ucitavam mjesta...");
            this.podaci.put("mjesta", this.ucitajPodatke(args[1]));

        }

        private HashMap<String, String[]> ucitajPodatke(String lokacija) {
            try {
                FileReader fr = new FileReader(lokacija);
                BufferedReader br = new BufferedReader(fr);
                HashMap<String, String[]> podaci = new HashMap<>();
                String s;
                int brojAtributa = 0;
                while ((s = br.readLine()) != null) {
                    String[] podatak = s.trim().split(";");
                    if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa
                        brojAtributa = podatak.length;
                    } else if (podatak.length == brojAtributa) {
                        podaci.put(podatak[0], podatak);

                    } else {
                        //ne valja ispisi poruku i spremi
                    }
                }
                return podaci;
            } catch (IOException e) {
                System.out.println("Greska prilikom citanja datoteke: " + e.toString());
                return null;
            }
        }

        public ToFBuilder postaviUredjaje() {

            AbstractFactory factory = new ToFFactory(this.podaci);
            //dva mjesta ne mogu se nikako pojaviti zbog HashMape
            for (String nazivMjesta: this.podaci.get("mjesta").keySet()){
                this.mjesta.put(nazivMjesta, factory.kreirajMjesto(nazivMjesta));
            }
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
