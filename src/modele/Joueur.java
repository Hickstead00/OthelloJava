package modele;

public class Joueur {
    private String nom;
    private String couleur;

    public Joueur(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public String toString() {
        return nom;
    }

}
