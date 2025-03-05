package modele;

public class Plateau {
    private String[][] plateau;
    private static final int TAILLE_PLATEAU = 8;
    private final String couleurNoire = "\u26AB";
    private final String couleurBlanc = "\u26AA";
    private final String caseVide = "\uD83D\uDFE9";

    public Plateau() {
        this.plateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        initialiser();
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

    private void initialiser(){
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for (int j = 0; j < TAILLE_PLATEAU; j++) {
                plateau[i][j] = caseVide;
            }
        }

        plateau[3][3] = couleurBlanc;
        plateau[3][4] = couleurNoire;
        plateau[4][3] = couleurNoire;
        plateau[4][4] = couleurBlanc;
    }

    public boolean verifCoup(int ligne, int colonne, String couleur) {
        // La case doit être vide !
        if (!estDansLesLimites(ligne, colonne) || !plateau[ligne][colonne].equals(caseVide)) {
            return false;
        }
        
        String couleurAdver = (couleur.equals(couleurNoire)) ? couleurBlanc : couleurNoire;
        
        // Pour chaque direction
        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] direction : directions) {
            if (verifLigneCoup(ligne, colonne, direction[0], direction[1], couleurAdver)){
                return true;  // Si au moins une direction permet une capture
            }
        }
        return false;
    }

    public boolean verifLigneCoup(int ligne,int colonne, int dirX, int dirY, String couleurAdver) {
        if(!estDansLesLimites(ligne + dirX, colonne + dirY)) {
            return false;
        }

        int currentLigne = ligne + dirX;
        int currentColonne = colonne + dirY;
        boolean pionAdversePresent = false;
        String couleurActuelle = (couleurAdver.equals(couleurNoire) ? couleurBlanc : couleurNoire);

        while(estDansLesLimites(currentLigne, currentColonne)){
            String caseCourante = plateau[currentLigne][currentColonne];
            if (caseCourante.equals(caseVide)){
                return false;
            }
            if (caseCourante.equals(couleurAdver)){
                pionAdversePresent = true;
            }
            else if (caseCourante.equals(couleurActuelle)){
                return pionAdversePresent;
            }
            currentLigne = currentLigne + dirX;
            currentColonne = currentColonne + dirY;
        }
    return false;

    }

    public void jouerCoup(int ligne, int colonne, String couleur) {
        // D'abord on place le pion
        plateau[ligne][colonne] = couleur;
        
        // Pour chaque direction possible
        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] dir : directions) {
            retournerPionsDirection(ligne, colonne, dir[0], dir[1], couleur);
        }
    }

    private void retournerPionsDirection(int ligne, int colonne, int dirX, int dirY, String couleur) {
        String couleurAdver = (couleur.equals(couleurNoire)) ? couleurBlanc : couleurNoire;
        
        // Si on ne peut pas capturer dans cette direction, on sort
        if (!verifLigneCoup(ligne, colonne, dirX, dirY, couleurAdver)) {
            return;
        }
        
        // On parcourt la ligne tant qu'on trouve des pions adverses
        int currentLigne = ligne + dirX;
        int currentColonne = colonne + dirY;
        
        while (estDansLesLimites(currentLigne, currentColonne) && 
               plateau[currentLigne][currentColonne].equals(couleurAdver)) {
            // On retourne le pion
            plateau[currentLigne][currentColonne] = couleur;
            currentLigne += dirX;
            currentColonne += dirY;
        }
    }

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

    // ta méthode VerifCoup
    // ta méthode VerifLigne
    // ta méthode retournerPion
    // Une méthode JouerCoup qui fait appel à retournerPion et verif direction en parcourant le plateau sur chaque verif direction et si c'est ok retourne les pions ?
    // Une methode compterPion pour compter les pions de chaque couleur pour le calcul du score


}


