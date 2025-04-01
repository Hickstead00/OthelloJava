package modele.ia;

import modele.JeuOthello;
import java.util.ArrayList;
import java.util.Random;

public class StrategieAleatoire implements StrategieIA {
    private Random random = new Random();

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