/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1.AbstractFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.foi.uzdiz.alebenkov_zadaca_1.Aktuator;
import org.foi.uzdiz.alebenkov_zadaca_1.Mjesto;
import org.foi.uzdiz.alebenkov_zadaca_1.Senzor;
import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;

/**
 *
 * @author abenkovic
 */
public class ToFFactory implements AbstractFactory {

    HashMap<String, String[]> mjesta;
    ArrayList<String[]> popisSenzora = new ArrayList<>();
    ArrayList<String[]> popisAktuatora = new ArrayList<>();
    FoiLogger logs = FoiLogger.getInstance();
    String[] args = null;

    public ToFFactory(String[] args) {
        this.args = args;

    }

    @Override
    public Mjesto kreirajMjesto(String[] podaciMjesta) {
        Mjesto mjesto = new Mjesto(podaciMjesta[0], Integer.parseInt(podaciMjesta[1]), Integer.parseInt(podaciMjesta[2]), Integer.parseInt(podaciMjesta[3]));
        for (int i = 0; i < mjesto.brojSenzora; i++) {
            mjesto.setUredjaj(this.dohvatiRandomSenzor(mjesto.tip));
        }

        for (int i = 0; i < mjesto.brojAktuatora; i++) {
            mjesto.setUredjaj(this.dohvatiRandomAktuator(mjesto.tip));
        }
        return mjesto;
    }

    @Override
    public Senzor kreirajSenzor(String[] senzor) {
        return new Senzor(senzor[0], Integer.parseInt(senzor[1]), Integer.parseInt(senzor[2]), Float.parseFloat(senzor[3]), Float.parseFloat(senzor[4]), senzor.length == 5 ? "-" : senzor[5]);
    }

    @Override
    public Aktuator kreirajAktuator(String[] aktuator) {
        return new Aktuator(aktuator[0], Integer.parseInt(aktuator[1]), Integer.parseInt(aktuator[2]), Float.parseFloat(aktuator[3]), Float.parseFloat(aktuator[4]), aktuator.length == 5 ? "-" : aktuator[5]);
    }

    @Override
    public HashMap<String, Mjesto> kreirajMjesta() {
        try {
            FileReader fr = new FileReader(this.args[1]);
            BufferedReader br = new BufferedReader(fr);
            HashMap<String, Mjesto> mjesta = new HashMap<>();
            String s;
            int brojAtributa = 0;
            while ((s = br.readLine()) != null) {
                String[] podatak = s.trim().split(";");
                if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa
                    brojAtributa = podatak.length;
                } else if (podatak.length == brojAtributa) {
                    this.logs.log("Kreiram mjesto " + podatak[0] + " .", "info");
                    mjesta.put(podatak[0], this.kreirajMjesto(podatak));

                } else {
                    // zapis nije valjan
                }
            }
            return mjesta;
        } catch (IOException e) {
            System.out.println("Greska prilikom citanja datoteke: " + e.toString());
            return null;
        }
    }

    @Override
    public ArrayList<String[]> ucitajPopisUredjaja(boolean isSenzor) {
        try {
            FileReader fr = new FileReader(isSenzor ? this.args[2] : this.args[3]);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String[]> uredjaji = new ArrayList<>();
            String s;
            int brojAtributa = 0;
            while ((s = br.readLine()) != null) {
                String[] podatak = s.trim().split(";");
                if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa
                    brojAtributa = podatak.length;
                } else if (podatak.length == brojAtributa || podatak.length == brojAtributa - 1) {
                    uredjaji.add(podatak);
                    if (isSenzor) {
                        this.popisSenzora.add(podatak);
                    } else {
                        this.popisAktuatora.add(podatak);
                    }

                } else {
                     this.logs.log("Format zapisa za " + podatak[0] + " nije valjan.", "warning");
                }
            }
            return uredjaji;
        } catch (IOException e) {
            System.out.println("Greska prilikom citanja datoteke: " + e.toString());
            return null;
        }

    }

    private Senzor dohvatiRandomSenzor(int tip) {
        ArrayList<Integer> prihvatljiviSenzori = new ArrayList<>();
        int i = 0;
        for (String[] senzor : this.popisSenzora) {

            if (Integer.parseInt(senzor[1]) == tip || Integer.parseInt(senzor[1]) == 2) {
                prihvatljiviSenzori.add(i);
            }
            i++;
        }

        Random rn = new Random();
        int randomBroj = rn.nextInt(prihvatljiviSenzori.size());
        int indexSenzora = prihvatljiviSenzori.get(randomBroj);
        String[] odabrani = this.popisSenzora.get(indexSenzora);
        Senzor senzor = this.kreirajSenzor(odabrani);

        return senzor;

    }

    private Aktuator dohvatiRandomAktuator(int tip) {
        ArrayList<Integer> prihvatljiviAktuatori = new ArrayList<>();
        int i = 0;
        for (String[] aktuator : this.popisAktuatora) {

            if (Integer.parseInt(aktuator[1]) == tip || Integer.parseInt(aktuator[1]) == 2) {
                prihvatljiviAktuatori.add(i);
            }
            i++;
        }

        Random rn = new Random();
        int randomBroj = rn.nextInt(prihvatljiviAktuatori.size());
        int indexAktuatora = prihvatljiviAktuatori.get(randomBroj);
        String[] odabrani = this.popisAktuatora.get(indexAktuatora);
        Aktuator aktuator = this.kreirajAktuator(odabrani);

        return aktuator;

    }

}
