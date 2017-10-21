/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author abenkovic
 */
public class ToF {

    HashMap<String, Mjesto> mjesta = new HashMap<>();
    String[] args = null;
    FoiLogger logs = FoiLogger.getInstance();

    public ToF(ToFBuilder builder) {
        this.mjesta = builder.mjesta;
        this.args = builder.args;
    }

    public void radiProvjere() {
        Runnable dretva = () -> {
            int i = 0;
            while (i < Integer.parseInt(this.args[6])) {
                try {
                    i++;
                    Thread.sleep(Integer.parseInt(this.args[5]) * 1000);
                    System.out.println("Dretva nesta radi...");

                    for (Mjesto mjesto : this.mjesta.values()) {
                        mjesto.provjeriUredjaje(this.args[4]);
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Problem sa dretvom...");
                }

            }
            this.statistika();
        };

        new Thread(dretva).start();

    }

    public void statistika() {
        for (Mjesto mjesto : this.mjesta.values()) {
            String log = "\n-------------------------------------------------------------"
                        + "\n\tStatistika za " + mjesto.naziv
                        + "\n-------------------------------------------------------------\n";
            for (Map.Entry<String, Integer> entry : mjesto.statistikaMjesta.entrySet()) {
                
                       log = log + entry.getKey() + ": " + entry.getValue() + "\n";
            }
            this.logs.log(log, "info");
        }
    }

    public static class ToFBuilder {

        HashMap<String, Mjesto> mjesta = new HashMap<>();
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
            /*
            
            
             */
            return new ToF(this);
        }

    }
}
