package main;

import modele.Joueur;
import modele.Plateau;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(8);
        plateau.afficherPlateau();
        Joueur joueur = new Joueur("TEST", '●');
        joueur.poserPion("A", "1", plateau);
        plateau.afficherPlateau();

    }

}
