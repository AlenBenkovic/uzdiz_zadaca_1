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

                this.logs.log("\n-------------------------------------------------------\n\tInicijaliziram uređaje za " + mjesto.naziv
                        + "\n-------------------------------------------------------", "info");
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
                        HashMap<String, Senzor> senzoriZamjena = new HashMap<>();

                        for (Mjesto mjesto : this.mjesta.values()) {
                            System.out.println("##Radim provjeru uredjaja za " + mjesto.naziv);
                            for (Senzor senzor : mjesto.senzori) {

                                System.out.println("------------------------------");
                                System.out.println(senzor.naziv);
                                System.out.println(senzor.getStatus());
                                System.out.println(senzor.neuspjesneProvjere);
                                if (senzor.neuspjesneProvjere == 3) {

                                    senzoriZamjena.put(mjesto.naziv, senzor);

                                }

                            }

                            for (Aktuator aktuator : mjesto.aktuatori) {

                            }

                        }

                        for (Map.Entry<String, Senzor> entry : senzoriZamjena.entrySet()) {

                            String mjesto = entry.getKey();
                            Senzor stari = entry.getValue();

                            System.out.println("*******GASIM****** " + stari.naziv);
                            this.mjesta.get(mjesto).removeSenzor(stari);

                            Uredjaj noviSenzor = (Senzor) stari.clone();
                            noviSenzor.inicijalizacija();
                            System.out.println("****DODAJEM NOVI****" + noviSenzor.naziv);
                            this.mjesta.get(mjesto).setSenzor((Senzor) noviSenzor);

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
