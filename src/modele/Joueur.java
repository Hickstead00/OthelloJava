package modele;

public class Joueur {
    private String nom;
    private char couleur;

    public Joueur(String nom, char couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public void poserPion(String colonne, String ligne, Plateau plateau) {
        int y = colonne.charAt(0) - 'A';
        int x = Integer.parseInt(ligne) - 1;
        plateau.ajoutPion(x, y);

    }

}
