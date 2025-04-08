/**
 * Interface définissant le comportement d'un jeu de plateau.
 * Toute implémentation de jeu doit respecter cette interface pour pouvoir être utilisée dans l'application.
 */
package modele;

public interface Jeu {
    /**
     * Initialise le jeu avec sa configuration de départ.
     */
    void initialiserJeu();

    /**
     * Vérifie si un coup est valide selon les règles du jeu.
     * @param x La coordonnée x (ligne) du coup
     * @param y La coordonnée y (colonne) du coup
     * @param couleur La couleur du joueur qui tente le coup
     * @return true si le coup est valide, false sinon
     */
    boolean verifCoup(int x, int y, String couleur);

    /**
     * Joue un coup sur le plateau.
     * @param x La coordonnée x (ligne) du coup
     * @param y La coordonnée y (colonne) du coup
     * @param couleur La couleur du joueur qui joue le coup
     */
    void jouerCoup(int x, int y, String couleur);

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est terminée, false sinon
     */
    boolean estPartieTerminee();

    /**
     * Détermine le vainqueur de la partie.
     * @param joueurNoir Le joueur ayant les pions noirs
     * @param joueurBlanc Le joueur ayant les pions blancs
     * @return Le joueur vainqueur, ou null en cas d'égalité
     */
    Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc);

    /**
     * Récupère la couleur du premier joueur.
     * @return La couleur du premier joueur
     */
    String getCouleurJ1();

    /**
     * Récupère la couleur du second joueur.
     * @return La couleur du second joueur
     */
    String getCouleurJ2();

    /**
     * Récupère la taille du plateau de jeu.
     * @return La taille du plateau
     */
    int getTaille();

    /**
     * Récupère l'état actuel du plateau de jeu.
     * @return Le plateau de jeu sous forme de tableau 2D
     */
    String[][] getPlateau();

    /**
     * Récupère le score du joueur ayant les pions noirs.
     * @return Le score des pions noirs
     */
    int getScoreNoir();

    /**
     * Récupère le score du joueur ayant les pions blancs.
     * @return Le score des pions blancs
     */
    int getScoreBlanc();

    /**
     * Récupère le score d'un joueur spécifique.
     * @param joueur Le joueur dont on veut connaître le score
     * @return Le score du joueur
     */
    public int getScoreJoueur(Joueur joueur);

    /**
     * Indique si le jeu supporte le mode contre l'IA.
     * @return true si le jeu supporte l'IA, false sinon
     */
    boolean supporteIA();
}
