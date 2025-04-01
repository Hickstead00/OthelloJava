package controleur;

import modele.Jeu;
import modele.JeuOthello;
import modele.Joueur;
import vue.Ihm;

public class ControleurOthello implements ControleurJeu {
    private Jeu jeu;

    public ControleurOthello(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void jouerUnCoup(Joueur joueur, String coup) {
        int ligne = coup.charAt(0) - '1';
        int colonne = coup.charAt(2) - 'A';
        jeu.jouerCoup(ligne, colonne, joueur.getCouleur());
    }

    @Override
    public boolean peutJouer(Joueur joueur) {
        for(int i = 0; i < jeu.getTaille(); i++){
            for(int j = 0; j < jeu.getTaille(); j++){
                if (jeu.verifCoup(i, j, joueur.getCouleur())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void afficherPlateau(Ihm ihm) {
        ihm.afficherPlateauOthello(jeu.getPlateau(), jeu.getTaille());
    }

    @Override
    public String demanderCoup(Joueur joueur, Ihm ihm) {
        return ihm.demanderCoupOthello(joueur);
    }

    @Override
    public boolean estCoupValide(String coup, Joueur joueur) {
        if (coup.length() == 3 && coup.charAt(1) == ' ') {
            int ligne = coup.charAt(0) - '1';
            int colonne = coup.charAt(2) - 'A';
            
            return jeu.verifCoup(ligne, colonne, joueur.getCouleur());
        }
        return false;
    }

    @Override
    public void afficherFormatCoupInvalide(Ihm ihm) {
        ihm.afficherFormatCoupInvalideOthello();
    }

    @Override
    public boolean estPartieTerminee() {
        return jeu.estPartieTerminee();
    }
} 