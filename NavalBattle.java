import java.util.Random;
import java.util.Scanner;

public class NavalBattle {

    static String[][] grille = new String[10][10];
    static String[] shipNames = { "A", "C", "D", "S", "T" }; // initiales des bateaux
    static int[] shipSizes = { 5, 4, 3, 3, 2 }; // tailles restantes
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[96m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLACK = "\u001B[30m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        String[] shipName = { "ac", "cruiser", "destroyer", "submarine", "torpedoBoat" };
        int[] shipSize = { 5, 4, 3, 3, 2 };

        // Initialisation et placement des bateaux
        initGrille();
        for (int i = 0; i < shipSize.length; i++) {
            setShip(shipSize[i], shipName[i]);
        }

        displayGrille(); // Affiche la grille initiale

        // Envoie des missiles
        try (Scanner xy = new Scanner(System.in)) {
            int maxShots = 70;
            int shots = 0;
            int remainingShipCells = 17; // Taille de tout les bateaux réunis

            while (shots < maxShots && remainingShipCells > 0) {
                System.out.println("Shot " + (shots + 1) + " / " + maxShots);
                System.out.print("Choose a cell to shoot :  ");
                String input = xy.nextLine().toUpperCase();

                // Tir et mise à jour du compteur
                if (shoot(input))
                    remainingShipCells--;

                shots++;
                // Affiche la grille après chaque tir
                displayGrille();
            }

            // Résultat final
            if (remainingShipCells == 0) {
                System.out.println("All ship are flowed, Nice job Captain ! ");
            } else {
                System.out.println("You lose, no ammo left ! ");
            }
        }
    }

    // --- Initialisation et affichage de la grille ---
    public static void initGrille() {
        // Initialisation de la grille avec l'eau bleue
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grille[i][j] = "-"; // Eau
            }
        }
    }

    // Affichage de la grille
    public static void displayGrille() {
         String[] legend = {
        BLUE + "- " + RESET + ": Case non tirée",
        GREEN + "O " + RESET + ": Tir manqué",
        RED + "X " + RESET + ": Touché",
        BLACK + "# " + RESET + ": Navire coulé"
    };
       // Affichage des colonnes
    System.out.print("   ");
    for (int i = 0; i < 10; i++) {
        System.out.print((char) ('A' + i) + "  "); // 2 espaces pour aligner
    }
    System.out.println();

    for (int i = 0; i < 10; i++) {
        // Numéro de ligne
        System.out.print((i < 9 ? " " : "") + (i + 1) + " ");

        for (int j = 0; j < 10; j++) {
            String cell = grille[i][j];
            String color;

            switch (cell) {
                case "O":
                    color = GREEN;
                    break;
                case "X":
                    color = RED;
                    break;
                case "#":
                    color = BLACK;
                    break;
                default:
                    color = BLUE;
            }

            // Bateau non touché = eau "-"
            if (cell.equals("A") || cell.equals("C") || cell.equals("D") || cell.equals("S") || cell.equals("T")) {
                System.out.print(BLUE + "-" + RESET + "  ");
            } else {
                System.out.print(color + cell + RESET + "  ");
            }
        }

        // Affiche la légende sur certaines lignes (centrée verticalement)
        if (i >= 3 && i <= 6) {
            System.out.print("       " + legend[i - 3]);
        }

        System.out.println();
    }
}

    // Placement aléatoire des bateaux
    public static void setShip(int shipSize, String shipName) { // Placement aléatoire des bateaux
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

    // Tir du joueur
    public static boolean shoot(String input) {
        // Vérifier que la saisie a au moins 2 caractères
        if (input.length() < 2) {
            System.out.println("Invalid input!");
            return false;
        }
        // Conversion A5 -> ligne/colonne
        int column = input.charAt(0) - 'A';
        int line = Integer.parseInt(input.substring(1)) - 1;

        try {
            line = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number!");
            return false;
        }

        // Vérifier que la ligne et la colonne sont dans les limites
        if (line < 0 || line >= 10 || column < 0 || column >= 10) {
            System.out.println("Coordinates out of the map !");
            return false;
        }

        String cell = grille[line][column];

        if (cell.equals("-")) {
            grille[line][column] = "O"; // Tir manqué
            System.out.println("Miss!");
            return false;
        } else if (cell.equals("X") || cell.equals("O") || cell.equals("#")) {
            System.out.println("Already shot here!");
            return false;
        } else {
            // Touché
            grille[line][column] = "X";
            System.out.println("Hit!");

            // Trouver quel bateau a été touché
            for (int i = 0; i < shipNames.length; i++) {
                if (shipNames[i].equals(cell)) {
                    shipSizes[i]--; // réduit la taille restante
                    if (shipSizes[i] == 0) {
                        System.out.println("Ship " + cell + " is sunk!");
                        markSunk(cell); // marquer toutes les cases du bateau en #
                    }
                    break;
                }
            }
            return true;
        }
    }

    // Marquer un navire coulé
    public static void markSunk(String shipLetter) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grille[i][j].equals(shipLetter) || grille[i][j].equals("X")) {
                    grille[i][j] = "#"; // navire coulé
                }
            }
        }
    }
}
