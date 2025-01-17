/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1;

import java.util.Random;
import org.foi.uzdiz.alebenkov_zadaca_1.logs.FoiLogger;

/**
 *
 * @author abenkovic
 */
public class Aktuator extends Uredjaj {

    FoiLogger logs = FoiLogger.getInstance();
    boolean gore = true;
    String log = null;

    public Aktuator(String naziv, int tip, int vrsta, float min, float max, String komentar) {
        super(naziv, tip, vrsta, min, max, komentar);
    }

    @Override
    public void provjera() {
        int status = this.getStatus();
        this.log = "\nUređaj: " + this.naziv + "\nStatus: " + status + " (neuspješne provjere: " + this.neuspjesneProvjere + ")\n";
                
        if (status > 0) {
            this.log = log + "Vrijednost: " + this.getVrijednost();
            this.obaviRadnju();

        } else {
            this.log = log + "Vrijednost: nepoznato";
            this.log = log + "\nRadnju nije moguće obaviti." + "\n----------";

        }
        this.logs.log(log, "info");
    }

    public void obaviRadnju() {
        switch (this.vrsta) {
            case 0:
            case 1:
            case 2:
                if (this.vrijednost == this.max) {
                    this.gore = false;
                } else if (this.vrijednost == this.min) {
                    this.gore = true;
                }
                if (this.gore) {
                    this.vrijednost = this.kreirajRandomVrijednost(this.vrijednost, this.max);
                } else {
                    this.vrijednost = this.kreirajRandomVrijednost(this.min, this.vrijednost);
                }
                break;
            case 3:
                if (this.vrijednost > 0) {
                    this.vrijednost = 0;
                } else {
                    this.vrijednost = 1;
                }

        }
        this.log = log + "\nAktuator izvršava radnju.\nNova vrijednost: " + this.getVrijednost() + "\n----------";

    }

}
