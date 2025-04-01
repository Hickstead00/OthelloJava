package controleur;

import modele.Jeu;
import modele.JeuOthello;
import modele.Joueur;
import modele.ia.StrategieIA;
import modele.ia.StrategieAleatoire;
import modele.ia.StrategieMiniMax;
import vue.Ihm;

public class ControleurOthello extends ControleurJeu {
    private int[] coor;

    public ControleurOthello(Ihm ihm) {
        super(ihm);
    }

    @Override
    protected Jeu creerJeu() {
        return new JeuOthello();
    }

    @Override
    protected boolean interpreterCoup(String coup, Joueur joueur) {
        if (coup.length() == 3 && coup.charAt(1) == ' ') {
            int ligne = coup.charAt(0) - '1';
            int colonne = coup.charAt(2) - 'A';
            
            if (jeu.verifCoup(ligne, colonne, joueur.getCouleur())) {
                jeu.jouerCoup(ligne, colonne, joueur.getCouleur());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void afficherPlateau() {
        ihm.afficherPlateauOthello(jeu.getPlateau(), jeu.getTaille());
    }

    @Override
    protected String demanderCoup(Joueur joueur) {
        return ihm.demanderCoupOthello(joueur);
    }

    @Override
    protected StrategieIA choisirStrategieIa() {
        String choix = ihm.demanderChoisirIa();
        switch(choix){
            case "1":
                return new StrategieAleatoire();
            case "2":
                return new StrategieMiniMax();
            default:
                return new StrategieAleatoire();
        }
    }

    @Override
    protected void jouerIa() {
        String couleurIa = joueurActuel.getCouleur();
        this.coor = joueurActuel.getStrategieIA().calculerCoup((JeuOthello) jeu, couleurIa);
        jeu.jouerCoup(coor[0], coor[1], joueurActuel.getCouleur());
        ihm.afficherCoupIa(coor);
    }
} 