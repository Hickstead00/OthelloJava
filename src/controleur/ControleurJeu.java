package controleur;

import modele.Jeu;
import modele.Joueur;
import vue.Ihm;

public interface ControleurJeu {
    void jouerUnCoup(Joueur joueur, String coup);
    boolean peutJouer(Joueur joueur);
    void afficherPlateau(Ihm ihm);
    String demanderCoup(Joueur joueur, Ihm ihm);
    boolean estCoupValide(String coup, Joueur joueur);
    void afficherFormatCoupInvalide(Ihm ihm);
    boolean estPartieTerminee();
} 