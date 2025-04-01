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
    
    public ControleurJeu(Ihm ihm) {
        this.ihm = ihm;
        this.jeu = creerJeu();
    }

    protected abstract Jeu creerJeu(); // Chaque jeu définit son propre jeu
    protected abstract boolean interpreterCoup(String coup, Joueur joueur);
    protected abstract void afficherPlateau();
    protected abstract String demanderCoup(Joueur joueur);
    
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
        ihm.afficherStatistiques(joueur1, joueur2);
    }

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
    
    protected abstract StrategieIA choisirStrategieIa();

    protected void jouerPartie() {
        while (!jeu.estPartieTerminee()) {
            afficherPlateau();
            if (peutJouer(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            } else {
                ihm.afficherAucunCoupPossible(joueurActuel);
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
                ihm.afficherAucunCoupPossible(joueurActuel);
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
    
    protected abstract void jouerIa();

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
            else {
                ihm.afficherCoupInvalide();
            }
        }
    }

    protected Joueur changerJoueur() {
        return (joueurActuel == joueur1) ? joueur2 : joueur1;
    }

    protected void terminerPartie() {
        int scoreJ1 = jeu.getScoreNoir();
        int scoreJ2 = jeu.getScoreBlanc();
        
        ihm.afficherScoreFinal(joueur1, scoreJ1, joueur2, scoreJ2);

        Joueur vainqueur = jeu.determinerVainqueur(joueur1, joueur2);
        if (vainqueur != null) {
            vainqueur.incrementerVictoires();
            ihm.afficherVainqueur(vainqueur);
        } else {
            Joueur.incrementerEgalites();
            ihm.afficherEgalite();
        }
    }
} 