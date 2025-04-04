package modele;

public class JeuAwale implements Jeu {
    private String[][] plateau;
    private static final int LARGEUR = 6;
    private static final int HAUTEUR = 2;
    private int[] greniers; // greniers[0] pour joueur 1, greniers[1] pour joueur 2
    private final String CASE_VIDE = "0";
    private final String COULEUR_J1 = "J1";
    private final String COULEUR_J2 = "J2";

    public JeuAwale() {
        this.plateau = new String[HAUTEUR][LARGEUR];
        this.greniers = new int[2];
        initialiserJeu();
    }


    public String[][] copiePlateau(String[][] plateau) {
        String[][] copiePlateau = new String[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                this.plateau[i][j] = copiePlateau[i][j];
            }
        }
        return copiePlateau;
    }

    @Override
    public void initialiserJeu() {
        // Initialiser le plateau avec 4 graines dans chaque trou
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                plateau[i][j] = "4";
            }
        }
        
        // Initialiser les greniers à 0
        greniers[0] = 0;
        greniers[1] = 0;
    }

    @Override
    public boolean verifCoup(int ligne,int colonne, String couleur)
    {
        if (!(estDansLesLimites(ligne,colonne)))
        {
            return false;
        }

        if ((couleur.equals(COULEUR_J1) && ligne !=0) || (couleur.equals(COULEUR_J2) && ligne!=1))
        {
            return false;
        }

        if (plateau[ligne][colonne].equals(CASE_VIDE))
        {
            return false;
        }


        return !(siAffame(ligne,colonne,couleur,plateau));
    }

    public void jouerCoup(int ligne,int colonne, String couleur)
    {
        int nbGraines= Integer.parseInt(plateau[ligne][colonne]);
        plateau[ligne][colonne] = CASE_VIDE;
        int ligneActual = ligne;
        int colonneActual = colonne;

        for (int i=0;i<=nbGraines;i++)
        {
            if (ligneActual == 0)
            {
                colonneActual = colonne--;
            }
            else
            {
                colonneActual = colonne++;
            }
            /*si les graines sont 12 ou + saute la case de départ*/
            if (nbGraines>11 && ligneActual == ligne && colonneActual == colonne)
            {
                continue;
            }
            /*seme une graines sur la case*/
            plateau[ligneActual][colonneActual] = String.valueOf(Integer.parseInt(plateau[ligneActual][colonneActual])+1);
            nbGraines--;

        }
        int ligneJoueurAdverse = (couleur.equals(COULEUR_J1) ? 0 : 1);
        if (ligneJoueurAdverse == ligneActual)
        {
            manger(ligneActual,colonneActual,couleur);
        }

    }

    private void manger(int ligneActual, int colonneActual, String couleur){
        int idjoueur = couleur.equals(COULEUR_J1) ? 0 : 1 ;
        int nbGrainesCase =Integer.parseInt(plateau[ligneActual][colonneActual]);
        if (nbGrainesCase>1 && nbGrainesCase<4)
        {

            plateau[ligneActual][colonneActual].equals("0");
            greniers[idjoueur] += nbGrainesCase;
            if (colonneActual > 0)
            {
                    manger(ligneActual, colonneActual - 1, couleur);
            }
        }
    }

    private boolean siAffame(int ligne,int colonne, String couleur, String[][] plateau)
    {

        String[][] copiePlateau = copiePlateau(plateau);
        int nbGraines= Integer.parseInt((copiePlateau[ligne][colonne]));
        copiePlateau[ligne][colonne] = CASE_VIDE;
        int ligneActual = ligne;
        int colonneActual = colonne;

        for (int i=0;i<=nbGraines;i++)
        {
            if (ligneActual == 0)
            {
                colonneActual = colonne--;
            }
            else
            {
                colonneActual = colonne++;
            }
            /*si les graines sont 12 ou + saute la case de départ*/
            if (nbGraines>11 && ligneActual == ligne && colonneActual == colonne)
            {
                continue;
            }
            /*seme une graines sur la case*/
            copiePlateau[ligneActual][colonneActual] = String.valueOf(Integer.parseInt(copiePlateau[ligneActual][colonneActual]+1));
            nbGraines--;

        }
        int idadverse = couleur.equals(COULEUR_J2) ? 0 : 1 ;
        for (int i=0;i<LARGEUR ;i++)
        {
            if ((Integer.parseInt(copiePlateau[idadverse][i])>0)){
                return true;
            }
        }
        return false;
    }
    public String[][] getPlateau(JeuAwale jeu){
        return plateau;
    }
    @Override
    public boolean estPartieTerminee() {

        boolean joueur1SansGraines = true;
        boolean joueur2SansGraines = true;

        for (int j = 0; j < LARGEUR; j++) {
            if (!plateau[1][j].equals("0") && Integer.parseInt(plateau[1][j]) > 0) {
                joueur1SansGraines = false;
            }
            if (!plateau[0][j].equals("0") && Integer.parseInt(plateau[0][j]) > 0) {
                joueur2SansGraines = false;
            }
        }


        if (joueur1SansGraines || joueur2SansGraines) {
            if (joueur1SansGraines) {
                for (int j = 0; j < LARGEUR; j++) {
                    greniers[1] += Integer.parseInt(plateau[0][j]);
                    plateau[0][j] = "0";
                }
            } else {
                for (int j = 0; j < LARGEUR; j++) {
                    greniers[0] += Integer.parseInt(plateau[1][j]);
                    plateau[1][j] = "0";
                }
            }
            return true;
        }

        return false;
    }


    @Override
    public Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc) {
        if (greniers[0] > greniers[1]) {
            return joueurNoir; // Joueur 1
        } else if (greniers[1] > greniers[0]) {
            return joueurBlanc; // Joueur 2
        }
        return null; // Égalité
    }

    @Override
    public String getCouleurJ1() {
        return COULEUR_J1;
    }

    @Override
    public String getCouleurJ2() {
        return COULEUR_J2;
    }

    @Override
    public int getTaille() {
        return LARGEUR; // On retourne la largeur pour la compatibilité
    }

    @Override
    public String[][] getPlateau() {
        return plateau;
    }

    @Override
    public int getScoreNoir() {
        return greniers[0]; // Score du joueur 1
    }

    @Override
    public int getScoreBlanc() {
        return greniers[1]; // Score du joueur 2
    }

    @Override
    public int getScoreJoueur(Joueur joueur) {
        if (joueur.getCouleur().equals(COULEUR_J1)) {
            return greniers[0];
        } else {
            return greniers[1];
        }
    }

    @Override
    public boolean supporteIA() {
        return false; // Pour l'instant, l'Awalé ne supporte pas l'IA
    }
    
    private boolean estDansLesLimites(int ligne, int colonne) {
        return ligne >= 0 && ligne < HAUTEUR && colonne >= 0 && colonne < LARGEUR;
    }
}
