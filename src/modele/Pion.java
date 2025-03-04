package modele;

public class Pion {
    private char couleurPion;
    private int x;
    private int y;


    public Pion(char couleur,int x, int y) {
        this.couleurPion = couleur;
        this.x=x;
        this.y=y;
    }

    public char getCouleur() {
        return couleurPion;
    }

    public void setCouleur(char couleur) {
        this.couleurPion = couleur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {

        return ""+couleurPion;
    }
}