/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

/**
 *
 * @author abenkovic
 */
public class Mjesto {

    private static volatile Mjesto INSTANCE;
    private Uredjaj[] uredjaji = null;

    private Mjesto() {
    }

    public static synchronized Mjesto getInstance() {
        if (INSTANCE == null) {
            synchronized (Mjesto.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Mjesto();
                }
            }
        }
        return INSTANCE;
    }


    
    


}
