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
public class Alebenkov_zadaca_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 8) {
            System.out.println("Broj parametara nije valjan!");
            System.exit(0);
        } else {
            ToF tof = new ToF.ToFBuilder(args)
                    .postaviUredjaje()
                    .inicijalizacija()
                    .build();
            
            tof.radiProvjere();
            
        }
        
    }
    
}
