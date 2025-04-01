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

    public String lireEntree() {
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

    public String demanderChoisirIa(){
        System.out.println("Veuillez choisir votre modèle d'IA : ");
        System.out.println("1 : IA Aléatoire (moyen)");
        System.out.println("2 : IA MiniMax (difficile)");
        System.out.println("Par défault l'IA sera aléatoire");
        return sc.nextLine().toUpperCase();
    }

    public String choisirJeu(){
        System.out.println("Choisissez un jeu : ");
        System.out.println(" - 1 - Othello");
        System.out.println(" - 2 - Awalé");
        System.out.println("Par défault le jeu sera Othello");
        return sc.nextLine().toUpperCase();
    }

    // Méthodes spécifiques pour Othello
    public void afficherPlateauOthello(String[][] plateau, int taille) {
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

    public String demanderCoupOthello(Joueur joueur) {
        System.out.printf("%s, entrez votre coup (ex : 1 C) ou 'P' pour passer : ", joueur);
        return sc.nextLine().toUpperCase();
    }

    public void afficherFormatCoupInvalideOthello() {
        System.out.println("Le format du coup est invalide. Utilisez le format 'ligne colonne' (ex: 1 C)");
    }

    // Méthodes spécifiques pour Awalé
    public void afficherPlateauAwale(String[][] plateau, int scoreJ1, int scoreJ2) {
        System.out.println("Grenier J2: " + scoreJ2);
        
        System.out.println("  6  5  4  3  2  1");
        System.out.print("| ");
        for (int j = 5; j >= 0; j--) {
            System.out.print(plateau[0][j] + " | ");
        }
        System.out.println();
        
        System.out.print("| ");
        for (int j = 0; j < 6; j++) {
            System.out.print(plateau[1][j] + " | ");
        }
        System.out.println();
        System.out.println("  1  2  3  4  5  6");
        
        System.out.println("Grenier J1: " + scoreJ1);
    }

    public String demanderCoupAwale(Joueur joueur) {
        System.out.printf("%s, entrez le numéro du trou que vous voulez jouer (1-6) : ", joueur);
        return sc.nextLine().toUpperCase();
    }

    public void afficherFormatCoupInvalideAwale() {
        System.out.println("Le format du coup est invalide. Entrez un chiffre entre 1 et 6");
    }
}
