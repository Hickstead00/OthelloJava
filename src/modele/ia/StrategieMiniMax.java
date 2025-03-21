package modele.ia;

import modele.Plateau;

import java.util.ArrayList;
import java.util.HashMap;

public class StrategieMiniMax implements StrategieIA {

    private HashMap<String, Integer> arbre = new HashMap<>();
    private final int SCORE_VICTOIRE = 1000;
    private final int PROFONDEUR_MAX = 20; // Profondeur de recherche par défaut de l'algo

    @Override
    public int[] calculerCoup(Plateau plateau, String couleur) {
        return meilleurCoupMinMax(plateau, couleur, PROFONDEUR_MAX);
    }

    @Override
    public String getNom() {
        return "MiniMax";
    }

    private static boolean estCoin(int ligne, int colonne, int taille) {
        return (ligne == 0 && colonne == 0) ||
                (ligne == 0 && colonne == taille - 1) ||
                (ligne == taille - 1 && colonne == 0) ||
                (ligne == taille - 1 && colonne == taille - 1);
    }

    private static boolean estBord(int ligne, int colonne, int taille) {
        return ligne == 0 || ligne == taille - 1 || colonne == 0 || colonne == taille - 1;
    }

    private int evaluerPosition(Plateau plateau, String couleur) {
        // Vérifier si la partie est terminée
        if (estPartieTerminee(plateau)) {
            plateau.mettreAJourScores();
            int scoreNoir = plateau.getScoreNoir();
            int scoreBlanc = plateau.getScoreBlanc();

            boolean estNoir = couleur.equals(plateau.getCouleurNoire());

            // Si le joueur actuel est noir
            if (estNoir) {
                if (scoreNoir > scoreBlanc) {
                    return SCORE_VICTOIRE;  // Victoire
                } else if (scoreNoir < scoreBlanc) {
                    return -SCORE_VICTOIRE; // Défaite
                } else {
                    return 0;  // Égalité
                }
            } else {
                // Si le joueur actuel est blanc
                if (scoreBlanc > scoreNoir) {
                    return SCORE_VICTOIRE;  // Victoire
                } else if (scoreBlanc < scoreNoir) {
                    return -SCORE_VICTOIRE; // Défaite
                } else {
                    return 0;  // Égalité
                }
            }
        }

        // Évaluation classique si la partie n'est pas terminée
        int score = 0;
        int taille = plateau.getTaille();
        String[][] etatPlateau = plateau.getPlateau();
        String couleurAdversaire = (couleur.equals(plateau.getCouleurNoire())) ?
                plateau.getCouleurBlanc() : plateau.getCouleurNoire();

        for (int i = 0; i < taille; i++)
        {
            for (int j = 0; j < taille; j++)
            {
                if (etatPlateau[i][j].equals(couleur))
                {
                    if (estCoin(i, j, taille))
                    {
                        score += 11;
                    } else if (estBord(i, j, taille))
                    {
                        score += 3;
                    } else {
                        score += 1;
                    }
                /*} else if (etatPlateau[i][j].equals(couleurAdversaire)) {
                    if (estCoin(i, j, taille)) {
                        score -= 11;
                    } else if (estBord(i, j, taille)) {
                        score -= 3;
                    } else {
                        score -= 1;
                    }*/
                }
            }
        }
        return score;
    }


    private Plateau coupHypothetique(int ligne, int colonne, Plateau plateau, String couleur) {
        Plateau nouveauPlateau = new Plateau(plateau);
        nouveauPlateau.jouerCoup(ligne, colonne, couleur);
        return nouveauPlateau;
    }

    private int minMax(Plateau plateau, String couleur, int profondeur, boolean estMax) {
        if (profondeur == 0)
            return evaluerPosition(plateau, couleur);  /* si on arrive à une feuille return eval des pions du plateau pour l'ordi */
        String couleurActuelle = estMax ? couleur :
                (couleur.equals(plateau.getCouleurNoire()) ?
                        plateau.getCouleurBlanc() : plateau.getCouleurNoire());
        ArrayList<int[]> coupsPossibles = plateau.coupPossible(couleur);

        if (coupsPossibles.isEmpty()) {
            // Si pas de coups, on change de joueur ou on évalue si les deux ne peuvent pas jouer
            String autreCouleur = (couleurActuelle.equals(plateau.getCouleurNoire())) ?
                    plateau.getCouleurBlanc() : plateau.getCouleurNoire();
            if (plateau.coupPossible(autreCouleur).isEmpty()) {
                // Partie terminée
                return evaluerPosition(plateau, couleur);
            }
            return minMax(plateau, couleur, profondeur - 1, !estMax);
        }

        int meilleurScore;
        if (estMax) {
            meilleurScore = Integer.MIN_VALUE;
            for (int[] coup : coupsPossibles) {
                Plateau nouveauPlateau = coupHypothetique(coup[0], coup[1], plateau, couleurActuelle);
                int score = minMax(nouveauPlateau, couleur, profondeur - 1, false);
                meilleurScore = Math.max(meilleurScore, score);

            }
        } else {
            meilleurScore = Integer.MAX_VALUE;
            for (int[] coup : coupsPossibles) {
                Plateau nouveauPlateau = coupHypothetique(coup[0], coup[1], plateau, couleurActuelle);
                int score = minMax(nouveauPlateau, couleur, profondeur - 1, true);
                meilleurScore = Math.min(meilleurScore, score);
            }
        }

        return meilleurScore;
    }


    public int[] meilleurCoupMinMax(Plateau plateau, String couleur, int profondeur) {
        ArrayList<int[]> coupsPossibles = plateau.coupPossible(couleur);
        if (coupsPossibles.isEmpty()) {
            return null;
        }

        int[] meilleurCoup = null;
        int meilleurScore = Integer.MIN_VALUE;

        for (int[] coup : coupsPossibles) {
            Plateau nouveauPlateau = coupHypothetique(coup[0], coup[1], plateau, couleur);
            int score = minMax(nouveauPlateau, couleur, profondeur - 1, false);

            System.out.println(score+" : "+coup[0]+";"+coup[1]);
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coup;
            }
        }

        return meilleurCoup;
    }


    public HashMap<String, Integer> getArbre() {
        return arbre;
    }

    public void addArbre(int[] coup, int point) {
        String key = coup[0] + "," + coup[1];
        arbre.put(key, point);
    }

    private boolean estPartieTerminee(Plateau plateau) {
        String couleurNoire = plateau.getCouleurNoire();
        String couleurBlanc = plateau.getCouleurBlanc();

        return plateau.coupPossible(couleurNoire).isEmpty() &&
                plateau.coupPossible(couleurBlanc).isEmpty();
    }
}