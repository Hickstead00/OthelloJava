package modele.ia;
import modele.Plateau;

public interface StrategieIA {
    int[] calculerCoup(Plateau plateau, String couleur);
    String getNom();
}