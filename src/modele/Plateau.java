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

    // ta méthode VerifCoup
    // ta méthode VerifLigne
    // ta méthode retournerPion
    // Une méthode JouerCoup qui fait appel à retournerPion et verif direction en parcourant le plateau sur chaque verif direction et si c'est ok retourne les pions ?
    // Une methode compterPion pour compter les pions de chaque couleur pour le calcul du score


}


