/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;

/**
 *
 * @author abenkovic
 */
public class Senzor extends Uredjaj {

    FoiLogger logs = FoiLogger.getInstance();

    public Senzor(String naziv, int tip, int vrsta, float min, float max, String komentar) {
        super(naziv, tip, vrsta, min, max, komentar);
    }

    @Override
    public void provjera() {

        this.logs.log("\nUređaj: " + this.naziv + "\nStatus: " + super.getStatus() + " (neuspješne provjere: " + this.neuspjesneProvjere + ")\n"
                + "Vrijednost: " + super.getVrijednost() + "\n----------", "info");

    }

}
