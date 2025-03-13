package modele;

import java.util.ArrayList;

public class Plateau {
    private String[][] plateau;
    private static final int TAILLE_PLATEAU = 4; // doit être pair pour que le placement de départ soit correct
    private final String couleurNoire = "\u26AB";
    private final String couleurBlanc = "\u26AA";
    private final String caseVide = "\uD83D\uDFE9";
    private int scoreNoir;
    private int scoreBlanc;

    public Plateau() {
        this.plateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        initialiser();
    }

    public Plateau(Plateau copie){
        this.plateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                this.plateau[i][j] = copie.plateau[i][j];
            }
        }
    }

    public int getTaille() {
        return TAILLE_PLATEAU;
    }

    public String[][] getPlateau() {
        return plateau;
    }

    public String getCouleurNoire() {
        return couleurNoire;
    }

    public String getCouleurBlanc() {
        return couleurBlanc;
    }

    // Initialise le plateau à cases vides puis en determine le millieu de manière a ce que l'on ai qu'à changer le
    // parametre de la taille du plateau dans toute l'application
    private void initialiser(){
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                plateau[i][j] = caseVide;
            }
        }

        int millieu = TAILLE_PLATEAU / 2;
        plateau[millieu-1][millieu-1] = couleurBlanc;
        plateau[millieu-1][millieu] = couleurNoire;
        plateau[millieu][millieu-1] = couleurNoire;
        plateau[millieu][millieu] = couleurBlanc;
    }

    // Verification de la légalité du coup selon les règles de l'othello, case vide, adjacence à une couleur adverse..
    public boolean verifCoup(int ligne, int colonne, String couleur) {
        // La case doit être vide et dans les limites
        if (!estDansLesLimites(ligne, colonne) || !plateau[ligne][colonne].equals(caseVide)) {
            return false;
        }
        
        String couleurAdver = (couleur.equals(couleurNoire)) ? couleurBlanc : couleurNoire;
        
        // Pour chaque direction on va vérifier les lignes/colonne/diagonales
        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] direction : directions) {
            if (verifLigneCoup(ligne, colonne, direction[0], direction[1], couleurAdver)){
                return true;  // Si au moins une direction permet une capture
            }
        }
        return false;
    }




    // Méthode appelée pour chaque ligne colonne et diagonale afin de vérifier s'il existe au minimum un pion adverse
    // a capturer si oui renvoie true sinon false
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

    public ArrayList<int[]> coupPossible(String couleur){
        ArrayList<int[]> listeCoup;
        listeCoup = new ArrayList<>();
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                if (verifCoup(i,j,couleur)){
                    listeCoup.add(new int[]{i,j});
                }
            }
        }
        return listeCoup;
    }


    // Pose un pion et appelle retournerPionDirection pour toutes les directions possibles
    public void jouerCoup(int ligne, int colonne, String couleur) {
        // On place le pion d'abord
        plateau[ligne][colonne] = couleur;

        // Puis pour chaque direction possible on apelle retournerPionDirection
        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] dir : directions) {
            retournerPionsDirection(ligne, colonne, dir[0], dir[1], couleur);
        }
    }
    
    // Retourne tout les pions possibles dans chaque direction ou l'on rencontre un pion de couleur opposé
    // s'arrête au premier.
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

    // Compte les pions de chaque couleur et retourne un tableau taille 2 avec 0 : Joueur noir et 1 : Joueur blanc
    public int[] compterPion(){
        int [] scores = new int[2];
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                if (plateau[i][j].equals(couleurNoire)) {
                    scores[0]++;
                }
                if (plateau[i][j].equals(couleurBlanc)) {
                    scores[1]++;
                }
            }
        }
        return scores;
    }

    public boolean estDansLesLimites(int ligne, int colonne) {
        return ligne >=0 && ligne < TAILLE_PLATEAU && colonne >=0 && colonne < TAILLE_PLATEAU;
    }

    // Parcours le plateau et met jour les scores de chaque couleur
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

    public int getScoreNoir() {
        mettreAJourScores();
        return scoreNoir;
    }

    public int getScoreBlanc() {
        mettreAJourScores();
        return scoreBlanc;
    }

    public Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc) {
        mettreAJourScores();
        if (scoreNoir > scoreBlanc) {
            return joueurNoir;
        } else if (scoreBlanc > scoreNoir) {
            return joueurBlanc;
        }
        return null; // En cas d'égalité
    }

}


