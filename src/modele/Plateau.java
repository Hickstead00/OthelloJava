package modele;

public class Plateau {
    private Pion[][] plateau;
    private final int TAILLE_JEU;

    // Caractères pour représenter les pions et cases
    private static final char PION_NOIR = '○';
    private static final char PION_BLANC = '●';
    private static final char CASE_VIDE = '□';

    public Plateau(int tailleJeu) {
        this.TAILLE_JEU = tailleJeu;
        plateau = new Pion[TAILLE_JEU][TAILLE_JEU];
        initialiserPlateau();
    }


    public Pion[][] getPlateau() {
        return plateau;
    }

    public Pion getPion(Coordonnee coordonnee) {
        int x = coordonnee.getX();
        int y = coordonnee.getY();
        return plateau[x][y];
    }

    public int getTailleJeu() {
        return TAILLE_JEU;
    }

    public char getCaseVide() {
        return CASE_VIDE;
    }

    public char getPionNoir() {
        return PION_NOIR;
    }

    public char getPionBlanc() {
        return PION_BLANC;
    }


    private void initialiserPlateau() {
        // Initialiser toutes les cases avec des cases vides
        for (int i = 0; i < TAILLE_JEU; i++) {
            for (int j = 0; j < TAILLE_JEU; j++) {
                plateau[i][j] = new Pion(new Coordonnee(i, j), CASE_VIDE);
            }
        }

        // Position initiale des pions
        int milieu = TAILLE_JEU / 2 - 1;
        plateau[milieu][milieu] = new Pion(new Coordonnee(milieu, milieu), PION_BLANC);
        plateau[milieu][milieu + 1] = new Pion(new Coordonnee (milieu, milieu + 1), PION_NOIR);
        plateau[milieu + 1][milieu] = new Pion(new Coordonnee (milieu + 1, milieu), PION_NOIR);
        plateau[milieu + 1][milieu + 1] = new Pion(new Coordonnee (milieu + 1, milieu +1), PION_BLANC);
    }

}