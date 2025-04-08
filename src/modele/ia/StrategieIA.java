/**
 * Interface définissant le comportement d'une stratégie d'IA pour le jeu Othello.
 * Toute stratégie d'IA doit implémenter cette interface pour pouvoir être utilisée dans le jeu.
 */
package modele.ia;
import modele.JeuOthello;

public interface StrategieIA {
    /**
     * Calcule le meilleur coup à jouer pour l'IA selon sa stratégie.
     * @param jeuOthello L'état actuel du jeu
     * @param couleur La couleur des pions de l'IA
     * @return Un tableau de deux entiers représentant les coordonnées [ligne, colonne] du coup à jouer,
     *         ou null si aucun coup n'est possible
     */
    int[] calculerCoup(JeuOthello jeuOthello, String couleur);
}