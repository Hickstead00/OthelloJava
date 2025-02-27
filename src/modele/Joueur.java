package modele;

public class Joueur {
    private String nom;
    private char couleur;

    public Joueur(String nom, char couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public void placerPion(Plateau plateau,int x, int y) {
        plateau.getPlateau().setCouleurPion(this.couleur);
    }

    public char getCouleurJoueur() {
        return couleur;
    }

    public void setCouleurJoueur(char couleur) {
        this.couleur = couleur;
    }
    public String getNom(){
        return nom;
    }


}
