package controleur;

import modele.Joueur;
import vue.Ihm;
import modele.Plateau;

import java.util.Random;

public class Controleur {
    private Ihm ihm;
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;
    private boolean jouerContreIA = false;
    private Random randomInt;
    private int[] coor;


    public Controleur(Ihm ihm) {
        this.ihm = ihm;
        this.plateau = new Plateau();
        this.randomInt = new Random();
    }

    public void initPartie(){
        this.joueur1 = new Joueur(ihm.demanderJoueur(1), plateau.getCouleurNoire());
        if(ihm.demanderJouerContreIA().equals("O")){
            this.joueur2 = new Joueur("Ordinateur", plateau.getCouleurBlanc());
            joueur2.setEstUneIA();
            jouerContreIA = true;
        }
        else {
            this.joueur2 = new Joueur(ihm.demanderJoueur(2), plateau.getCouleurBlanc());}
        joueurActuel = joueur1;

    }



    // Initialise la partie et gère la boucle de jeu "globale", a savoir relancer une partie
    // lorsque jouerPartie() se résoud
    public void jouer() {
        initPartie();
        boolean continuerJeu = true;
        
        while (continuerJeu) {
            if (jouerContreIA) {
                jouerPartieIA();
                // Ajout de la demande de nouvelle partie même après une partie contre l'IA
                String reponse;
                boolean reponseValide = false;
                while (!reponseValide) {
                    reponse = ihm.demanderNouvellePartie();
                    if (reponse.equals("O")) {
                        reponseValide = true;
                        this.plateau = new Plateau();
                        joueurActuel = joueur1;
                    } else if (reponse.equals("N")) {
                        reponseValide = true;
                        continuerJeu = false;
                    } else {
                        ihm.afficherReponseInvalide();
                    }
                }
            } else {
                jouerPartie();
                String reponse;
                boolean reponseValide = false;
                while (!reponseValide) {
                    reponse = ihm.demanderNouvellePartie();
                    if (reponse.equals("O")) {
                        reponseValide = true;
                        this.plateau = new Plateau();
                        joueurActuel = joueur1;
                    } else if (reponse.equals("N")) {
                        reponseValide = true;
                        continuerJeu = false;
                    } else {
                        ihm.afficherReponseInvalide();
                    }
                }
            }
        }
        ihm.afficherStatistiques(joueur1, joueur2);
    }

    public void jouerPartieIA(){
        while(!estPartieTerminee()){
            ihm.afficherPlateau(plateau.getPlateau(), plateau.getTaille());
            if(peutJouer(joueurActuel) && joueurActuel.getEstUneIA()){
                jouerIa();
                ihm.afficherCoupIa(coor);
                joueurActuel =  (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
            else if (peutJouer(joueurActuel)){
                jouerUnCoup(joueurActuel);
            }
            else if (!peutJouer(joueurActuel) && joueurActuel.getEstUneIA()){
                ihm.afficherIaPasse();
                joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
            }
            else {
                ihm.afficherAucunCoupPossible(joueurActuel);
                String reponse = ihm.demanderCoup(joueurActuel);
                if (reponse.equals("P")) {
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherDoitPasser();
                }

            }
        }
        ihm.afficherPlateau(plateau.getPlateau(), plateau.getTaille());
        terminerPartie();
    }

    public void jouerIa(){
        String couleurIa=joueurActuel.getCouleur();
        int n=randomInt.nextInt(plateau.coupPossible(couleurIa).size());
        this.coor = plateau.coupPossible(couleurIa).get(n);
        plateau.jouerCoup(coor[0],coor[1],couleurIa);
    }

    // Gère une partie unique, tant que la partie n'est pas terminée on affiche le plateau on regarde si
    // le joueur actuel peut jouer, si oui il joue un coup, sinon on lui affiche qu'aucun coup n'est possible et
    // l'invite à passer son tour
    // Lorsque estTermine deviens faux (quand aucun joueur ne peut jouer) on sort du while on passe dans terminerPartie
    // qui calcule les scores et affecte les victoires si besoin puis on quitte cette méthode pour remonter dans jouer()
    public void jouerPartie() {
        while (!estPartieTerminee()) {
            ihm.afficherPlateau(plateau.getPlateau(), plateau.getTaille());
            if (peutJouer(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            } else {
                ihm.afficherAucunCoupPossible(joueurActuel);
                String reponse = ihm.demanderCoup(joueurActuel);
                if (reponse.equals("P")) {
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherDoitPasser();
                }
            }
        }
        ihm.afficherPlateau(plateau.getPlateau(), plateau.getTaille());
        terminerPartie();
    }

    // Gère la syntaxe du coup, tant que le coup n'est pas valide on le demande. Si le joueur demande a passer et n'a
    // pas le droit alors on renvoie une erreur. Sinon on prend le coup et on vérifie sa syntaxe. Si sa syntaxe est
    // valide on va chercher dans le modèle si le coup est légal, si oui on le joue et on change de joueur
    public void jouerUnCoup(Joueur joueur) {
        boolean coupValide = false;
        while(!coupValide) {
            String coup = ihm.demanderCoup(joueurActuel);
            if (coup.equals("P")) {
                if (!peutJouer(joueurActuel)) {
                    coupValide = true;
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                } else {
                    ihm.afficherPassageImpossible();
                }
            }
            else if (coup.length() == 3 && coup.charAt(1) == ' ') {
                int ligne = coup.charAt(0) - '1';
                int colonne = coup.charAt(2) - 'A';
                String couleur = joueurActuel.getCouleur();

                if (plateau.verifCoup(ligne, colonne, couleur)) {
                    coupValide = true;
                    plateau.jouerCoup(ligne, colonne, couleur);
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                }
                else {
                    ihm.afficherCoupInvalide();
                }
            }
            else {
                ihm.formatCoupInvalide();
            }
        }
    }

    // Va chercher dans le modèle si un coup est légal
    private boolean peutJouer(Joueur joueurActuel) {
        for(int i = 0; i < plateau.getTaille(); i++){
            for(int j = 0; j < plateau.getTaille(); j++){
                if (plateau.verifCoup(i, j, joueurActuel.getCouleur())){
                    return true;
                }
            }
        }
        return false;
    }

    // Utilise le fait qu'aucun coup n'est légal pour terminer la partie
    private boolean estPartieTerminee() {
        return !peutJouer(joueur1) && !peutJouer(joueur2);
    }


    // Assigne aux variable J1 J2 les scores de chaque joueurs calculés dans le plateau et incrémente leur compteur de
    // victoire personelles si necessaire ou un statique égalité commun à la classe si égalité.
    private void terminerPartie() {
        int scoreJ1 = plateau.getScoreNoir();
        int scoreJ2 = plateau.getScoreBlanc();
        
        ihm.afficherScoreFinal(joueur1, scoreJ1, joueur2, scoreJ2);

        Joueur vainqueur = plateau.determinerVainqueur(joueur1, joueur2);
        if (vainqueur != null) {
            vainqueur.incrementerVictoires();
            ihm.afficherVainqueur(vainqueur);
        } else {
            Joueur.incrementerEgalites();
            ihm.afficherEgalite();
        }
    }
}