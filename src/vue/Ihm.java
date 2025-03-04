package vue;

import modele.Joueur;
import modele.Plateau;

import java.util.Scanner;

public class Ihm {
    private Scanner sc;

    public Ihm() {
        sc = new Scanner(System.in);
    }

    public String demanderJoueur(int numJoueur) {
        System.out.printf("Entrez le nom du joueur %d : ", numJoueur);
        return sc.nextLine();
    }

    public String demanderCoup(Joueur joueur) {
        System.out.printf("%s, entrez votre coup, (ex : A1) : ", joueur);
        return sc.nextLine().toUpperCase();
    }

    public void afficherPlateau(String[][] plateau, int taille) {

        System.out.println("   A  B  C  D   E  F  G  H");

        for (int i = 0; i < 8; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println("" + (i+1));
        }

        // Pied
        System.out.println("   A  B  C  D   E  F  G  H");
    }

    public void afficherCoupInvalide() {
        System.out.println("Ce coup est invalide");
    }

    public void formatCoupInvalide() {
        System.out.println("Le format du coup est invalide");
    }

    public void afficherScoreFinal(Joueur joueur1, int scoreJoueur1, Joueur joueur2, int scoreJoueur2) {
        System.out.println("Score joueur 1 : " + scoreJoueur1);
        System.out.println("Score joueur 2 : " + scoreJoueur2);
    }

    public void afficherVainqueur(Joueur joueur) {
        System.out.println("Vainqueur : " + joueur);
    }

    public void afficherEgalite() {
        System.out.println("Egalite");
    }


    public boolean demanderNouvellePartie() {
        return false;
    }

    public void afficherStatistiques(Joueur joueur1, int victoireJoueur1, Joueur joueur2, int victoireJoueur2) {
        System.out.println("Joueur1 : " + joueur1 + "Nb victoires : " + victoireJoueur1);
        System.out.println("Joueur2 : " + joueur2 + "Nb victoires : " + victoireJoueur2);
    }
}
