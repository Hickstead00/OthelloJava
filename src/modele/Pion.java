package modele;

public class Pion {
    private char couleurPion;
    private Coordonnee coordonnee;


    public Pion(Coordonnee coordonnee, char couleur) {
        this.couleurPion = couleur;
        this.coordonnee = coordonnee;
    }

    public char getCouleur() {
        return couleurPion;
    }

    public void setCouleur(char couleur) {
        this.couleurPion = couleur;
    }

    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    @Override
    public String toString() {

        return ""+couleurPion;
    }
}