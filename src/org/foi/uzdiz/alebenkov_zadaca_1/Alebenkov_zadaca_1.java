/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.Random;

/**
 *
 * @author abenkovic
 */
public class Alebenkov_zadaca_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double random = new Random(540).nextDouble();
        ToF tof = new ToF.ToFBuilder(args)
                .inicijalizacija()
                .build();

    }
    


}
