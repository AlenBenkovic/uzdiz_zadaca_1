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
    String log = null;

    public Senzor(String naziv, int tip, int vrsta, float min, float max, String komentar) {
        super(naziv, tip, vrsta, min, max, komentar);
    }

    @Override
    public void provjera() {
        int status = this.getStatus();
        this.log ="\nUređaj: " + this.naziv + "\nStatus: " + status + " (neuspješne provjere: " + this.neuspjesneProvjere + ")\n";
               
        
        if(status > 0){
            this.log = log + "Vrijednost: " + this.getVrijednost() + "\n----------";
        } else {
            this.log = log + "Vrijednost: nepoznato\n----------";
        }
        this.logs.log(this.log, "info");

    }

    @Override
    public String getVrijednost() {
        this.vrijednost = this.kreirajRandomVrijednost(this.min, this.max);
        return super.getVrijednost();
    }

}
