/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.alebenkov_zadaca_1.logs;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author abenkovic
 */
public class FoiLogger {

    private static volatile FoiLogger INSTANCE;
    Logger logger = Logger.getLogger(FoiLogger.class.getName());
    FileHandler fh;
    String datoteka;

    static {
        INSTANCE = new FoiLogger();
    }

    private FoiLogger() {

    }

    public static FoiLogger getInstance() {
        return INSTANCE;
    }

    public void init(String datoteka) {
        this.datoteka = datoteka;
        FoiFormatter formatter = new FoiFormatter();
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
        try {

            fh = new FileHandler(this.datoteka);
            logger.addHandler(fh);

            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            System.err.println("Greska prilikom I/O logova");
        }
    }

    public void log(String poruka, String tip) {
        switch (tip) {
            case "info":
                logger.info(poruka);
                break;
            case "warning":
                logger.warning(poruka);
                break;
            case "fine":
                logger.fine(poruka);
                break;

        }

    }

}
