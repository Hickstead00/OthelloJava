package controleur;

import modele.Jeu;
import modele.JeuAwale;
import modele.Joueur;
import vue.Ihm;

public class ControleurAwale implements ControleurJeu {
    private Jeu jeu;

    public ControleurAwale(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void jouerUnCoup(Joueur joueur, String coup) {
        int trou = Character.getNumericValue(coup.charAt(0)) - 1;
        int ligne = (joueur.getCouleur().equals(jeu.getCouleurJ1())) ? 1 : 0;
        jeu.jouerCoup(ligne, trou, joueur.getCouleur());
    }

    @Override
    public boolean peutJouer(Joueur joueur) {
        int ligne = (joueur.getCouleur().equals(jeu.getCouleurJ1())) ? 1 : 0;
        for(int j = 0; j < jeu.getTaille(); j++){
            if (jeu.verifCoup(ligne, j, joueur.getCouleur())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void afficherPlateau(Ihm ihm) {
        ihm.afficherPlateauAwale(jeu.getPlateau(), jeu.getScoreNoir(), jeu.getScoreBlanc());
    }

    @Override
    public String demanderCoup(Joueur joueur, Ihm ihm) {
        return ihm.demanderCoupAwale(joueur);
    }

    @Override
    public boolean estCoupValide(String coup, Joueur joueur) {
        if (coup.length() == 1 && Character.isDigit(coup.charAt(0))) {
            int trou = Character.getNumericValue(coup.charAt(0)) - 1;
            int ligne = (joueur.getCouleur().equals(jeu.getCouleurJ1())) ? 1 : 0;
            
            return jeu.verifCoup(ligne, trou, joueur.getCouleur());
        }
        return false;
    }

    @Override
    public void afficherFormatCoupInvalide(Ihm ihm) {
        ihm.afficherFormatCoupInvalideAwale();
    }

    @Override
    public boolean estPartieTerminee() {
        return jeu.estPartieTerminee();
    }
} 