package battleship;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static battleship.Shot.*;

public class Main {

    public static final String[] PLAYERS = {"Player 1", "Player 2"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] field1 = fieldCreation();
        char[][] hiddenField1 = fieldCreation();
        char[][] field2 = fieldCreation();
        char[][] hiddenField2 = fieldCreation();
        List<char[][]> fields = Arrays.asList(field1, field2, hiddenField1, hiddenField2);
        Ship aircraft = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        List<Ship> ships = Arrays.asList(aircraft, battleship, submarine, cruiser, destroyer);
        for (int i = 0; i < PLAYERS.length; i++) {
            System.out.println(PLAYERS[i] + ", place your ships on the game field\n");
            printField(fields.get(i));
            for (Ship ship : ships) {
                System.out.println("\nEnter the coordinates of the " + ship.getName() + " (" +
                        ship.getLength() + " cells):\n");
                int[][] shipCoordinates = inputShipCoordinates(ship.getName(), ship.getLength(), fields.get(i));
                shipPlacement(fields.get(i), shipCoordinates[0][0], shipCoordinates[0][1], shipCoordinates[1][0], shipCoordinates[1][1]);
                printField(fields.get(i));
            }
            System.out.println("\nPress Enter and pass the move to another player");
            while (!sc.nextLine().equals("")) {
                System.out.println("\nPress Enter and pass the move to another player");
            }
        }
        while (!IsEndOfGame(field1) && !IsEndOfGame(field2)) {
            for (int i = 0, j = 0; i < PLAYERS.length; i++, j--) {
                printField(fields.get(i + 2));
                System.out.println("---------------------");
                printField(fields.get(i));
                System.out.println("\n" + PLAYERS[i] + ", it's your turn:\n");
                shot(fields.get(j + 1), fields.get(i + 2));
                if (IsEndOfGame(fields.get(j + 1))) {
                    break;
                }
                System.out.println("\nPress Enter and pass the move to another player");
                while (!sc.nextLine().equals("")) {
                    System.out.println("\nPress Enter and pass the move to another player");
                }
            }
        }
    }

    public static char[][] fieldCreation() {
        char[][] field = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = '~';
            }
        }
        return field;
    }

    public static void printField(char[][] field) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char row = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(row++);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    public static int[][] inputShipCoordinates(String shipName, int shipLength, char[][] field) {
        int[][] shipCoordinates;
        int startRow;
        int startCol;
        int endRow;
        int endCol;
        do {
            Scanner sc = new Scanner(System.in);
            String textCoordinates = sc.nextLine();
            shipCoordinates = ShipCoordinates.coordinates(textCoordinates, shipName, shipLength);
            startRow = shipCoordinates[0][0];
            startCol = shipCoordinates[0][1];
            endRow = shipCoordinates[1][0];
            endCol = shipCoordinates[1][1];
        } while (ShipCoordinates.isShipNotStraight(startRow, startCol, endRow, endCol)
                || ShipCoordinates.isShipLengthNotCorrect(startRow, startCol, endRow, endCol, shipLength)
                || !isShipInCorrectPosition(field, startRow, startCol, endRow, endCol));
        return shipCoordinates;
    }

    public static void shipPlacement(char[][] field, int startRow, int startCol, int endRow, int endCol) {
        int temp;
        if (endRow < startRow) {
            temp = startRow;
            startRow = endRow;
            endRow = temp;
        } else if (endCol < startCol) {
            temp = startCol;
            startCol = endCol;
            endCol = temp;
        }
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                field[i][j] = 'O';
            }
        }
    }

    public static boolean isShipInCorrectPosition(char[][] field, int startRow, int startCol, int endRow, int endCol) {
        int temp;
        if (endRow < startRow) {
            temp = startRow;
            startRow = endRow;
            endRow = temp;
        } else if (endCol < startCol) {
            temp = startCol;
            startCol = endCol;
            endCol = temp;
        }
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (!isCellAndAroundEmpty(field, i, j)) {
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCellAndAroundEmpty(char[][] field, int x, int y) {
        for (int i = -1; i < 2; i++) {
            if (x + i < 0 || x + i > 9) continue;
            for (int j = -1; j < 2; j++) {
                if (y + j < 0 || y + j > 9) {
                    continue;
                } else if (field[x + i][y + j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}