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

            return this;
        }

        public ToFBuilder inicijalizacija() {
            System.out.println("#Inicijalizacija");
            for (Mjesto mjesto : this.mjesta.values()) {
                System.out.println("##Inicijaliziram uredjaje za " + mjesto.naziv);
                for (Senzor senzor : mjesto.senzori) {
                    if (!senzor.inicijalizacija(Integer.parseInt(this.args[0]))) {
                        senzor.setOnemogucen(true);
                        System.out.println(senzor.naziv + " FAILED");
                    } else {
                        System.out.println(senzor.naziv + " OK");
                    }
                }

                for (Aktuator aktuator : mjesto.aktuatori) {
                    if (!aktuator.inicijalizacija(Integer.parseInt(this.args[0]))) {
                        aktuator.setOnemogucen(true);
                        System.out.println(aktuator.naziv + " FAILED");
                    } else {
                        System.out.println(aktuator.naziv + " OK");
                    }
                }

                mjesto.makniOnemogucene();
                System.out.println("------------------------------------------");
            }

            return this;
        }

        public ToF build() {
            System.out.println("Pokrecem dretvu...");
            Runnable dretva = () -> {
                int i = 0;
                while (i < Integer.parseInt(this.args[6])) {
                    try {
                        i++;
                        Thread.sleep(Integer.parseInt(this.args[5]) * 1000);
                        System.out.println("Dretva nesta radi...");
                        for (Mjesto mjesto : this.mjesta.values()) {
                            System.out.println("##Radim provjeru uredjaja za " + mjesto.naziv);
                            for (Iterator<Uredjaj> iterator = this.senzori.iterator(); iterator.hasNext();) {
                                Uredjaj senzor = iterator.next();
                                System.out.println("------------------------------");
                                System.out.println(senzor.naziv);
                                System.out.println(senzor.getStatus());
                                System.out.println(senzor.neuspjesneProvjere);
                                if (senzor.neuspjesneProvjere == 3) {
                                    System.out.println("*******GASIM****** " + senzor.naziv);
                                    iterator.remove();
                                }

                            }

                            for (Aktuator aktuator : mjesto.aktuatori) {

                            }

                        }
                    } catch (InterruptedException ex) {
                        System.out.println("Problem sa dretvom...");
                    }

                }
            };

            new Thread(dretva).start();

            return new ToF(this);
        }

    }
}
