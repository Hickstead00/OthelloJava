package modele;

import java.sql.SQLOutput;

public class Plateau {
    private char[][] plateau;
    private final int TAILLE_JEU;

    // Codes ANSI pour les couleurs
    private static final String VERT = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    // Caractères pour représenter les pions et cases
    private static final char PION_BLANC = '○';
    private static final char PION_NOIR = '●';
    private static final char CASE_VIDE = '□';

    public Plateau(int tailleJeu) {
        this.TAILLE_JEU = tailleJeu;
        plateau = new char[TAILLE_JEU][TAILLE_JEU];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        // Initialiser toutes les cases avec des cases vides
        for (int i = 0; i < TAILLE_JEU; i++) {
            for (int j = 0; j < TAILLE_JEU; j++) {
                plateau[i][j] = CASE_VIDE;
            }
        }

        // Position initiale des pions
        int milieu = TAILLE_JEU / 2 - 1;
        plateau[milieu][milieu] = PION_BLANC;
        plateau[milieu][milieu + 1] = PION_NOIR;
        plateau[milieu + 1][milieu] = PION_NOIR;
        plateau[milieu + 1][milieu + 1] = PION_BLANC;
    }

    public void afficherPlateau() {
        // Afficher les lettres des colonnes
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < TAILLE_JEU; j++) {
            System.out.print((char)('A' + j) + " ");
        }
        System.out.println();

        // Afficher le plateau
        for (int i = 0; i < TAILLE_JEU; i++) {
            System.out.printf("%2d ", i + 1); // Affiche le numéro de ligne (1-8 au lieu de 0-7) d'ou le i + 1
            for (int j = 0; j < TAILLE_JEU; j++) {
                if (plateau[i][j] == CASE_VIDE) {
                    System.out.print(VERT + plateau[i][j] + RESET + " ");
                } else {
                    System.out.print(plateau[i][j] + " ");
                }
            }
            System.out.println(i + 1); // Numéro de ligne à droite
        }

        // Réafficher les lettres en bas
        System.out.print("   ");
        for (int j = 0; j < TAILLE_JEU; j++) {
            System.out.print((char)('A' + j) + " ");
        }
        System.out.println();
    }

    public char[][] getPlateau() {
        return plateau;
    }

    public void ajoutPion(int x, int y) {
        plateau[x][y] = PION_NOIR;
    }

}