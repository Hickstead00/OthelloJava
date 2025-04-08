/**
 * Classe principale du programme qui initialise et lance le jeu.
 * Elle crée l'interface utilisateur et le contrôleur, puis démarre la partie.
 */
package main;

import controleur.Controleur;
import vue.Ihm;

public class Main {
    /**
     * Point d'entrée du programme.
     * Initialise l'interface utilisateur et le contrôleur, puis lance le jeu.
     * @param args Les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.jouer();
    }
}
