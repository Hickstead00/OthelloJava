package modele;

public class Pion {
    private char couleurPion;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pion(int x, int y, char couleur) {
        this.couleurPion = couleur;
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {

        return ""+couleurPion;
    }

    public char getCouleurPion() {
        return couleurPion;
    }

    public void setCouleurPion(char couleurPion) {
        this.couleurPion = couleurPion;
    }

}
