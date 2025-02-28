package modele;

public class Joueur {
    private String nom;
    private char couleur;

    public Joueur(String nom, char couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String toString(){
        return nom + " ";
    }

}
