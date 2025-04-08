/**
 * Classe principale du contrôleur qui gère le flux général du jeu.
 * Elle permet de choisir le type de jeu et de lancer la partie.
 */
package controleur;

import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private ControleurJeu controleurJeu;

    /**
     * Constructeur du contrôleur principal.
     * @param ihm L'interface homme-machine utilisée pour l'interaction avec l'utilisateur
     */
    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    /**
     * Méthode principale qui lance le jeu.
     * Elle permet de choisir le type de jeu et de commencer la partie.
     */
    public void jouer() {
        choixJeu();
        controleurJeu.jouer();
    }

    /**
     * Permet à l'utilisateur de choisir le type de jeu qu'il souhaite jouer.
     * Par défaut, si le choix n'est pas valide, le jeu Othello est sélectionné.
     */
    public void choixJeu() {
        String choix = ihm.choisirJeu();
        switch (choix) {
            case "1":
                this.controleurJeu = new ControleurOthello(ihm);
                break;
            case "2":
                this.controleurJeu = new ControleurAwale(ihm);
                break;
            default:
                this.controleurJeu = new ControleurOthello(ihm);
                break;
        }
    }
}