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
    public boolean verifCoup(int ligne, int colonne, String couleur) {
        // Vérifier si le coup est dans les limites
        if (!estDansLesLimites(ligne, colonne)) {
            return false;
        }
        
        // Vérifier si le joueur joue de son côté
        if ((couleur.equals(COULEUR_J1) && ligne != 1) || 
            (couleur.equals(COULEUR_J2) && ligne != 0)) {
            return false;
        }
        
        // Vérifier si le trou n'est pas vide
        if (plateau[ligne][colonne].equals(CASE_VIDE) || 
            Integer.parseInt(plateau[ligne][colonne]) == 0) {
            return false;
        }
        
        // Vérifier si le coup n'affame pas l'adversaire
        if (affameraitAdversaire(ligne, colonne)) {
            return false;
        }
        
        return true;
    }

    private boolean affameraitAdversaire(int ligne, int colonne) {
        // Créer une copie du plateau pour simuler le coup
        String[][] plateauCopie = new String[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                plateauCopie[i][j] = plateau[i][j];
            }
        }
        
        // Simuler le coup
        int nbGraines = Integer.parseInt(plateauCopie[ligne][colonne]);
        plateauCopie[ligne][colonne] = "0";
        
        int ligneActuelle = ligne;
        int colonneActuelle = colonne;
        
        while (nbGraines > 0) {
            // Passer au trou suivant (sens inverse des aiguilles d'une montre)
            colonneActuelle--;
            if (colonneActuelle < 0) {
                ligneActuelle = (ligneActuelle == 0) ? 1 : 0;
                colonneActuelle = 0;
            } else if (colonneActuelle >= LARGEUR) {
                ligneActuelle = (ligneActuelle == 0) ? 1 : 0;
                colonneActuelle = LARGEUR - 1;
            }
            
            // Sauter le trou de départ si on fait un tour complet
            if (ligneActuelle == ligne && colonneActuelle == colonne) {
                continue;
            }
            
            // Déposer une graine
            int valeur = Integer.parseInt(plateauCopie[ligneActuelle][colonneActuelle]);
            plateauCopie[ligneActuelle][colonneActuelle] = String.valueOf(valeur + 1);
            nbGraines--;
        }
        
        // Vérifier si l'adversaire a des graines
        int ligneAdversaire = (ligne == 0) ? 1 : 0;
        for (int j = 0; j < LARGEUR; j++) {
            if (!plateauCopie[ligneAdversaire][j].equals("0") && 
                Integer.parseInt(plateauCopie[ligneAdversaire][j]) > 0) {
                return false; // L'adversaire a au moins une graine
            }
        }
        
        return true; // L'adversaire n'a plus de graines
    }

    @Override
    public void jouerCoup(int ligne, int colonne, String couleur) {
        // Récupérer toutes les graines du trou
        int nbGraines = Integer.parseInt(plateau[ligne][colonne]);
        plateau[ligne][colonne] = "0";
        
        int ligneActuelle = ligne;
        int colonneActuelle = colonne;
        
        // Distribuer les graines
        while (nbGraines > 0) {
            // Passer au trou suivant (sens inverse des aiguilles d'une montre)
            colonneActuelle = (colonneActuelle + 1) % LARGEUR;
            if (colonneActuelle == 0) {
                ligneActuelle = (ligneActuelle == 0) ? 1 : 0;
            }
            
            // Sauter le trou de départ si on fait un tour complet
            if (ligneActuelle == ligne && colonneActuelle == colonne && nbGraines > 11) {
                continue;
            }
            
            // Déposer une graine
            int valeur = Integer.parseInt(plateau[ligneActuelle][colonneActuelle]);
            plateau[ligneActuelle][colonneActuelle] = String.valueOf(valeur + 1);
            nbGraines--;
        }
        
        // Vérifier s'il y a capture
        int joueurIndex = couleur.equals(COULEUR_J1) ? 0 : 1;
        int ligneAdversaire = (joueurIndex == 0) ? 0 : 1;
        
        // Capture et rafle
        if (ligneActuelle == ligneAdversaire) {
            rafle(ligneActuelle, colonneActuelle, joueurIndex);
        }
    }
    
    private void rafle(int ligne, int colonne, int joueurIndex) {
        int valeur = Integer.parseInt(plateau[ligne][colonne]);
        
        // Si le trou contient 2 ou 3 graines, on les capture
        if (valeur == 2 || valeur == 3) {
            greniers[joueurIndex] += valeur;
            plateau[ligne][colonne] = "0";
            
            // Vérifier si on peut continuer la rafle
            if (colonne > 0) {
                int valeurPrecedente = Integer.parseInt(plateau[ligne][colonne - 1]);
                if (valeurPrecedente == 2 || valeurPrecedente == 3) {
                    rafle(ligne, colonne - 1, joueurIndex);
                }
            }
        }
    }

    @Override
    public boolean estPartieTerminee() {
        // Vérifier si un joueur n'a plus de graines
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
        
        // Si un joueur n'a plus de graines, l'autre capture les graines restantes
        if (joueur1SansGraines || joueur2SansGraines) {
            if (joueur1SansGraines) {
                // Joueur 2 capture les graines restantes
                for (int j = 0; j < LARGEUR; j++) {
                    greniers[1] += Integer.parseInt(plateau[0][j]);
                    plateau[0][j] = "0";
                }
            } else {
                // Joueur 1 capture les graines restantes
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
