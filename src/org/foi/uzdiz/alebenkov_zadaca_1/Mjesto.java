/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
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
    public ArrayList<Uredjaj> uredjaji = new ArrayList<>();
    FoiLogger logs = FoiLogger.getInstance();
    HashMap<String, Integer> statistikaMjesta = new HashMap<String, Integer>();

    public Mjesto(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
        this.statistikaMjesta.put("Ukupan broj senzora", this.brojSenzora);
        this.statistikaMjesta.put("Ukupan broj aktuatora", this.brojAktuatora);
        this.statistikaMjesta.put("Broj senzora koji nisu prošli inicijalizaciju", 0);
        this.statistikaMjesta.put("Broj aktuatora koji nisu prošli inicijalizaciju", 0);
        this.statistikaMjesta.put("Broj dodanih senzora", 0);
        this.statistikaMjesta.put("Broj dodanih aktuatora", 0);
        this.statistikaMjesta.put("Broj uklonjenih senzora", 0);
        this.statistikaMjesta.put("Broj uklonjenih aktuatora", 0);
    }

    public void setUredjaj(Uredjaj uredjaj) {
        this.logs.log("Postavljam uredjaj " + uredjaj.naziv, "info");
        this.uredjaji.add(uredjaj);
        if (uredjaj instanceof Senzor) {
            int tmp = this.statistikaMjesta.get("Broj dodanih senzora");
            this.statistikaMjesta.put("Broj dodanih senzora", tmp + 1);
        } else {
            int tmp = this.statistikaMjesta.get("Broj dodanih aktuatora");
            this.statistikaMjesta.put("Broj dodanih aktuatora", tmp + 1);
        }
    }

    public void removeUredjaj(Uredjaj uredjaj) {
        this.logs.log("Uklanjam uredjaj " + uredjaj.naziv, "info");
        this.uredjaji.remove(uredjaj);
        if (uredjaj instanceof Senzor) {
            int tmp = this.statistikaMjesta.get("Broj uklonjenih senzora");
            this.statistikaMjesta.put("Broj uklonjenih senzora", tmp + 1);
        } else {
            int tmp = this.statistikaMjesta.get("Broj uklonjenih aktuatora");
            this.statistikaMjesta.put("Broj uklonjenih aktuatora", tmp + 1);
        }

    }

   

    public void makniOnemogucene() {
        for (Iterator<Uredjaj> iterator = uredjaji.iterator(); iterator.hasNext();) {
            Uredjaj next = iterator.next();
            if (next.onemogucen) {
                this.logs.log("Uklanjam uredjaj: " + next.naziv, "info");
                iterator.remove();
                if (next instanceof Senzor) {
                    int tmp = this.statistikaMjesta.get("Broj senzora koji nisu prošli inicijalizaciju");
                    this.statistikaMjesta.put("Broj senzora koji nisu prošli inicijalizaciju", tmp + 1);
                } else {
                    int tmp = this.statistikaMjesta.get("Broj uklonjenih aktuatora");
                    this.statistikaMjesta.put("Broj uklonjenih aktuatora", tmp + 1);
                }

            }

        }
    }

    public int kreirajRandomVrijednost(int min, int max) {
        Random r = new Random();
        int R = r.nextInt(max + 1 - min) + min;
        return R;
    }

    public void provjeriUredjaje(String algoritam) {
        ArrayList<Uredjaj> uredjajiZamjena = new ArrayList<>();

        this.logs.log("\n-------------------------------------------------------------"
                + "\n\tRadim provjeru uređaja za " + this.naziv
                + "\n-------------------------------------------------------------", "info");
        switch (algoritam) {
            case "slijedno":
                this.logs.log("***Radim slijednu provjeru***", "info");
                this.uredjaji.stream().map((uredjaj) -> {
                    uredjaj.provjera();
                    return uredjaj;
                }).filter((uredjaj) -> (uredjaj.neuspjesneProvjere == 3)).forEachOrdered((uredjaj) -> {
                    uredjajiZamjena.add(uredjaj);
                });
                break;
            case "random":
                this.logs.log("***Radim random provjeru***", "info");

                ArrayList<Integer> uredjajiRandom = new ArrayList<>(this.uredjaji.size());

                for (int i = 0; i < uredjaji.size(); i++) {
                    uredjajiRandom.add(i);
                }
 
                Collections.shuffle(uredjajiRandom);

                uredjajiRandom.forEach((Integer index) -> {
                    Uredjaj trenutniUredjaj = this.uredjaji.get(index);
                    trenutniUredjaj.provjera();
                    if (trenutniUredjaj.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniUredjaj);
                    }
                });
                break;

            case "obrnuto":
                this.logs.log("***Radim obrnutu provjeru***", "info");

                ArrayList<Integer> uredjajiObrnuto = new ArrayList<>(this.uredjaji.size());

                for (int i = 0; i < uredjaji.size(); i++) {
                    uredjajiObrnuto.add(i);
                }

                Collections.reverse(uredjajiObrnuto);

                uredjajiObrnuto.forEach((Integer index) -> {
                    Uredjaj trenutniUredjaj = this.uredjaji.get(index);
                    trenutniUredjaj.provjera();
                    if (trenutniUredjaj.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniUredjaj);
                    }
                });
                break;
        }

        if (!uredjajiZamjena.isEmpty()) {
            uredjajiZamjena.forEach(stari -> {
                this.logs.log("\n-------------------------------------------------------------"
                        + "\n\tRadim zamjene uređaja u " + this.naziv
                        + "\n-------------------------------------------------------------", "info");
                Uredjaj noviUredjaj = (Uredjaj) stari.clone();
                this.removeUredjaj(stari);
                this.setUredjaj(noviUredjaj);
                noviUredjaj.inicijalizacija();

            });
            uredjajiZamjena.clear();

        }

    }

}
