/**
 * Contrôleur spécifique au jeu Awale.
 * Gère la logique du jeu Awale et l'interaction avec l'utilisateur.
 * Note : L'IA n'est pas supportée pour ce jeu.
 */
package controleur;

import modele.Jeu;
import modele.JeuAwale;
import modele.Joueur;
import modele.ia.StrategieIA;
import vue.Ihm;

public class ControleurAwale extends ControleurJeu {

    /**
     * Constructeur du contrôleur Awale.
     * @param ihm L'interface homme-machine utilisée pour l'interaction avec l'utilisateur
     */
    public ControleurAwale(Ihm ihm) {
        super(ihm);
    }

    /**
     * Crée une nouvelle instance du jeu Awale.
     * @return Une nouvelle instance de JeuAwale
     */
    @Override
    protected Jeu creerJeu() {
        return new JeuAwale();
    }

    /**
     * Interprète le coup joué par le joueur dans le jeu Awale.
     * Le coup doit être un chiffre entre 1 et 6 représentant le trou choisi.
     * @param coup Le coup joué sous forme de chaîne de caractères
     * @param joueur Le joueur qui effectue le coup
     * @return true si le coup est valide et a été joué, false sinon
     */
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

    /**
     * Affiche le plateau de jeu Awale avec les scores des joueurs.
     */
    @Override
    protected void afficherPlateau() {
        ihm.afficherPlateauAwale(jeu.getPlateau(), jeu.getScoreNoir(), jeu.getScoreBlanc(), joueur1.toString(), joueur2.toString());
    }

    /**
     * Demande au joueur de jouer un coup dans le jeu Awale.
     * @param joueur Le joueur qui doit jouer
     * @return Le coup choisi par le joueur
     */
    @Override
    protected String demanderCoup(Joueur joueur) {
        return ihm.demanderCoupAwale(joueur.toString());
    }

    /**
     * Retourne null car l'Awale ne supporte pas l'IA.
     * @return null
     */
    @Override
    protected StrategieIA choisirStrategieIa() {
        // L'Awalé ne supporte pas l'IA pour l'instant
        return null;
    }

    /**
     * Méthode vide car l'Awale ne supporte pas l'IA.
     */
    @Override
    protected void jouerIa() {
        // L'Awalé ne supporte pas l'IA pour l'instant
    }

    /**
     * Vérifie si un joueur peut jouer un coup dans le jeu Awale.
     * @param joueur Le joueur à vérifier
     * @return true si le joueur peut jouer, false sinon
     */
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