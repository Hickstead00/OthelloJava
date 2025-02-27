package controleur;

import modele.Jeu;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Jeu jeu;


    public Controleur(Ihm ihm) {
        this.ihm = ihm;
        this.jeu = new Jeu();
    }


    public void jouer() {
        initialiserPartie();
        deroulementPartie();
        //finPartie();
    }

    private void initialiserPartie() {
        ihm.initPartie();
        jeu.initialiserJoueur(ihm.collecterJoueur(), ihm.collecterJoueur());
    }

    private void deroulementPartie() {
        boolean partieTerminee = false;
        while (!partieTerminee) {
            ihm.afficherPlateau(jeu.getPlateau());
        }

        //private void finPartie() {}
    }
}