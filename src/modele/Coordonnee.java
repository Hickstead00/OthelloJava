package modele;

public class Coordonnee {
    private int x;
    private int y;

    public Coordonnee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean estValide(int taillePlateau){
        return x >= 0 && x < taillePlateau && y >= 0 && y < taillePlateau;
    }

    @Override
    public String toString() {
        char lettre = (char)('A' + y);
        return lettre + Integer.toString(x + 1);
    }
}
