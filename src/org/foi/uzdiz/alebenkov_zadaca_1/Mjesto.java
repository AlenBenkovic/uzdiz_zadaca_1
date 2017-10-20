/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.ArrayList;
import java.util.Iterator;
import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;

/**
 *
 * @author abenkovic
 */
public class Mjesto {

    public String naziv;
    public int tip;
    public int brojSenzora;
    public int brojAktuatora;
    public ArrayList<Senzor> senzori = new ArrayList<>();
    public ArrayList<Aktuator> aktuatori = new ArrayList<>();
    FoiLogger logs = FoiLogger.getInstance();

    public Mjesto(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
    }

    public void setSenzor(Senzor senzor) {
        this.logs.log("Postavljam senzor " + senzor.naziv, "info");
        this.senzori.add(senzor);
    }

    public void setAktuator(Aktuator aktuator) {
        this.logs.log("Postavljam aktuator " + aktuator.naziv, "info");
        this.aktuatori.add(aktuator);
    }

    public void removeSenzor(Senzor senzor) {
        this.logs.log("Uklanjam senzor " + senzor.naziv, "info");
        this.senzori.remove(senzor);
    }

    public void removeAktuator(Aktuator aktuator) {
        this.logs.log("Uklanjam aktuator " + aktuator.naziv, "info");
        this.aktuatori.remove(aktuator);
    }

    public void makniOnemogucene() {
        for (Iterator<Senzor> iterator = senzori.iterator(); iterator.hasNext();) {
            Senzor next = iterator.next();
            if (next.onemogucen) {
                this.logs.log("Uklanjam senzor: " + next.naziv, "info");
                iterator.remove();
            }

        }

        for (Iterator<Aktuator> iterator = aktuatori.iterator(); iterator.hasNext();) {
            Aktuator next = iterator.next();
            if (next.onemogucen) {
                this.logs.log("Uklanjam aktuator: " + next.naziv, "info");
                iterator.remove();
            }

        }
    }

    public void provjeriUredjaje() {
        ArrayList<Uredjaj> uredjajiZamjena = new ArrayList<>();

        this.logs.log("\n-------------------------------------------------------------"
                + "\n\tRadim provjeru uređaja za " + this.naziv
                + "\n-------------------------------------------------------------", "info");
        this.senzori.stream().map((senzor) -> {
            this.logs.log("\nUređaj: " + senzor.naziv + "\nStatus: " + senzor.getStatus() + " (neuspješne provjere: " + senzor.neuspjesneProvjere + ")\n"
                    + "Vrjednost: " + senzor.getVrijednost() + "\n----------", "info");
            return senzor;
        }).filter((senzor) -> (senzor.neuspjesneProvjere == 3)).forEachOrdered((senzor) -> {
            uredjajiZamjena.add(senzor);
        });

        this.aktuatori.stream().map((aktuator) -> {
            this.logs.log("\nUređaj: " + aktuator.naziv + "\nStatus: " + aktuator.getStatus() + " (neuspješne provjere: " + aktuator.neuspjesneProvjere + ")\n"
                    + "Vrjednost: " + aktuator.getVrijednost() + "\n----------", "info");
            return aktuator;
        }).filter((aktuator) -> (aktuator.neuspjesneProvjere == 3)).forEachOrdered((aktuator) -> {
            uredjajiZamjena.add(aktuator);
        });

        if (!uredjajiZamjena.isEmpty()) {
            uredjajiZamjena.forEach(stari -> {
                this.logs.log("\n-------------------------------------------------------------"
                        + "\n\tRadim zamjene uređaja u " + this.naziv
                        + "\n-------------------------------------------------------------", "info");
                Uredjaj noviUredjaj = (Uredjaj) stari.clone();
                if (stari instanceof Senzor) {
                    this.removeSenzor((Senzor) stari);
                    this.setSenzor((Senzor) noviUredjaj);
                } else if (stari instanceof Aktuator) {
                    this.removeAktuator((Aktuator) stari);
                    this.setAktuator((Aktuator) noviUredjaj);
                }
                noviUredjaj.inicijalizacija();

            });
            uredjajiZamjena.clear();

        }

    }

}
