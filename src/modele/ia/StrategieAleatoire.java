package modele.ia;

import modele.Plateau;
import java.util.ArrayList;
import java.util.Random;

public class StrategieAleatoire implements StrategieIA {
    private Random random = new Random();

    @Override
    public int[] calculerCoup(Plateau plateau, String couleur) {
        ArrayList<int[]> coupsPossibles = plateau.coupPossible(couleur);

        if (coupsPossibles.isEmpty()) {
            return null;
        }

        // Choisir un coup aléatoire parmi les coups possibles
        int indiceAleatoire = random.nextInt(coupsPossibles.size());
        return coupsPossibles.get(indiceAleatoire);
    }
}