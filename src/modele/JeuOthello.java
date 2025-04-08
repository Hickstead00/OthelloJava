/**
 * Implémentation du jeu Othello.
 * Gère la logique du jeu Othello, y compris les règles, le plateau et le décompte des points.
 */
package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class JeuOthello implements Jeu{
    private String[][] plateau;
    private static final int TAILLE_PLATEAU = 4; // doit être pair pour que le placement de départ soit correct
    private final String couleurNoire = "\u26AB";
    private final String couleurBlanc = "\u26AA";
    private final String caseVide = "\uD83D\uDFE9";
    private int scoreNoir;
    private int scoreBlanc;
    HashMap<String, Integer> arbre = new HashMap<>();

    /**
     * Constructeur par défaut qui initialise un nouveau jeu d'Othello.
     */
    public JeuOthello() {
        this.plateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        initialiserJeu();
    }

    /**
     * Constructeur de copie qui crée une nouvelle instance à partir d'une autre.
     * @param copie Le jeu Othello à copier
     */
    public JeuOthello(JeuOthello copie) {
        this.plateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                this.plateau[i][j] = copie.plateau[i][j];
            }
        }
    }

    /**
     * Récupère la taille du plateau.
     * @return La taille du plateau
     */
    public int getTaille() {
        return TAILLE_PLATEAU;
    }

    /**
     * Récupère l'état actuel du plateau.
     * @return Le plateau de jeu
     */
    public String[][] getPlateau() {
        return plateau;
    }

    /**
     * Récupère la couleur du premier joueur (noir).
     * @return La couleur du premier joueur
     */
    public String getCouleurJ1() {
        return couleurNoire;
    }

    /**
     * Récupère la couleur du second joueur (blanc).
     * @return La couleur du second joueur
     */
    public String getCouleurJ2() {
        return couleurBlanc;
    }

    /**
     * Initialise le plateau avec la configuration de départ du jeu Othello.
     * Place les pions initiaux au centre du plateau.
     */
    @Override
    public void initialiserJeu() {
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                plateau[i][j] = caseVide;
            }
        }

        int millieu = TAILLE_PLATEAU / 2;
        plateau[millieu - 1][millieu - 1] = couleurBlanc;
        plateau[millieu - 1][millieu] = couleurNoire;
        plateau[millieu][millieu - 1] = couleurNoire;
        plateau[millieu][millieu] = couleurBlanc;
    }

    /**
     * Vérifie si un coup est valide selon les règles de l'Othello.
     * @param ligne La ligne du coup
     * @param colonne La colonne du coup
     * @param couleur La couleur du joueur
     * @return true si le coup est valide, false sinon
     */
    @Override
    public boolean verifCoup(int ligne, int colonne, String couleur) {
        // La case doit être vide et dans les limites
        if (!estDansLesLimites(ligne, colonne) || !plateau[ligne][colonne].equals(caseVide)) {
            return false;
        }

        String couleurAdver = (couleur.equals(couleurNoire)) ? couleurBlanc : couleurNoire;

        // Pour chaque direction on va vérifier les lignes/colonne/diagonales
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] direction : directions) {
            if (verifLigneCoup(ligne, colonne, direction[0], direction[1], couleurAdver)) {
                return true;  // Si au moins une direction permet une capture
            }
        }
        return false;
    }

    /**
     * Vérifie si un coup est valide dans une direction spécifique.
     * @param ligne La ligne de départ
     * @param colonne La colonne de départ
     * @param dirX La direction en x
     * @param dirY La direction en y
     * @param couleurAdver La couleur adverse
     * @return true si le coup est valide dans cette direction, false sinon
     */
    public boolean verifLigneCoup(int ligne, int colonne, int dirX, int dirY, String couleurAdver) {
        int nextLigne = ligne + dirX;
        int nextColonne = colonne + dirY;

        if (!estDansLesLimites(nextLigne, nextColonne) || !plateau[nextLigne][nextColonne].equals(couleurAdver)) {
            return false;
        }

        String couleurActuelle = (couleurAdver.equals(couleurNoire)) ? couleurBlanc : couleurNoire;
        int currentLigne = nextLigne + dirX;
        int currentColonne = nextColonne + dirY;

        // tant que l'on est dans les limites on regarde si la case adjacente n'est pas vide, puis on parcours jusqu'à
        // trouver une case de notre couleur, si c'est le cas renvoie true sinon faux (pas de pions adverses à capturer)
        while (estDansLesLimites(currentLigne, currentColonne)) {
            String caseCourante = plateau[currentLigne][currentColonne];
            if (caseCourante.equals(caseVide)) {
                return false;
            }
            if (caseCourante.equals(couleurActuelle)) {
                return true;
            }
            currentLigne += dirX;
            currentColonne += dirY;
        }
        return false;
    }

    /**
     * Récupère tous les coups possibles pour une couleur donnée.
     * @param couleur La couleur du joueur
     * @return Une liste des coordonnées des coups possibles
     */
    public ArrayList<int[]> coupPossible(String couleur) {
        ArrayList<int[]> listeCoup;
        listeCoup = new ArrayList<>();
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                if (verifCoup(i, j, couleur)) {
                    listeCoup.add(new int[]{i, j});
                }
            }
        }
        return listeCoup;
    }

    /**
     * Joue un coup sur le plateau et retourne les pions capturés.
     * @param ligne La ligne du coup
     * @param colonne La colonne du coup
     * @param couleur La couleur du joueur
     */
    @Override
    public void jouerCoup(int ligne, int colonne, String couleur) {
        // On place le pion d'abord
        plateau[ligne][colonne] = couleur;

        // Puis pour chaque direction possible on apelle retournerPionDirection
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] dir : directions) {
            retournerPionsDirection(ligne, colonne, dir[0], dir[1], couleur);
        }
    }

    /**
     * Retourne les pions dans une direction spécifique après un coup.
     * @param ligne La ligne de départ
     * @param colonne La colonne de départ
     * @param dirX La direction en x
     * @param dirY La direction en y
     * @param couleur La couleur du joueur
     */
    private void retournerPionsDirection(int ligne, int colonne, int dirX, int dirY, String couleur) {
        String couleurAdver = (couleur.equals(couleurNoire)) ? couleurBlanc : couleurNoire;

        // Si on ne peut pas capturer dans cette direction, on sort
        if (!verifLigneCoup(ligne, colonne, dirX, dirY, couleurAdver)) {
            return;
        }

        // On parcourt la ligne tant qu'on trouve des pions adverses
        int currentLigne = ligne + dirX;
        int currentColonne = colonne + dirY;

        while (estDansLesLimites(currentLigne, currentColonne) && plateau[currentLigne][currentColonne].equals(couleurAdver)) {
            // On retourne le pion
            plateau[currentLigne][currentColonne] = couleur;
            currentLigne += dirX;
            currentColonne += dirY;
        }
    }


    /**
     * Vérifie si des coordonnées sont dans les limites du plateau.
     * @param ligne La ligne à vérifier
     * @param colonne La colonne à vérifier
     * @return true si les coordonnées sont valides, false sinon
     */
    public boolean estDansLesLimites(int ligne, int colonne) {
        return ligne >= 0 && ligne < TAILLE_PLATEAU && colonne >= 0 && colonne < TAILLE_PLATEAU;
    }

    /**
     * Met à jour les scores des joueurs en comptant les pions sur le plateau.
     */
    public void mettreAJourScores() {
        scoreNoir = 0;
        scoreBlanc = 0;
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                if (plateau[i][j].equals(couleurNoire)) {
                    scoreNoir++;
                } else if (plateau[i][j].equals(couleurBlanc)) {
                    scoreBlanc++;
                }
            }
        }
    }

    /**
     * Récupère le score du joueur noir en comptant le nombre de pions noirs sur le plateau.
     * @return Le score du joueur noir
     */
    public int getScoreNoir() {
        mettreAJourScores();
        return scoreNoir;
    }

    /**
     * Récupère le score du joueur blanc en comptant le nombre de pions blancs sur le plateau.
     * @return Le score du joueur blanc
     */
    public int getScoreBlanc() {
        mettreAJourScores();
        return scoreBlanc;
    }

    /**
     * Détermine le vainqueur de la partie en comparant les scores des deux joueurs.
     * @param joueurNoir Le joueur ayant les pions noirs
     * @param joueurBlanc Le joueur ayant les pions blancs
     * @return Le joueur vainqueur, ou null en cas d'égalité
     */
    @Override
    public Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc) {
        mettreAJourScores();
        if (scoreNoir > scoreBlanc) {
            return joueurNoir;
        } else if (scoreBlanc > scoreNoir) {
            return joueurBlanc;
        }
        return null; // En cas d'égalité
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie est terminée lorsqu'aucun des deux joueurs ne peut jouer de coup valide.
     * @return true si la partie est terminée, false sinon
     */
    @Override
    public boolean estPartieTerminee() {
        return (coupPossible(couleurBlanc).isEmpty() && coupPossible(couleurNoire).isEmpty());
    }

    /**
     * Indique si le jeu supporte le mode contre l'IA.
     * @return true car le jeu Othello supporte l'IA
     */
    @Override
    public boolean supporteIA(){
        return true;
    }

}


