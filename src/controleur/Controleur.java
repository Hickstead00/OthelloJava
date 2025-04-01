package controleur;

import modele.Jeu;
import modele.JeuAwale;
import modele.JeuOthello;
import modele.Joueur;
import modele.ia.StrategieIA;
import modele.ia.StrategieAleatoire;
import modele.ia.StrategieMiniMax;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Jeu jeu;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;
    private boolean jouerContreIA = false;
    private int[] coor;
    private ControleurJeu controleurJeu;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void initPartie(){
        this.joueur1 = new Joueur(ihm.demanderJoueur(1), jeu.getCouleurJ1());
        if(jeu.supporteIA() && ihm.demanderJouerContreIA().equals("O")){
            this.joueur2 = new Joueur("Ordinateur", jeu.getCouleurJ2());
            StrategieIA strategieIA = choisirStrategieIa();
            joueur2.setStrategieIA(strategieIA);
            joueur2.setEstUneIA();
            jouerContreIA = true;
        }
        else {
            this.joueur2 = new Joueur(ihm.demanderJoueur(2), jeu.getCouleurJ2());
        }
        joueurActuel = joueur1;
    }

    private StrategieIA choisirStrategieIa(){
        String choix = ihm.demanderChoisirIa();
        switch(choix){
            case "1":
                return new StrategieAleatoire();
            case "2":
                return new StrategieMiniMax();
            default:
                return new StrategieAleatoire();
        }
    }

    public void jouer() {
        choixJeu();
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
                    // Réinitialiser le jeu selon son type
                    if (jeu instanceof JeuOthello) {
                        this.jeu = new JeuOthello();
                    } else if (jeu instanceof JeuAwale) {
                        this.jeu = new JeuAwale();
                    }
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

    public void jouerPartieIA(){
        while(!controleurJeu.estPartieTerminee()){
            controleurJeu.afficherPlateau(ihm);
            if(controleurJeu.peutJouer(joueurActuel) && joueurActuel.getEstUneIA()){
                jouerIa();
                ihm.afficherCoupIa(coor);
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
            else if (controleurJeu.peutJouer(joueurActuel)){
                jouerUnCoup(joueurActuel);
            }
            else if (!controleurJeu.peutJouer(joueurActuel) && joueurActuel.getEstUneIA()){
                ihm.afficherIaPasse();
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
            else {
                ihm.afficherAucunCoupPossible(joueurActuel);
                String reponse = controleurJeu.demanderCoup(joueurActuel, ihm);
                if (reponse.equals("P")) {
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherDoitPasser();
                }
            }
        }
        controleurJeu.afficherPlateau(ihm);
        terminerPartie();
    }

    public void jouerIa(){
        String couleurIa = joueurActuel.getCouleur();
        this.coor = joueurActuel.getStrategieIA().calculerCoup((JeuOthello) jeu, couleurIa);
        jeu.jouerCoup(coor[0], coor[1], joueurActuel.getCouleur());
    }

    public void jouerPartie() {
        while (!controleurJeu.estPartieTerminee()) {
            controleurJeu.afficherPlateau(ihm);
            if (controleurJeu.peutJouer(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            } else {
                ihm.afficherAucunCoupPossible(joueurActuel);
                String reponse = controleurJeu.demanderCoup(joueurActuel, ihm);
                if (reponse.equals("P")) {
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherDoitPasser();
                }
            }
        }
        controleurJeu.afficherPlateau(ihm);
        terminerPartie();
    }

    public void jouerUnCoup(Joueur joueur) {
        boolean coupValide = false;
        while(!coupValide) {
            String coup = controleurJeu.demanderCoup(joueurActuel, ihm);
            
            if (coup.equals("P")) {
                if (!controleurJeu.peutJouer(joueurActuel)) {
                    coupValide = true;
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherPassageImpossible();
                }
            }
            else if (controleurJeu.estCoupValide(coup, joueurActuel)) {
                coupValide = true;
                controleurJeu.jouerUnCoup(joueurActuel, coup);
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
            else {
                ihm.afficherCoupInvalide();
            }
        }
    }

    private void terminerPartie() {
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

    public void choixJeu(){
        String choix = ihm.choisirJeu();
        switch (choix) {
            case "1":
                this.jeu = new JeuOthello();
                this.controleurJeu = new ControleurOthello(jeu);
                break;
            case "2":
                this.jeu = new JeuAwale();
                this.controleurJeu = new ControleurAwale(jeu);
                break;
            default:
                this.jeu = new JeuOthello();
                this.controleurJeu = new ControleurOthello(jeu);
                break;
        }
    }
}