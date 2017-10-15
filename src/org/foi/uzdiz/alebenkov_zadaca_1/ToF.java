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
public class ToF {

    Mjesto[] mjesta = null;
    Aktuator[] aktuatori = null;
    Senzor[] senzori = null;

    public ToF(ToFBuilder builder) {
        System.out.println("Kreiram ToF");
    }

    public static class ToFBuilder {

        Mjesto[] mjesta = null;
        Aktuator[] aktuatori = null;
        Senzor[] senzori = null;

        public ToFBuilder(String[] args) {
            System.out.println("Konstruktor ToF buildera" + args[0]);
        }
        
        public ToFBuilder inicijalizacija(){
            System.out.println("Inicijalizacija");
            return this;
        }
        
        public ToF build() {
            return new ToF(this);
        }

    }
}
