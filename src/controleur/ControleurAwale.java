package controleur;

import modele.Jeu;
import modele.JeuAwale;
import modele.Joueur;
import modele.ia.StrategieIA;
import vue.Ihm;

public class ControleurAwale extends ControleurJeu {

    public ControleurAwale(Ihm ihm) {
        super(ihm);
    }

    @Override
    protected Jeu creerJeu() {
        return new JeuAwale();
    }

    @Override
    protected boolean interpreterCoup(String coup, Joueur joueur) {
        if (coup.length() == 1 && Character.isDigit(coup.charAt(0))) {
            int trou = Character.getNumericValue(coup.charAt(0)) - 1;
            int ligne = (joueur.getCouleur().equals(jeu.getCouleurJ1())) ? 1 : 0;
            
            if (jeu.verifCoup(ligne, trou, joueur.getCouleur())) {
                jeu.jouerCoup(ligne, trou, joueur.getCouleur());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void afficherPlateau() {
        ihm.afficherPlateauAwale(jeu.getPlateau(), jeu.getScoreNoir(), jeu.getScoreBlanc());
    }

    @Override
    protected String demanderCoup(Joueur joueur) {
        return ihm.demanderCoupAwale(joueur);
    }

    @Override
    protected StrategieIA choisirStrategieIa() {
        // L'Awalé ne supporte pas l'IA pour l'instant
        return null;
    }

    @Override
    protected void jouerIa() {
        // L'Awalé ne supporte pas l'IA pour l'instant
    }

    @Override
    protected boolean peutJouer(Joueur joueur) {
        int ligne = (joueur.getCouleur().equals(jeu.getCouleurJ1())) ? 1 : 0;
        for(int j = 0; j < jeu.getTaille(); j++){
            if (jeu.verifCoup(ligne, j, joueur.getCouleur())){
                return true;
            }
        }
        return false;
    }
} 