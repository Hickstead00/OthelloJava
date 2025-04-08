/**
 * Classe représentant un joueur dans le jeu.
 * Gère les informations du joueur comme son nom, sa couleur, ses victoires et sa stratégie d'IA.
 */
package modele;
import modele.ia.StrategieIA;

public class Joueur {
    private String nom;
    private String couleur;
    private int nbVictoires;
    private static int nbEgalites = 0;  // Partagé entre tous les joueurs
    private boolean estUneIA = false;
    private StrategieIA strategieIA;

    /**
     * Constructeur d'un joueur.
     * @param nom Le nom du joueur
     * @param couleur La couleur des pions du joueur
     */
    public Joueur(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.nbVictoires = 0;
    }

    /**
     * Récupère la couleur des pions du joueur.
     * @return La couleur du joueur
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Incrémente le nombre de victoires du joueur.
     */
    public void incrementerVictoires() {
        nbVictoires++;
    }

    /**
     * Incrémente le nombre d'égalités (statique, partagé entre tous les joueurs).
     */
    public static void incrementerEgalites() {
        nbEgalites++;
    }

    /**
     * Récupère le nombre de victoires du joueur.
     * @return Le nombre de victoires
     */
    public int getNbVictoires() {
        return nbVictoires;
    }

    /**
     * Récupère le nombre total d'égalités.
     * @return Le nombre d'égalités
     */
    public static int getNbEgalites() {
        return nbEgalites;
    }

    /**
     * Vérifie si le joueur est une IA.
     * @return true si le joueur est une IA, false sinon
     */
    public boolean getEstUneIA() {
        return estUneIA;
    }

    /**
     * Définit le joueur comme étant une IA.
     */
    public void setEstUneIA() {
        this.estUneIA = true;
    }

    /**
     * Retourne le nom du joueur.
     * @return Le nom du joueur
     */
    public String toString() {
        return nom;
    }

    /**
     * Définit la stratégie d'IA du joueur.
     * @param strategieIA La stratégie d'IA à utiliser
     */
    public void setStrategieIA(StrategieIA strategieIA) {
        this.strategieIA = strategieIA;
    }

    /**
     * Récupère la stratégie d'IA du joueur.
     * @return La stratégie d'IA du joueur
     */
    public StrategieIA getStrategieIA() {
        return strategieIA;
    }
}
