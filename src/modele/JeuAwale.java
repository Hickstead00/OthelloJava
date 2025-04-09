/**
 * Implémentation du jeu Awalé.
 * Gère la logique du jeu Awalé, y compris les règles, le plateau et le décompte des points.
 */
package modele;

public class JeuAwale implements Jeu {
    private String[][] plateau;
    private static final int LARGEUR = 6;
    private static final int HAUTEUR = 2;
    private int[] greniers; // greniers[0] pour joueur 1, greniers[1] pour joueur 2
    private final String CASE_VIDE = "0";
    private final String COULEUR_J1 = "J1";
    private final String COULEUR_J2 = "J2";

    /**
     * Constructeur par défaut qui initialise un nouveau jeu d'Awalé.
     * Crée un plateau de 2x6 cases et initialise les greniers des joueurs à 0.
     */
    public JeuAwale() {
        this.plateau = new String[HAUTEUR][LARGEUR];
        this.greniers = new int[2];
        initialiserJeu();
    }

    /**
     * Crée une copie du plateau de jeu.
     * Utilisé notamment pour simuler des coups sans modifier le plateau original.
     * 
     * @param plateau Le plateau à copier
     * @return Une copie du plateau
     */
    public String[][] copiePlateau(String[][] plateau) {
        String[][] copiePlateau = new String[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                copiePlateau[i][j] = plateau[i][j];
            }
        }
        return copiePlateau;
    }

    /**
     * Initialise le jeu avec sa configuration de départ.
     * Place 4 graines dans chaque trou et initialise les greniers à 0.
     */
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

    /**
     * Vérifie si un coup est valide selon les règles de l'Awalé.
     * Un coup est valide si le trou choisi contient des graines et appartient au joueur,
     * et si le coup ne laisse pas l'adversaire sans graines (règle de "non-affamement").
     * 
     * @param ligne La ligne du coup (0 pour J2, 1 pour J1)
     * @param colonne La colonne du coup (0-5)
     * @param couleur La couleur du joueur qui tente le coup
     * @return true si le coup est valide, false sinon
     */
    @Override
    public boolean verifCoup(int ligne,int colonne, String couleur)
    {
        if (!(estDansLesLimites(ligne,colonne)))
        {

            return false;
        }
        if ((couleur.equals(COULEUR_J1) && ligne != 1) ||
                (couleur.equals(COULEUR_J2) && ligne != 0)) {
            return false;
        }
        if (plateau[ligne][colonne].equals(CASE_VIDE))
        {
            return false;
        }
        if (siAffame(ligne,colonne,couleur)){
            System.out.println("Erreur de verification");return false;
        }

        return true;
    }

    /**
     * Joue un coup sur le plateau.
     * Distribue les graines du trou choisi dans le sens anti-horaire
     * et capture les graines adverses selon les règles du jeu.
     * 
     * @param ligne La ligne du coup (0 pour J2, 1 pour J1)
     * @param colonne La colonne du coup (0-5)
     * @param couleur La couleur du joueur qui joue le coup
     */
    public void jouerCoup(int ligne,int colonne, String couleur)
    {
        int nbGraines= Integer.parseInt(plateau[ligne][colonne]);
        plateau[ligne][colonne] = CASE_VIDE;
        int ligneActual = ligne;
        int colonneActual = colonne;

        while(nbGraines> 0)
        {
            colonneActual=(colonneActual+1)%LARGEUR;

            if (colonneActual == 0) {
                ligneActual = (ligneActual == 0) ? 1 : 0;
            }
            /*si les graines sont 12 ou + saute la case de départ*/
            if (ligneActual == ligne && colonneActual == colonne)
            {
                continue;
            }
            /*seme une graines sur la case*/
            int val=Integer.parseInt(plateau[ligneActual][colonneActual]);
            plateau[ligneActual][colonneActual] = String.valueOf(val+1);
            nbGraines--;

        }
        int ligneJoueurAdverse = (couleur.equals(COULEUR_J1) ? 0 : 1);
        if (ligneJoueurAdverse == ligneActual)
        {
            manger(ligneActual,colonneActual,couleur);
        }

    }

    /**
     * Capture les graines adverses selon les règles du jeu.
     * Si la dernière graine semée se trouve du côté adverse et forme un groupe de 2 ou 3 graines,
     * ces graines sont capturées. La rafle se poursuit tant que possible.
     * 
     * @param ligneActual La ligne où se trouve la dernière graine semée
     * @param colonneActual La colonne où se trouve la dernière graine semée
     * @param couleur La couleur du joueur qui a joué
     */
    private void manger(int ligneActual, int colonneActual, String couleur) {
    int idjoueur = couleur.equals(COULEUR_J1) ? 0 : 1;
    int nbGrainesCase = Integer.parseInt(plateau[ligneActual][colonneActual]);
    
    if (nbGrainesCase > 1 && nbGrainesCase < 4) {
        plateau[ligneActual][colonneActual] = "0";  // Correction de l'erreur equals → affectation
        greniers[idjoueur] += nbGrainesCase;
        
        // Continuer la rafle dans le sens inverse des aiguilles d'une montre
        int nextColonne = colonneActual - 1;
        if (nextColonne >= 0) {
            int prochainNbGraines = Integer.parseInt(plateau[ligneActual][nextColonne]);
            if (prochainNbGraines > 1 && prochainNbGraines < 4) {
                manger(ligneActual, nextColonne, couleur);
            }
        }
    }
}

    /**
     * Vérifie si un coup proposé affamerait l'adversaire.
     * La règle interdit de jouer un coup qui laisserait l'adversaire sans aucune graine.
     * 
     * @param ligne La ligne du coup proposé
     * @param colonne La colonne du coup proposé
     * @param couleur La couleur du joueur qui tente le coup
     * @return true si le coup affame l'adversaire, false sinon
     */
    private boolean siAffame(int ligne, int colonne, String couleur) {
    // Copie du plateau pour simulation
    String[][] copiePlateau = copiePlateau(plateau);
    int nbGraines = Integer.parseInt(copiePlateau[ligne][colonne]);
    copiePlateau[ligne][colonne] = CASE_VIDE;
    
    // Simulation de la distribution des graines
    int ligneActual = ligne;
    int colonneActual = colonne;
    
    while(nbGraines > 0) {
        colonneActual = (colonneActual + 1) % LARGEUR;
        if (colonneActual == 0) {
            ligneActual = (ligneActual == 0) ? 1 : 0;
        }
        // Saut de la case de départ si on fait un tour complet (avec 12+ graines)
        if (nbGraines > 11 && ligneActual == ligne && colonneActual == colonne) {
            continue;
        }
        // Sème une graine
        int val = Integer.parseInt(copiePlateau[ligneActual][colonneActual]);
        copiePlateau[ligneActual][colonneActual] = String.valueOf(val + 1);
        nbGraines--;
    }
    
    // Vérifie si l'adversaire a au moins une graine
    int idAdverse = couleur.equals(COULEUR_J2) ? 0 : 1;
    boolean adversaireAffame = true;
    
    for (int i = 0; i < LARGEUR; i++) {
        if (Integer.parseInt(copiePlateau[idAdverse][i]) > 0) {
            adversaireAffame = false;
            break;
        }
    }
    
    return adversaireAffame;  // Retourne true si l'adversaire n'a aucune graine
}


    /**
     * Récupère l'état actuel du plateau pour une instance du jeu Awalé.
     * 
     * @param jeu L'instance du jeu Awalé
     * @return Le plateau de jeu
     */
    public String[][] getPlateau(JeuAwale jeu){
        return plateau;
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie se termine si un joueur n'a plus de graines sur son côté du plateau.
     * Les graines restantes sont alors ajoutées au grenier de l'autre joueur.
     * 
     * @return true si la partie est terminée, false sinon
     */
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

    /**
     * Détermine le vainqueur de la partie.
     * Le vainqueur est le joueur qui a le plus de graines dans son grenier.
     * 
     * @param joueurNoir Le joueur 1
     * @param joueurBlanc Le joueur 2
     * @return Le joueur vainqueur, ou null en cas d'égalité
     */
    @Override
    public Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc) {
        if (greniers[0] > greniers[1]) {
            return joueurNoir; // Joueur 1
        } else if (greniers[1] > greniers[0]) {
            return joueurBlanc; // Joueur 2
        }
        return null; // Égalité
    }

    /**
     * Récupère la couleur du premier joueur.
     * 
     * @return La couleur du premier joueur
     */
    @Override
    public String getCouleurJ1() {
        return COULEUR_J1;
    }

    /**
     * Récupère la couleur du second joueur.
     * 
     * @return La couleur du second joueur
     */
    @Override
    public String getCouleurJ2() {
        return COULEUR_J2;
    }

    /**
     * Récupère la taille du plateau (nombre de trous par ligne).
     * 
     * @return La largeur du plateau
     */
    @Override
    public int getTaille() {
        return LARGEUR; // On retourne la largeur pour la compatibilité
    }

    /**
     * Récupère l'état actuel du plateau de jeu.
     * 
     * @return Le plateau de jeu sous forme de tableau 2D
     */
    @Override
    public String[][] getPlateau() {
        return plateau;
    }

    /**
     * Récupère le score du joueur 1 (nombre de graines dans son grenier).
     * 
     * @return Le score du joueur 1
     */
    @Override
    public int getScoreNoir() {
        return greniers[0]; // Score du joueur 1
    }

    /**
     * Récupère le score du joueur 2 (nombre de graines dans son grenier).
     * 
     * @return Le score du joueur 2
     */
    @Override
    public int getScoreBlanc() {
        return greniers[1]; // Score du joueur 2
    }


    /**
     * Indique si le jeu supporte le mode contre l'IA.
     * Actuellement, l'Awalé ne supporte pas l'IA.
     * 
     * @return false car l'Awalé ne supporte pas l'IA
     */
    @Override
    public boolean supporteIA() {
        return false; // Pour l'instant, l'Awalé ne supporte pas l'IA
    }
    
    /**
     * Vérifie si des coordonnées sont dans les limites du plateau.
     * 
     * @param ligne La ligne à vérifier
     * @param colonne La colonne à vérifier
     * @return true si les coordonnées sont valides, false sinon
     */
    private boolean estDansLesLimites(int ligne, int colonne) {
        return ligne >= 0 && ligne < HAUTEUR && colonne >= 0 && colonne < LARGEUR;
    }
}
