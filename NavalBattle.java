
import java.util.Scanner;

public class NavalBattle {

    // public static final String RED = "\u001B[31m";
    // public static final String BLUE = "\u001B[34m";
    // public static final String PURPLE = "\u001B[35m";
    // public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) {

        try (Scanner xy = new Scanner(System.in)) {
            System.out.println("Choose a case to shoot : ");

            // int aircraftCarrier = 5;
            // int cruiser = 4;
            // int destroyer = 3;
            // int submarine = 3;
            // int orpedoBoat = 2;
            String[][] grille = new String[10][10];

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
                System.out.print(colLetter + " ");  // Lettre A à J colonne
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
        }
    }
}
