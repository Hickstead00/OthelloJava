/**
 * Classe gérant l'interface homme-machine du jeu.
 * Elle s'occupe de toutes les interactions avec l'utilisateur via la console.
 */
package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner sc;

    /**
     * Constructeur de l'interface homme-machine.
     * Initialise le scanner pour la lecture des entrées utilisateur.
     */
    public Ihm() {
        sc = new Scanner(System.in);
    }

    /**
     * Demande le nom d'un joueur.
     * @param numJoueur Le numéro du joueur (1 ou 2)
     * @return Le nom saisi par l'utilisateur
     */
    public String demanderJoueur(int numJoueur) {
        System.out.printf("Entrez le nom du joueur %d : ", numJoueur);
        return sc.nextLine();
    }


    /**
     * Affiche un message indiquant que le coup est invalide.
     */
    public void afficherCoupInvalide() {
        System.out.println("Ce coup est invalide");
    }


    /**
     * Affiche les scores finaux des joueurs.
     * @param nomJ1 Le nom du premier joueur
     * @param scoreJoueur1 Le score du premier joueur
     * @param nomJ2 Le nom du second joueur
     * @param scoreJoueur2 Le score du second joueur
     */
    public void afficherScoreFinal(String nomJ1, int scoreJoueur1, String nomJ2, int scoreJoueur2) {
        System.out.println("Score " + nomJ1 + " : " + scoreJoueur1);
        System.out.println("Score " + nomJ2 + " : " + scoreJoueur2);
    }

    /**
     * Affiche le nom du vainqueur.
     * @param nomJoueur Le nom du joueur vainqueur
     */
    public void afficherVainqueur(String nomJoueur) {
        System.out.println("Vainqueur : " + nomJoueur);
    }

    /**
     * Affiche un message indiquant une égalité.
     */
    public void afficherEgalite() {
        System.out.println("Egalite");
    }

    /**
     * Demande à l'utilisateur s'il souhaite jouer une nouvelle partie.
     * @return La réponse de l'utilisateur (O ou N)
     */
    public String demanderNouvellePartie() {
        System.out.print("Voulez-vous jouer une nouvelle partie ? (O/N) : ");
        return sc.nextLine().toUpperCase();
    }

    /**
     * Affiche un message indiquant que la réponse est invalide.
     */
    public void afficherReponseInvalide() {
        System.out.println("Veuillez répondre par O (Oui) ou N (Non)");
    }

    /**
     * Affiche les statistiques des parties jouées.
     * @param nomJ1 Le nom du premier joueur
     * @param nomJ2 Le nom du second joueur
     * @param victoireJ1 Le nombre de victoires du premier joueur
     * @param victoireJ2 Le nombre de victoires du second joueur
     * @param nbEgalites Le nombre d'égalités
     */
    public void afficherStatistiques(String nomJ1, String nomJ2, int victoireJ1, int victoireJ2, int nbEgalites) {
        System.out.println("Joueur1 : " + nomJ1 + "." + " Nb victoires : " + victoireJ1);
        System.out.println("Joueur2 : " + nomJ2 + "." + " Nb victoires : " + victoireJ2);
        System.out.println("Nombre d'égalités : " + nbEgalites);
    }

    /**
     * Affiche un message indiquant que le joueur ne peut pas passer son tour.
     */
    public void afficherPassageImpossible() {
        System.out.println("Vous ne pouvez pas passer votre tour car vous avez encore des coups possibles !");
    }

    /**
     * Affiche un message indiquant qu'un joueur n'a aucun coup possible.
     * @param nomJoueur Le nom du joueur concerné
     */
    public void afficherAucunCoupPossible(String nomJoueur) {
        System.out.println(nomJoueur + " n'a aucun coup possible. Vous devez passer votre tour en tapant 'P'.");
    }

    /**
     * Affiche un message indiquant que le joueur doit passer son tour.
     */
    public void afficherDoitPasser() {
        System.out.println("Vous n'avez aucun coup possible, vous devez taper 'P' pour passer votre tour !");
    }

    /**
     * Demande à l'utilisateur s'il souhaite jouer contre l'IA.
     * @return La réponse de l'utilisateur (O ou N)
     */
    public String demanderJouerContreIA(){
        System.out.println("Souhaitez vous jouer contre l'ordinateur ? (O/N) : ");
        return sc.nextLine().toUpperCase();
    }

    /**
     * Affiche un message indiquant que l'IA passe son tour.
     */
    public void afficherIaPasse(){
        System.out.println("L'ordinateur passe son tour");
    }

    /**
     * Affiche le coup joué par l'IA.
     * @param coordonnee Les coordonnées du coup joué
     */
    public void afficherCoupIa(int[] coordonnee){
        // Conversion des coordonnées numériques en format "ligne colonne"
        char colonne = (char)('A' + coordonnee[1]);  // 0 -> 'A', 1 -> 'B', etc.
        int ligne = coordonnee[0] + 1;  // 0 -> 1, 1 -> 2, etc.
        System.out.println("L'ordinateur joue le coup " + ligne + " " + colonne);
    }

    /**
     * Demande à l'utilisateur de choisir le type d'IA.
     * @return Le choix de l'utilisateur
     */
    public String demanderChoisirIa(){
        System.out.println("Veuillez choisir votre modèle d'IA : ");
        System.out.println("1 : IA Aléatoire (moyen)");
        System.out.println("2 : IA MiniMax (difficile)");
        System.out.println("Par défault l'IA sera aléatoire");
        return sc.nextLine().toUpperCase();
    }

    /**
     * Demande à l'utilisateur de choisir le jeu à jouer.
     * @return Le choix de l'utilisateur
     */
    public String choisirJeu(){
        System.out.println("Choisissez un jeu : ");
        System.out.println(" - 1 - Othello");
        System.out.println(" - 2 - Awalé");
        System.out.println("Par défault le jeu sera Othello");
        return sc.nextLine().toUpperCase();
    }

    /**
     * Affiche le plateau de jeu Othello.
     * @param plateau Le plateau à afficher
     * @param taille La taille du plateau
     */
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

    /**
     * Demande un coup à un joueur pour le jeu Othello.
     * @param nomJoueur Le nom du joueur qui doit jouer
     * @return Le coup saisi par le joueur
     */
    public String demanderCoupOthello(String nomJoueur) {
        System.out.printf("%s, entrez votre coup (ex : 1 C) ou 'P' pour passer : ", nomJoueur);
        return sc.nextLine().toUpperCase();
    }

    /**
     * Affiche un message indiquant que le format du coup est invalide pour Othello.
     */
    public void afficherFormatCoupInvalideOthello() {
        System.out.println("Le format du coup est invalide. Utilisez le format 'ligne colonne' (ex: 1 C)");
    }

    /**
     * Affiche le plateau de jeu Awalé.
     * @param plateau Le plateau à afficher
     * @param scoreJ1 Le score du premier joueur
     * @param scoreJ2 Le score du second joueur
     * @param nomJ1 Le nom du premier joueur
     * @param nomJ2 Le nom du second joueur
     */
    public void afficherPlateauAwale(String[][] plateau, int scoreJ1, int scoreJ2, String nomJ1, String nomJ2) {
        System.out.println();
        System.out.printf("[ Grenier %s : %2d ]\n", nomJ2, scoreJ2);
        System.out.println("              +----+----+----+----+----+----+");

        System.out.print("Cases       : |");
        for (int j = 5; j >= 0; j--) {
            System.out.printf(" %2d |", j + 1);
        }
        System.out.println();

        System.out.print("Zone de jeu : |");
        for (int j = 5; j >= 0; j--) {
            System.out.printf(" %2s |", plateau[0][j]);
        }
        System.out.println();

        // Séparation
        System.out.println("              +----+----+----+----+----+----+");


        System.out.print("Zone de jeu : |");
        for (int j = 0; j < 6; j++) {
            System.out.printf(" %2s |", plateau[1][j]);
        }
        System.out.println();

        System.out.print("Cases       : |");
        for (int j = 0; j < 6; j++) {
            System.out.printf(" %2d |", j + 1);
        }
        System.out.println();

        System.out.println("              +----+----+----+----+----+----+");
        System.out.printf("[ Grenier %s : %2d ]\n", nomJ1, scoreJ1);
    }

    /**
     * Demande un coup à un joueur pour le jeu Awalé.
     * @param nomJoueur Le nom du joueur qui doit jouer
     * @return Le coup saisi par le joueur
     */
    public String demanderCoupAwale(String nomJoueur) {
        System.out.printf("%s, entrez le numéro du trou que vous voulez jouer (1-6) : ", nomJoueur);
        return sc.nextLine().toUpperCase();
    }

    /**
     * Affiche un message indiquant que le format du coup est invalide pour Awalé.
     */
    public void afficherFormatCoupInvalideAwale() {
        System.out.println("Le format du coup est invalide. Entrez un chiffre entre 1 et 6");
    }
}
