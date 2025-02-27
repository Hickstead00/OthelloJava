package vue;

import modele.Coordonnee;
import modele.Pion;
import modele.Plateau;

import java.util.Scanner;


public class Ihm {

    private static final String VERT = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    Scanner sc = new Scanner(System.in);

    public void initPartie(){
        System.out.println("Bievenue dans Othello");
    }

    public String collecterJoueur(){
        System.out.println("Entrez le nom du Joueur 1");
        String joueur1 = sc.nextLine();
        System.out.println("Joueur 1 : "+joueur1);
        return joueur1;
    }

    public void afficherPlateau(Plateau plateau) {
        // Afficher les lettres des colonnes
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < plateau.getTailleJeu(); j++) {
            System.out.print((char)('A' + j) + " ");
        }
        System.out.println();

        // Afficher le plateau
        for (int i = 0; i < plateau.getTailleJeu(); i++) {
            System.out.printf("%2d ", i + 1); // Affiche le numéro de ligne (1-8 au lieu de 0-7) d'ou le i + 1
            for (int j = 0; j < plateau.getTailleJeu(); j++) {
                Pion pion = plateau.getPion(new Coordonnee(i,j));
                if (pion.getCouleur() == plateau.getCaseVide()) {
                    System.out.print(VERT + plateau.getCaseVide() + RESET + " ");
                } else {
                    System.out.print(pion.getCouleur() + " ");
                }
            }
            System.out.println(i + 1); // Numéro de ligne à droite
        }

        // Réafficher les lettres en bas
        System.out.print("   ");
        for (int j = 0; j < plateau.getTailleJeu(); j++) {
            System.out.print((char)('A' + j) + " ");
        }
        System.out.println();
    }


}
