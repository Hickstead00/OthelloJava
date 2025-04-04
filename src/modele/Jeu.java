package modele;

public interface Jeu {
    void initialiserJeu();
    boolean verifCoup(int x, int y, String couleur);
    void jouerCoup(int x, int y, String couleur);
    boolean estPartieTerminee();
    Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc);
    String getCouleurJ1();
    String getCouleurJ2();
    int getTaille();
    String[][] getPlateau();
    int getScoreNoir();
    int getScoreBlanc();
    public int getScoreJoueur(Joueur joueur);
    boolean supporteIA();
}
