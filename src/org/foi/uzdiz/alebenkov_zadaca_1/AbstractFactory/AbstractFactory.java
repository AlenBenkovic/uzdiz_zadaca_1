/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1.AbstractFactory;

import java.util.ArrayList;
import java.util.HashMap;
import org.foi.uzdiz.alebenkov_zadaca_1.Mjesto;
import org.foi.uzdiz.alebenkov_zadaca_1.Uredjaj;

/**
 *
 * @author abenkovic
 */
public interface AbstractFactory {


    HashMap<String, Mjesto> kreirajMjesta();

    Mjesto kreirajMjesto(String[] mjesto);

    Uredjaj kreirajSenzor(String[] senzor);

    Uredjaj kreirajAktuator(String[] aktuator);
    
    ArrayList<String[]> ucitajPopisUredjaja(boolean isSenzor);

}
