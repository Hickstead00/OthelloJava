package modele;
import modele.ia.StrategieIA;

public class Joueur {
    private String nom;
    private String couleur;
    private int nbVictoires;
    private static int nbEgalites = 0;  // Partagé entre tous les joueurs
    private boolean estUneIA = false;
    private StrategieIA strategieIA;

    public Joueur(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.nbVictoires = 0;
    }

    public String getCouleur() {
        return couleur;
    }

    public void incrementerVictoires() {
        nbVictoires++;
    }

    public static void incrementerEgalites() {
        nbEgalites++;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public static int getNbEgalites() {
        return nbEgalites;
    }

    public String toString() {
        return nom;
    }

    public boolean getEstUneIA() {
        return estUneIA;
    }

    public void setEstUneIA() {
        this.estUneIA = true;
    }

    public void setStrategieIA(StrategieIA strategieIA) {
        this.strategieIA = strategieIA;
    }

}
