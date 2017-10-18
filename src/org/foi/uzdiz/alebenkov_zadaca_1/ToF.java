/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author abenkovic
 */
public class ToF {

    Mjesto[] mjesta = null;
    Aktuator[] aktuatori = null;
    Senzor[] senzori = null;

    public ToF(ToFBuilder builder) {
        System.out.println("Kreiram ToF");
    }

    public static class ToFBuilder {

        HashMap<String, ArrayList<String[]>> podaci = new HashMap<String, ArrayList<String[]>>();

        public ToFBuilder(String[] args) {
            System.out.println("Konstruktor ToF buildera");
            System.out.println("Ucitavam senzore...");
            this.podaci.put("senzori", this.ucitajPodatke(args[2]));

            System.out.println("Ucitavam aktuatore...");
            this.podaci.put("aktuatori", this.ucitajPodatke(args[3]));

            System.out.println("Ucitavam mjesta...");
            this.podaci.put("mjesta", this.ucitajPodatke(args[1]));

            ArrayList<String[]> senzori = this.podaci.get("senzori");
            
            senzori.forEach(data -> { System.out.println(data[0]);});

        }

        private ArrayList<String[]> ucitajPodatke(String lokacija) {
            try {
                FileReader fr = new FileReader(lokacija);
                BufferedReader br = new BufferedReader(fr);
                ArrayList<String[]> podaci = new ArrayList<String[]>();
                String s;
                int brojAtributa = 0;
                while ((s = br.readLine()) != null) {
                    String[] podatak = s.trim().split(";");
                    if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa
                        brojAtributa = podatak.length;
                    } else if (podatak.length == brojAtributa) {
                        podaci.add(podatak);

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
        
        public ToFBuilder postaviUredjaje(){
            AbstractFactory factory = new ToFFactory();
            Mjesto mjesta = factory.kreirajMjesto();
            
            return this;
        }
        

        public ToFBuilder inicijalizacija() {
            System.out.println("Inicijalizacija");
            Uredjaj senzor1 = new Senzor();
            Uredjaj aktuator1 = new Aktuator();
            senzor1.setBrojProvjera(5);

            return this;
        }

        public ToF build() {
            return new ToF(this);
        }

    }
}
