package fr.eni.papeterie.ihm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;


public class Main {

    private static Logger logMain = LoggerFactory.getLogger(Main.class); //on le met en attribut de classe (static)

    public static void main(String[] args) {
        logMain.trace("Démarrage du programme.");

        SwingUtilities.invokeLater(
                () -> {
                    logMain.debug("Lancement de GUI");
                    JFrame app = new GUI();
                }
        );
        logMain.debug("Fin du programme.");
    }
}
