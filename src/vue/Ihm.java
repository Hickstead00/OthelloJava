package vue;

import modele.Joueur;

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
        System.out.printf("%s, entrez votre coup (ex : 1 C) ou 'P' pour passer : ", joueur);
        return sc.nextLine().toUpperCase();
    }

    public void afficherPlateau(String[][] plateau, int taille) {

        System.out.println("   A  B  C  D   E  F  G  H");

        for (int i = 0; i < taille; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < taille; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println("" + (i+1));
        }

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

    public String demanderNouvellePartie() {
        System.out.print("Voulez-vous jouer une nouvelle partie ? (O/N) : ");
        return sc.nextLine().toUpperCase();
    }

    public void afficherReponseInvalide() {
        System.out.println("Veuillez répondre par O (Oui) ou N (Non)");
    }

    public void afficherStatistiques(Joueur joueur1, Joueur joueur2) {
        System.out.println("Joueur1 : " + joueur1 + "." + " Nb victoires : " + joueur1.getNbVictoires());
        System.out.println("Joueur2 : " + joueur2 + "." + " Nb victoires : " + joueur2.getNbVictoires());
        System.out.println("Nombre d'égalités : " + Joueur.getNbEgalites());
    }

    public void afficherPassageImpossible() {
        System.out.println("Vous ne pouvez pas passer votre tour car vous avez encore des coups possibles !");
    }

    public void afficherAucunCoupPossible(Joueur joueur) {
        System.out.println(joueur + " n'a aucun coup possible. Vous devez passer votre tour en tapant 'P'.");
    }

    public void afficherDoitPasser() {
        System.out.println("Vous n'avez aucun coup possible, vous devez taper 'P' pour passer votre tour !");
    }

    public String demanderJouerContreIA(){
        System.out.println("Souhaitez vous jouer contre l'ordinateur ? (O/N) : ");
        return sc.nextLine().toUpperCase();
    }

    public void afficherIaPasse(){
        System.out.println("L'ordinateur passe son tour");
    }

    public void afficherCoupIa(int[] coordonnee){
        // Conversion des coordonnées numériques en format "ligne colonne"
        char colonne = (char)('A' + coordonnee[1]);  // 0 -> 'A', 1 -> 'B', etc.
        int ligne = coordonnee[0] + 1;  // 0 -> 1, 1 -> 2, etc.
        System.out.println("L'ordinateur joue le coup " + ligne + " " + colonne);
    }

}
