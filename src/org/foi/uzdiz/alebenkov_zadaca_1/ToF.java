/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        FoiLogger logs = FoiLogger.getInstance();

        public ToFBuilder(String[] args) {
            this.args = args;
            this.logs.init(args[7]);
        }

        public ToFBuilder postaviUredjaje() {
            AbstractFactory factory = new ToFFactory();
            factory.ucitajPopisUredjaja(this.args[2], true); // ucitavam popis senzora
            factory.ucitajPopisUredjaja(this.args[3], false); // ucitavam popis aktuatora
            this.mjesta = factory.kreirajMjesta(this.args[1]); // kreiram mjesta i dodjeljujem uredjaje
            return this;
        }

        public ToFBuilder inicijalizacija() {
            System.out.println("#Inicijalizacija");
            for (Mjesto mjesto : this.mjesta.values()) {

                this.logs.log("\n-------------------------------------------------------------"
                        + "\n\tInicijaliziram uređaje za " + mjesto.naziv
                        + "\n-------------------------------------------------------------", "info");
                for (Senzor senzor : mjesto.senzori) {
                    if (!senzor.inicijalizacija()) {
                        senzor.setOnemogucen(true);
                        this.logs.log(senzor.naziv + " FAILED", "warning");

                    } else {
                        this.logs.log(senzor.naziv + " OK", "info");

                    }
                }

                for (Aktuator aktuator : mjesto.aktuatori) {
                    if (!aktuator.inicijalizacija()) {
                        aktuator.setOnemogucen(true);
                        this.logs.log(aktuator.naziv + " FAILED", "warning");
                    } else {
                        this.logs.log(aktuator.naziv + " OK", "info");
                    }
                }

                mjesto.makniOnemogucene();
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
                        HashMap<String, Uredjaj> uredjajiZamjena = new HashMap<>();

                        for (Mjesto mjesto : this.mjesta.values()) {
                            this.logs.log("\n-------------------------------------------------------------"
                                    + "\n\tRadim provjeru uređaja za " + mjesto.naziv
                                    + "\n-------------------------------------------------------------", "info");
                            for (Senzor senzor : mjesto.senzori) {
                                this.logs.log(senzor.naziv + ": " + senzor.getStatus() + " (neuspješne provjere: " + senzor.neuspjesneProvjere + ")", "info");
                                if (senzor.neuspjesneProvjere == 3) {
                                    uredjajiZamjena.put(mjesto.naziv, senzor);
                                }
                            }

                            for (Aktuator aktuator : mjesto.aktuatori) {
                                this.logs.log(aktuator.naziv + ": " + aktuator.getStatus() + " (neuspješne provjere: " + aktuator.neuspjesneProvjere + ")", "info");
                                if (aktuator.neuspjesneProvjere == 3) {
                                    uredjajiZamjena.put(mjesto.naziv, aktuator);
                                }
                            }

                        }

                        if (!uredjajiZamjena.isEmpty()) {
                            uredjajiZamjena.entrySet().stream().map((entry) -> {
                                String mjesto = entry.getKey();
                                Uredjaj stari = entry.getValue();
                                this.logs.log("\n-------------------------------------------------------------"
                                        + "\n\tRadim zamjene uređaja u " + mjesto
                                        + "\n-------------------------------------------------------------", "info");
                                Uredjaj noviUredjaj = (Uredjaj) stari.clone();
                                if (stari instanceof Senzor) {
                                    System.out.println("SENZOR");
                                    this.mjesta.get(mjesto).removeSenzor((Senzor) stari);
                                    this.mjesta.get(mjesto).setSenzor((Senzor) noviUredjaj);
                                } else if (stari instanceof Aktuator) {
                                    System.out.println("AKTUATOR");
                                    this.mjesta.get(mjesto).removeAktuator((Aktuator) stari);
                                    this.mjesta.get(mjesto).setAktuator((Aktuator) noviUredjaj);
                                }
                                return noviUredjaj;
                            }).forEachOrdered((noviUredjaj) -> {
                                noviUredjaj.inicijalizacija();
                            });
                            uredjajiZamjena.clear();

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
