package modele;

public class JeuAwale implements Jeu {
    @Override
    public void initialiserJeu() {

    }

    @Override
    public boolean verifCoup(int x, int y, String couleur) {
        return false;
    }

    @Override
    public void jouerCoup(int x, int y, String couleur) {

    }

    @Override
    public boolean estPartieTerminee() {
        return false;
    }

    @Override
    public Joueur determinerVainqueur(Joueur joueurNoir, Joueur joueurBlanc) {
        return null;
    }

    @Override
    public String getCouleurJ1() {
        return "";
    }

    @Override
    public String getCouleurJ2() {
        return "";
    }

    @Override
    public int getTaille() {
        return 0;
    }

    @Override
    public String[][] getPlateau() {
        return new String[0][];
    }

    @Override
    public int getScoreNoir() {
        return 0;
    }

    @Override
    public int getScoreBlanc() {
        return 0;
    }

    @Override
    public int getScoreJoueur(Joueur joueur) {
        return 0;
    }

    @Override
    public boolean supporteIA(){
        return false;
    }
}
