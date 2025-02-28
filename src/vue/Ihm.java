package vue;

import modele.Coordonnee;
import modele.Joueur;
import modele.Pion;
import modele.Plateau;

import java.util.Scanner;


public class Ihm {

    // ANSI code pour colorer les caractères "□" du damier
    private static final String VERT = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    Scanner sc = new Scanner(System.in);

    public void initPartie() {
        System.out.println("Bievenue dans Othello");
    }

    // Méthode pour récupérer le nom des joueurs
    public String collecterJoueur() {
        System.out.println("Entrez le nom du Joueur 1");
        String joueur1 = sc.nextLine();
        System.out.println("Joueur 1 : " + joueur1);
        return joueur1;
    }

    // Méthode pour afficherle plateau : Parcours la matrice en affichant les couleurs des pions (notamment la couleur
    // "□" qui sert de base à chaque pion)
    public void afficherPlateau(Plateau plateau) {
        // Afficher les lettres des colonnes
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < plateau.getTailleJeu(); j++) {
            System.out.print((char) ('A' + j) + " ");
        }
        System.out.println();

        // Afficher le plateau
        for (int i = 0; i < plateau.getTailleJeu(); i++) {
            System.out.printf("%2d ", i + 1); // Affiche le numéro de ligne (1-8 au lieu de 0-7) d'ou le i + 1
            for (int j = 0; j < plateau.getTailleJeu(); j++) {
                Pion pion = plateau.getPion(new Coordonnee(i, j));
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
            System.out.print((char) ('A' + j) + " ");
        }
        System.out.println();
    }

    // Méthode qui permet de récupérer un coup et d'en vérifier la légalité syntaxique
    public Coordonnee collecterCoup(Joueur joueur) {
        while (true) {
            try {
                System.out.println("C'est au tour de " + joueur);
                System.out.println("Indique le coup que tu souhaites jouer (exemple: A 3) : ");

                String ligne = sc.nextLine();

                // Division de la string en coupant les espaces et en opérant un split entre les 2 parties de l'input
                String[] parties = ligne.trim().split("\\s+");

                // Verification que la string ne contenait que 2 éléments
                if (parties.length != 2) {
                    System.out.println("Format incorrect. Veuillez entrer une lettre puis un chiffre séparés " +
                            "par un espace");
                    continue;
                }

                // On récupère le premier élément et on vérifie qu'il s'agit d'une lettre unique
                String lettre = parties[0];
                if (lettre.length() != 1 || !Character.isLetter(lettre.charAt(0))) {
                    System.out.println("Premier caractère invalide. Veuillez entrer une lettre.");
                    continue;
                }

                // On passe le caractère en uppercase afin de sécuriser un input en minuscule
                int y = Character.toUpperCase(lettre.charAt(0) - 'A');
                int x;

                // On vérifie si le deuxième élément est bien un entier
                try {
                    x = Integer.parseInt(parties[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Deuxième partie invalide. Veuillez entrer un entier");
                    continue;
                }

                Coordonnee coordonnee = new Coordonnee(x, y);
                System.out.println("Coordonnée saisie : " + coordonnee);

                return coordonnee;
            }
            catch (Exception e) {
                System.out.println("Erreur de saisie. Veuillez réessayer");
                if (sc.hasNext()){
                    sc.nextLine();
                }
            }

        }
    }

    public void afficherMessage (String message){
        System.out.println(">>>" + message + "<<<");
    }
}

