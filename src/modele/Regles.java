package modele;

public class Regles {

    // Noir commence toujours la partie

    // Methode qui verifie si un coup est légal
    // - Coup non légal : Le pion ne peut pas retourner d'autres pions
    // - Coup non légal : Le joueur tente de passer alors qu'il est possible de poser un pion
    //

    // Methode "adjacent"
    // - Qui étant donné un pion à une position X calcule les coordonées du carré autour de lui pour vérifier l'adjacance diagonale, verticale, horizontale

    // Methode poser un pion/jouer un tour :
    // - Adjacent à un pion adverse
    // - Si impossible de retourner un pion alors le joueur passe son tour

    // Methode capture
    // - Lorsqu'un pion est joué entre un/plusieurs pions adverses et qu'il y a également un pion de la même couleur de l'autre côté alors on retourne
    // - Si un pion posé est aligné avec plusieurs pions alliés il peut retourner plusieurs lignes à la fois (ATTENTION : les pions retournés ne sont pas pris en compte et ne créent pas de nouvelles cascades)


    // Methode qui vérifie la fin de jeu
    // - Le jeu s'arrête si aucun joueur ne peut poser de pion OU si le plateau est rempli

    // Methode qui check le gagnant
    // - Parcours de la matrice et comptage des pions ?


}
