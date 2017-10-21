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
    public ArrayList<Senzor> senzori = new ArrayList<>();
    public ArrayList<Aktuator> aktuatori = new ArrayList<>();
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

    public void setSenzor(Senzor senzor) {
        this.logs.log("Postavljam senzor " + senzor.naziv, "info");
        this.senzori.add(senzor);
        int tmp = this.statistikaMjesta.get("Broj dodanih senzora");
        this.statistikaMjesta.put("Broj dodanih senzora", tmp + 1);

    }

    public void setAktuator(Aktuator aktuator) {
        this.logs.log("Postavljam aktuator " + aktuator.naziv, "info");
        this.aktuatori.add(aktuator);
        int tmp = this.statistikaMjesta.get("Broj dodanih aktuatora");
        this.statistikaMjesta.put("Broj dodanih aktuatora", tmp + 1);

    }

    public void removeSenzor(Senzor senzor) {
        this.logs.log("Uklanjam senzor " + senzor.naziv, "info");
        this.senzori.remove(senzor);
        int tmp = this.statistikaMjesta.get("Broj uklonjenih senzora");
        this.statistikaMjesta.put("Broj uklonjenih senzora", tmp + 1);

    }

    public void removeAktuator(Aktuator aktuator) {
        this.logs.log("Uklanjam aktuator " + aktuator.naziv, "info");
        this.aktuatori.remove(aktuator);
        int tmp = this.statistikaMjesta.get("Broj uklonjenih aktuatora");
        this.statistikaMjesta.put("Broj uklonjenih aktuatora", tmp + 1);
    }

    public void makniOnemogucene() {
        for (Iterator<Senzor> iterator = senzori.iterator(); iterator.hasNext();) {
            Senzor next = iterator.next();
            if (next.onemogucen) {
                this.logs.log("Uklanjam senzor: " + next.naziv, "info");
                iterator.remove();
                int tmp = this.statistikaMjesta.get("Broj senzora koji nisu prošli inicijalizaciju");
                this.statistikaMjesta.put("Broj senzora koji nisu prošli inicijalizaciju", tmp + 1);
            }

        }

        for (Iterator<Aktuator> iterator = aktuatori.iterator(); iterator.hasNext();) {
            Aktuator next = iterator.next();
            if (next.onemogucen) {
                this.logs.log("Uklanjam aktuator: " + next.naziv, "info");
                iterator.remove();
                int tmp = this.statistikaMjesta.get("Broj aktuatora koji nisu prošli inicijalizaciju");
                this.statistikaMjesta.put("Broj aktuatora koji nisu prošli inicijalizaciju", tmp + 1);
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
                this.senzori.stream().map((senzor) -> {
                    senzor.provjera();
                    return senzor;
                }).filter((senzor) -> (senzor.neuspjesneProvjere == 3)).forEachOrdered((senzor) -> {
                    uredjajiZamjena.add(senzor);
                });

                this.aktuatori.stream().map((aktuator) -> {
                    aktuator.provjera();
                    return aktuator;
                }).filter((aktuator) -> (aktuator.neuspjesneProvjere == 3)).forEachOrdered((aktuator) -> {
                    uredjajiZamjena.add(aktuator);
                });
                break;
            case "random":
                this.logs.log("***Radim random provjeru***", "info");

                ArrayList<Integer> senzoriRandom = new ArrayList<>(this.senzori.size());
                ArrayList<Integer> aktuatoriRandom = new ArrayList<>(this.aktuatori.size());

                for (int i = 0; i < senzori.size(); i++) {
                    senzoriRandom.add(i);
                }
                for (int i = 0; i < aktuatori.size(); i++) {
                    aktuatoriRandom.add(i);
                }
                Collections.shuffle(senzoriRandom);
                Collections.shuffle(aktuatoriRandom);

                senzoriRandom.forEach((Integer index) -> {
                    Senzor trenutniSenzor = this.senzori.get(index);
                    trenutniSenzor.provjera();
                    if (trenutniSenzor.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniSenzor);
                    }
                });

                aktuatoriRandom.forEach((Integer index) -> {
                    Aktuator trenutniAktuator = this.aktuatori.get(index);
                    trenutniAktuator.provjera();
                    if (trenutniAktuator.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniAktuator);
                    }
                });

                break;

            case "obrnuto":
                this.logs.log("***Radim obrnutu provjeru***", "info");

                ArrayList<Integer> senzoriObrnuto = new ArrayList<>(this.senzori.size());
                ArrayList<Integer> aktuatoriObrnuto = new ArrayList<>(this.aktuatori.size());

                for (int i = 0; i < senzori.size(); i++) {
                    senzoriObrnuto.add(i);
                }
                for (int i = 0; i < aktuatori.size(); i++) {
                    aktuatoriObrnuto.add(i);
                }
                Collections.reverse(senzoriObrnuto);
                Collections.reverse(aktuatoriObrnuto);

                senzoriObrnuto.forEach((Integer index) -> {
                    Senzor trenutniSenzor = this.senzori.get(index);
                    trenutniSenzor.provjera();
                    if (trenutniSenzor.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniSenzor);
                    }
                });

                aktuatoriObrnuto.forEach((Integer index) -> {
                    Aktuator trenutniAktuator = this.aktuatori.get(index);
                    trenutniAktuator.provjera();
                    if (trenutniAktuator.neuspjesneProvjere == 3) {
                        uredjajiZamjena.add(trenutniAktuator);
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
