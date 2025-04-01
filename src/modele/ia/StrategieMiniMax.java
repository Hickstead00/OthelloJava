package modele.ia;

import modele.JeuOthello;

import java.util.ArrayList;

public class StrategieMiniMax implements StrategieIA {

    private final int SCORE_VICTOIRE = 1000;
    private final int PROFONDEUR_MAX = 3; // Profondeur de recherche par défaut de l'algo

    @Override
    public int[] calculerCoup(JeuOthello jeuOthello, String couleur) {
        return meilleurCoupMinMax(jeuOthello, couleur, PROFONDEUR_MAX);
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

    private int evaluerPosition(JeuOthello jeuOthello, String couleur) {
        // Vérifier si la partie est terminée
        if (estPartieTerminee(jeuOthello)) {
            jeuOthello.mettreAJourScores();
            int scoreNoir = jeuOthello.getScoreNoir();
            int scoreBlanc = jeuOthello.getScoreBlanc();

            boolean estNoir = couleur.equals(jeuOthello.getCouleurJ1());

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
        int taille = jeuOthello.getTaille();
        String[][] etatPlateau = jeuOthello.getPlateau();
        String couleurAdversaire = (couleur.equals(jeuOthello.getCouleurJ1())) ?
                jeuOthello.getCouleurJ2() : jeuOthello.getCouleurJ1();

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (etatPlateau[i][j].equals(couleur)) {
                    if (estCoin(i, j, taille)) {
                        score += 10;
                    } else if (estBord(i, j, taille)) {
                        score += 3;
                    } else {
                        score += 1;
                    }
                } else if (etatPlateau[i][j].equals(couleurAdversaire)) {
                    if (estCoin(i, j, taille)) {
                        score -= 10;
                    } else if (estBord(i, j, taille)) {
                        score -= 3;
                    } else {
                        score -= 1;
                    }
                }
            }
        }
        return score;
    }


    private JeuOthello coupHypothetique(int ligne, int colonne, JeuOthello jeuOthello, String couleur) {
        JeuOthello nouveauJeuOthello = new JeuOthello(jeuOthello);
        nouveauJeuOthello.jouerCoup(ligne, colonne, couleur);
        return nouveauJeuOthello;
    }

    private int minMax(JeuOthello jeuOthello, String couleur, int profondeur, boolean estMax) {
        if (profondeur == 0)
            return evaluerPosition(jeuOthello, couleur);  /* si on arrive à une feuille return eval des pions du plateau pour l'ordi */
        String couleurActuelle = estMax ? couleur :
                (couleur.equals(jeuOthello.getCouleurJ1()) ?
                        jeuOthello.getCouleurJ2() : jeuOthello.getCouleurJ1());
        ArrayList<int[]> coupsPossibles = jeuOthello.coupPossible(couleur);

        if (coupsPossibles.isEmpty()) {
            // Si pas de coups, on change de joueur ou on évalue si les deux ne peuvent pas jouer
            String autreCouleur = (couleurActuelle.equals(jeuOthello.getCouleurJ1())) ?
                    jeuOthello.getCouleurJ2() : jeuOthello.getCouleurJ1();
            if (jeuOthello.coupPossible(autreCouleur).isEmpty()) {
                // Partie terminée
                return evaluerPosition(jeuOthello, couleur);
            }
            return minMax(jeuOthello, couleur, profondeur - 1, !estMax);
        }

        int meilleurScore;
        if (estMax) {
            meilleurScore = Integer.MIN_VALUE;
            for (int[] coup : coupsPossibles) {
                JeuOthello nouveauJeuOthello = coupHypothetique(coup[0], coup[1], jeuOthello, couleurActuelle);
                int score = minMax(nouveauJeuOthello, couleur, profondeur - 1, false);
                meilleurScore = Math.max(meilleurScore, score);
            }
        } else {
            meilleurScore = Integer.MAX_VALUE;
            for (int[] coup : coupsPossibles) {
                JeuOthello nouveauJeuOthello = coupHypothetique(coup[0], coup[1], jeuOthello, couleurActuelle);
                int score = minMax(nouveauJeuOthello, couleur, profondeur - 1, true);
                meilleurScore = Math.min(meilleurScore, score);
            }
        }

        return meilleurScore;
    }


    public int[] meilleurCoupMinMax(JeuOthello jeuOthello, String couleur, int profondeur) {
        ArrayList<int[]> coupsPossibles = jeuOthello.coupPossible(couleur);
        if (coupsPossibles.isEmpty()) {
            return null;
        }

        int[] meilleurCoup = null;
        int meilleurScore = Integer.MIN_VALUE;

        for (int[] coup : coupsPossibles) {
            JeuOthello nouveauJeuOthello = coupHypothetique(coup[0], coup[1], jeuOthello, couleur);
            int score = minMax(nouveauJeuOthello, couleur, profondeur - 1, false);

            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coup;
            }
        }

        return meilleurCoup;
    }


    private boolean estPartieTerminee(JeuOthello jeuOthello) {
        String couleurNoire = jeuOthello.getCouleurJ1();
        String couleurBlanc = jeuOthello.getCouleurJ2();

        return jeuOthello.coupPossible(couleurNoire).isEmpty() &&
                jeuOthello.coupPossible(couleurBlanc).isEmpty();
    }
}