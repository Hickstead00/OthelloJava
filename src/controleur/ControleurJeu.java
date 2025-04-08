/**
 * Classe abstraite représentant le contrôleur de base pour tous les jeux.
 * Gère la logique commune à tous les jeux comme l'initialisation des joueurs,
 * le déroulement d'une partie et la gestion des tours.
 */
package controleur;

import vue.Ihm;
import modele.Jeu;
import modele.Joueur;
import modele.ia.StrategieIA;

public abstract class ControleurJeu {
    protected Ihm ihm;
    protected Jeu jeu;
    protected Joueur joueur1;
    protected Joueur joueur2;
    protected Joueur joueurActuel;
    protected boolean jouerContreIA = false;
    
    /**
     * Constructeur du contrôleur de jeu.
     * @param ihm L'interface homme-machine utilisée pour l'interaction avec l'utilisateur
     */
    public ControleurJeu(Ihm ihm) {
        this.ihm = ihm;
        this.jeu = creerJeu();
    }

    /**
     * Méthode abstraite pour créer une instance du jeu spécifique.
     * @return Une nouvelle instance du jeu
     */
    protected abstract Jeu creerJeu();

    /**
     * Méthode abstraite pour interpréter un coup joué.
     * @param coup Le coup joué sous forme de chaîne de caractères
     * @param joueur Le joueur qui effectue le coup
     * @return true si le coup est valide et a été joué, false sinon
     */
    protected abstract boolean interpreterCoup(String coup, Joueur joueur);

    /**
     * Méthode abstraite pour afficher le plateau de jeu.
     */
    protected abstract void afficherPlateau();

    /**
     * Méthode abstraite pour demander un coup au joueur.
     * @param joueur Le joueur qui doit jouer
     * @return Le coup choisi par le joueur
     */
    protected abstract String demanderCoup(Joueur joueur);
    
    /**
     * Méthode principale qui gère le déroulement d'une partie.
     * Gère les tours de jeu et la possibilité de jouer plusieurs parties.
     */
    public void jouer() {
        initPartie();
        boolean continuerJeu = true;
        
        while (continuerJeu) {
            if (jouerContreIA) {
                jouerPartieIA();
            } else {
                jouerPartie();
            }
            
            String reponse;
            boolean reponseValide = false;
            while (!reponseValide) {
                reponse = ihm.demanderNouvellePartie();
                if (reponse.equals("O")) {
                    reponseValide = true;
                    this.jeu = creerJeu();
                    joueurActuel = joueur1;
                } else if (reponse.equals("N")) {
                    reponseValide = true;
                    continuerJeu = false;
                } else {
                    ihm.afficherReponseInvalide();
                }
            }
        }
        ihm.afficherStatistiques(joueur1.toString(), joueur2.toString(), joueur1.getNbVictoires(), joueur2.getNbVictoires(), Joueur.getNbEgalites());
    }

    /**
     * Initialise une nouvelle partie en créant les joueurs.
     */
    protected void initPartie() {
        this.joueur1 = new Joueur(ihm.demanderJoueur(1), jeu.getCouleurJ1());
        if(jeu.supporteIA() && ihm.demanderJouerContreIA().equals("O")){
            this.joueur2 = new Joueur("Ordinateur", jeu.getCouleurJ2());
            joueur2.setStrategieIA(choisirStrategieIa());
            joueur2.setEstUneIA();
            jouerContreIA = true;
        }
        else {
            this.joueur2 = new Joueur(ihm.demanderJoueur(2), jeu.getCouleurJ2());
        }
        joueurActuel = joueur1;
    }
    
    /**
     * Méthode abstraite pour choisir la stratégie de l'IA.
     * @return La stratégie IA sélectionnée
     */
    protected abstract StrategieIA choisirStrategieIa();

    /**
     * Gère le déroulement d'une partie entre deux joueurs humains.
     */
    protected void jouerPartie() {
        while (!jeu.estPartieTerminee()) {
            afficherPlateau();
            if (peutJouer(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            } else {
                ihm.afficherAucunCoupPossible(joueurActuel.toString());
                String reponse = demanderCoup(joueurActuel);
                if (reponse.equals("P")) {
                    joueurActuel = changerJoueur();
                } else {
                    ihm.afficherDoitPasser();
                }
            }
        }
        afficherPlateau();
        terminerPartie();
    }
    
    /**
     * Gère le déroulement d'une partie contre l'IA.
     */
    protected void jouerPartieIA() {
        while (!jeu.estPartieTerminee()) {
            afficherPlateau();
            if (peutJouer(joueurActuel) && joueurActuel.getEstUneIA()) {
                jouerIa();
                joueurActuel = changerJoueur();
            } else if (peutJouer(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            } else if (!peutJouer(joueurActuel) && joueurActuel.getEstUneIA()) {
                ihm.afficherIaPasse();
                joueurActuel = changerJoueur();
            } else {
                ihm.afficherAucunCoupPossible(joueurActuel.toString());
                String reponse = demanderCoup(joueurActuel);
                if (reponse.equals("P")) {
                    joueurActuel = changerJoueur();
                } else {
                    ihm.afficherDoitPasser();
                }
            }
        }
        afficherPlateau();
        terminerPartie();
    }
    
    /**
     * Méthode abstraite pour faire jouer l'IA.
     */
    protected abstract void jouerIa();

    /**
     * Vérifie si un joueur peut jouer un coup.
     * @param joueur Le joueur à vérifier
     * @return true si le joueur peut jouer, false sinon
     */
    protected boolean peutJouer(Joueur joueur) {
        for(int i = 0; i < jeu.getTaille(); i++){
            for(int j = 0; j < jeu.getTaille(); j++){
                if (jeu.verifCoup(i, j, joueur.getCouleur())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gère le déroulement d'un coup pour un joueur.
     * @param joueur Le joueur qui doit jouer
     */
    protected void jouerUnCoup(Joueur joueur) {
        boolean coupValide = false;
        while(!coupValide) {
            String coup = demanderCoup(joueurActuel);
            
            if (coup.equals("P")) {
                if (!peutJouer(joueurActuel)) {
                    coupValide = true;
                    joueurActuel = changerJoueur();
                } else {
                    ihm.afficherPassageImpossible();
                }
            }
            else if (interpreterCoup(coup, joueurActuel)) {
                coupValide = true;
                joueurActuel = changerJoueur();
            }
        }
    }

    /**
     * Change le joueur actuel.
     * @return Le nouveau joueur actuel
     */
    protected Joueur changerJoueur() {
        return (joueurActuel == joueur1) ? joueur2 : joueur1;
    }

    /**
     * Termine la partie en affichant les scores et le vainqueur.
     */
    protected void terminerPartie() {
        int scoreJ1 = jeu.getScoreNoir();
        int scoreJ2 = jeu.getScoreBlanc();
        
        ihm.afficherScoreFinal(joueur1.toString(), scoreJ1, joueur2.toString(), scoreJ2);

        Joueur vainqueur = jeu.determinerVainqueur(joueur1, joueur2);
        if (vainqueur != null) {
            vainqueur.incrementerVictoires();
            ihm.afficherVainqueur(vainqueur.toString());
        } else {
            Joueur.incrementerEgalites();
            ihm.afficherEgalite();
        }
    }
} 