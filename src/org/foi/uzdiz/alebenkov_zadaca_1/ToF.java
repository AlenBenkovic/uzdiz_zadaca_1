/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

        ArrayList<ArrayList<String[]>> uredjaji = new ArrayList<>();
        ArrayList<String[]> mjesta = new ArrayList<>();


        public ToFBuilder(String[] args) {
            System.out.println("Konstruktor ToF buildera");
            System.out.println("Ucitavam senzore...");
            this.uredjaji.add(this.ucitajPodatke(args[2]));

            System.out.println("Ucitavam aktuatore...");
            this.uredjaji.add(this.ucitajPodatke(args[3]));

            System.out.println("Ucitavam mjesta...");
            mjesta = this.ucitajPodatke(args[1]);
            
            this.uredjaji.forEach(el -> {
                el.forEach(data -> System.out.println(data[0]));
            });
            
            this.mjesta.forEach(data -> System.out.println(data[0]));


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
                        System.out.println("HEADER: " + podatak[0] + podatak.length);
                        brojAtributa = podatak.length;
                    } else if (podatak.length == brojAtributa) {
                        System.out.println("VALJA: " + podatak[0] + podatak.length);
                        podaci.add(podatak);

                    } else {
                        System.out.println("NEVALJA: " + podatak[0] + podatak.length);
                    }
                }
                podaci.forEach(data -> {
                    System.out.println(data[1]);
                });
                return podaci;
            } catch (IOException e) {
                System.out.println("Greska prilikom citanja datoteke: " + e.toString());
                return null;
            }
        }

        public ToFBuilder inicijalizacija() {
            System.out.println("Inicijalizacija");
            Uredjaj senzor1 = new Senzor();
            Uredjaj aktuator1 = new Aktuator();
            senzor1.setBrojProvjera(5);
            System.out.println(senzor1.brojProvjera);
            System.out.println(aktuator1.brojProvjera);

            return this;
        }

        public ToF build() {
            return new ToF(this);
        }

    }
}
