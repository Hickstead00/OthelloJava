package controleur;

import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private ControleurJeu controleurJeu;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void jouer() {
        choixJeu();
        controleurJeu.jouer();
    }

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