/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author abenkovic
 */
public interface AbstractFactory {

    ArrayList<Uredjaj> kreirajUredjaje(String lokacija, boolean senzor);

    HashMap<String, Mjesto> kreirajMjesta(String lokacija);

    Mjesto kreirajMjesto(String[] mjesto);

    Uredjaj kreirajSenzor(String[] senzor);

    Uredjaj kreirajAktuator(String[] aktuator);
    
    ArrayList<String[]> ucitajPopisUredjaja(String lokacija, boolean isSenzor);

}
