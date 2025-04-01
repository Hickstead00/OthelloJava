package modele.ia;
import modele.JeuOthello;

public interface StrategieIA {
    int[] calculerCoup(JeuOthello jeuOthello, String couleur);
}