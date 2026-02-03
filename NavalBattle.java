import java.util.Random;
import java.util.Scanner;

public class NavalBattle {

    static String[][] grille = new String[10][10];

    // public static final String RED = "\u001B[31m";
    // public static final String BLUE = "\u001B[34m";
    // public static final String PURPLE = "\u001B[35m";
    // public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) {
        String[] shipName = { "ac", "cruiser", "destroyer", "submarine", "torpedoBoat" };
        int[] shipSize = { 5, 4, 3, 3, 2 };

        // try (Scanner xy = new Scanner(System.in)) {
        // System.out.println("Choose a case to shoot : ");

        // Initialisation de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grille[i][j] = "-"; // Water all case
            }
        }

        // Affichage de la grille
        System.out.print("   ");
        for (int i = 0; i < 10; i++) {
            char colLetter = (char) ('A' + i);
            System.out.print(colLetter + " "); // Lettre A à J colonne
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            if (i + 1 < 10) {
                System.out.print(" " + (i + 1) + " "); // ligne 1 à 10
            } else {
                System.out.print((i + 1) + " ");
            }

            for (int j = 0; j < 10; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
        // Placer les bateaux
        for (int i = 0; i < shipSize.length; i++) {
            setShip(shipSize[i], shipName[i]);
        }
    }

    public static void setShip(int shipSize, String shipName) {
        Random random = new Random();

        // variable qui indique si le bateau
        boolean isSet = false;

        while (isSet == false) { // tant que pas positionné, on retente de positionner le bateau

            // 1. on cherche une case random
            int line = random.nextInt(10);
            int column = random.nextInt(10);
            int direction = random.nextInt(4); // le random pour la direction

            // compteur du nombre de cases disponibles
            // si shipSpaceCount == 5
            int shipSpaceCount = 0;
            int tempLine = line;
            int tempColumn = column;

            if (direction == 0) { // cas du haut

                // tant que y'a de la place et qu'on est pas sorti
                while (tempLine >= 0 && shipSpaceCount < shipSize) {
                    if (grille[tempLine][column].equals("-")) {
                        // si c'est de la mer, y'a de la place, c'est okay pour le bateau
                        shipSpaceCount++;
                        tempLine--; // on remonte d'un cran
                    } else { // PANIC, y'a pas la place
                        break;
                    }
                }

                if (shipSpaceCount == shipSize) { // y'a assez de place pour le bateau, on le pose
                    for (int k = 0; k < shipSize; k++) {
                        grille[line - k][column] = shipName.substring(0, 1).toUpperCase();
                    }
                    isSet = true;
                }
            } else if (direction == 1) { // on part à droite

                while (tempColumn < 10 && shipSpaceCount < shipSize) {
                    if (grille[line][tempColumn].equals("-")) {
                        shipSpaceCount++;
                        tempColumn++;
                    } else {
                        break;
                    }
                }

                if (shipSpaceCount == shipSize) {
                    for (int k = 0; k < shipSize; k++) {
                        grille[line][column + k] = shipName.substring(0, 1).toUpperCase();
                    }
                    isSet = true;
                }
            }

            else if (direction == 2) { // on part en bas

                while (tempLine < 10 && shipSpaceCount < shipSize) {
                    if (grille[tempLine][column].equals("-")) {
                        shipSpaceCount++;
                        tempLine++;
                    } else {
                        break;
                    }
                }

                if (shipSpaceCount == shipSize) {
                    for (int k = 0; k < shipSize; k++) {
                        grille[line + k][column] = shipName.substring(0, 1).toUpperCase();
                    }
                    isSet = true;
                }
            }

            else if (direction == 3) { // on part à gauche

                while (tempColumn >= 0 && shipSpaceCount < shipSize) {
                    if (grille[line][tempColumn].equals("-")) {
                        shipSpaceCount++;
                        tempColumn--;
                    } else {
                        break;
                    }
                }

                if (shipSpaceCount == shipSize) {
                    for (int k = 0; k < shipSize; k++) {
                        grille[line][column - k] = shipName.substring(0, 1).toUpperCase();
                    }
                    isSet = true;
                }
            }
        }
    }
}
