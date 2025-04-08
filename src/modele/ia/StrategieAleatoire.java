/**
 * Implémentation d'une stratégie d'IA qui joue de manière aléatoire.
 * Cette stratégie choisit un coup valide au hasard parmi tous les coups possibles.
 */
package modele.ia;

import modele.JeuOthello;
import java.util.ArrayList;
import java.util.Random;

public class StrategieAleatoire implements StrategieIA {
    private Random random = new Random();

    /**
     * Calcule un coup aléatoire parmi les coups possibles.
     * @param jeuOthello L'état actuel du jeu
     * @param couleur La couleur des pions de l'IA
     * @return Les coordonnées d'un coup aléatoire valide, ou null si aucun coup n'est possible
     */
    @Override
    public int[] calculerCoup(JeuOthello jeuOthello, String couleur) {
        ArrayList<int[]> coupsPossibles = jeuOthello.coupPossible(couleur);

        if (coupsPossibles.isEmpty()) {
            return null;
        }

        // Choisir un coup aléatoire parmi les coups possibles
        int indiceAleatoire = random.nextInt(coupsPossibles.size());
        return coupsPossibles.get(indiceAleatoire);
    }
}