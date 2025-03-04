// Flow du jeu :
// Dans le main on crée un controleur avec un ihm. Puis on appelle controleur.jouer()
// Jouer() initialise 2 joueurs, défini le premier joueur et lance une boucle while globale sur "l'application"
// Cette boucle apelle jouerPartie() qui elle lance une boucle sur une partie.
// La boucle jouerPartie() regarde si la partie est terminée (qui est la fonction qui retourne un bool true tant que les deux verifCoup sont OK et que donc au moins 1 des 2 joueurs peut jouer)
// On affiche le plateau puis on regarde si le joueur actuel peut jouer, si oui alors on joue un coup.
// Dans Jouer un coup tant que le coup n'est pas syntaxiquement ok on le boucle, quand il est ok syntaxiquement on le verif sur le plateau, on le passe a true pour casser le while, on joue le coup et on passe au joueur suivant.
// On sort donc du while et on remonte dans la fonction jouer partie qui va afficher et demander un nouveau coup.
// Il y a un moment ou le while(!partieTerminee) va devenir faux, donc on sort et on apelle terminer partie qui va calculer le score en appelant compter pion puis les afficher.
// Et puisqu'on sort aussi de la fonction jouerPartie on remonte encore d'un cran et on retombe sur continuerJeu = ihm.demanderNouvellePartie() qui fera un sc.nextline() avec un test oui/non.
// Si oui le boolean est vrai est donc le while "application globale" reprend (et la il faut que je modifie car actuellement je ne reset pas le plateau).
// Si non on appelle ihm.afficherStats sur les victoires des joueurs 1 et 2 qui ont été incrémentées dans terminerPartie.






package controleur;

import modele.Joueur;
import vue.Ihm;
import modele.Plateau;

public class Controleur {
    private Ihm ihm;
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private int victoireJoueur1;
    private int victoireJoueur2;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
        this.plateau = new Plateau();
    }


    public void jouer() {
        Joueur joueur1 = new Joueur(ihm.demanderJoueur(1), plateau.getCouleurNoire());
        Joueur joueur2 = new Joueur(ihm.demanderJoueur(2), plateau.getCouleurBlanc());
        joueurActuel = joueur1;

        boolean continuerJeu = true;
        while (continuerJeu) {
            jouerPartie();
            continuerJeu = false; //normalement on appelle continuerjeu;
        }

        ihm.afficherStatistiques(joueur1, victoireJoueur1, joueur2, victoireJoueur2);
    }

    public void jouerPartie() {
        while (!estPartieTerminee()) {
            ihm.afficherPlateau(plateau.getPlateau(), plateau.getTaille());
            if (peutJouer(joueurActuel) || peutPasser(joueurActuel)) {
                jouerUnCoup(joueurActuel);
            }

        }

        terminerPartie();
    }

    public void jouerUnCoup(Joueur joueur ) {
        boolean coupValide = false;
        while(!coupValide) {
            String coup = ihm.demanderCoup(joueurActuel);
            if (coup.length() == 2){
                int colonne;
                int ligne;
                String couleur = joueurActuel.getCouleur();

                if (plateau.verifCoup(ligne, colonne, couleur)){
                    coupValide = true;
                    plateau.jouerCoup(ligne, colonne, couleur);
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                }
                else {
                    ihm.afficherCoupInvalide();
                }

            }
            else{
                ihm.formatCoupInvalide();
            }
        }
    }

    private boolean peutJouer(Joueur joueurActuel) {
        for(int i; i < plateau.getTaille(); i++){
            for(int j = 0; j < plateau.getTaille(); j++){
                if (plateau.verifCoup(i, j, joueurActuel){
                    return true;
                }
            }
        }
        return false;
    }

    //private boolean peutPasser(Joueur joueurActuel) ?? Ou bien tu check ta verif coup le fait de passer son tour aussi ?

    private boolean estPartieTerminee() {
        return !peutJouer(joueur1) && !peutJouer(joueur2);
    }

    private void terminerPartie() {
        int[] scores = plateau.compterPion();
        ihm.afficherScoreFinal(joueur1, scoreJoueur1, joueur2, scoreJoueur2);

        if(scores[0]>scores[1]){
            victoireJoueur1++;
            ihm.afficherVainqueur(joueur1);
        } else if (scores[0]<scores[1]) {
            victoireJoueur2++;
            ihm.afficherVainqueur(joueur2);
        } else {
            ihm.afficherEgalite();
        }
    }
}