package controleur;

import modele.Coordonnee;
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
        //while (!partieTerminee) {
        ihm.afficherPlateau(jeu.getPlateau());
        Coordonnee coordonnee;
        do {
            coordonnee = ihm.collecterCoup(jeu.getJoueurCourant());
            if (!coordonnee.estValide(jeu.getTaillePlateau())) {
                String message = "Coordonnée invalide ! Les valeurs doivent être entre 0 et " + jeu.getTaillePlateau() + ".";
                ihm.afficherMessage(message);
            }
        } while (!coordonnee.estValide(jeu.getTaillePlateau()));

        //private void finPartie() {}

    }

}