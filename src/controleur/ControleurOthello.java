/**
 * Contrôleur spécifique au jeu Othello.
 * Gère la logique du jeu Othello et l'interaction avec l'utilisateur.
 */
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

    /**
     * Constructeur du contrôleur Othello.
     * @param ihm L'interface homme-machine utilisée pour l'interaction avec l'utilisateur
     */
    public ControleurOthello(Ihm ihm) {
        super(ihm);
    }

    /**
     * Crée une nouvelle instance du jeu Othello.
     * @return Une nouvelle instance de JeuOthello
     */
    @Override
    protected Jeu creerJeu() {
        return new JeuOthello();
    }

    /**
     * Interprète le coup joué par le joueur.
     * @param coup Le coup joué sous forme de chaîne de caractères
     * @param joueur Le joueur qui effectue le coup
     * @return true si le coup est valide et a été joué, false sinon
     */
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

    /**
     * Affiche le plateau de jeu Othello.
     */
    @Override
    protected void afficherPlateau() {
        ihm.afficherPlateauOthello(jeu.getPlateau(), jeu.getTaille());
    }

    /**
     * Demande au joueur de jouer un coup.
     * @param joueur Le joueur qui doit jouer
     * @return Le coup choisi par le joueur
     */
    @Override
    protected String demanderCoup(Joueur joueur) {
        return ihm.demanderCoupOthello(joueur.toString());
    }

    /**
     * Permet de choisir la stratégie de l'IA.
     * @return La stratégie IA sélectionnée
     */
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

    /**
     * Fait jouer l'IA en calculant et exécutant son coup.
     */
    @Override
    protected void jouerIa() {
        String couleurIa = joueurActuel.getCouleur();
        this.coor = joueurActuel.getStrategieIA().calculerCoup((JeuOthello) jeu, couleurIa);
        jeu.jouerCoup(coor[0], coor[1], joueurActuel.getCouleur());
        ihm.afficherCoupIa(coor);
    }
} 