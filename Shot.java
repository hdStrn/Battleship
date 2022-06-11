package battleship;

import java.util.Scanner;

import static battleship.Main.*;
import static battleship.ShipCoordinates.*;

public class Shot {

    public static int[] textIntoCoordinates() {
        Scanner sc = new Scanner(System.in);
        String textCoordinate = sc.next();
        int row = textCoordinate.charAt(0) - 65;
        int col = Integer.parseInt(textCoordinate.substring(1)) - 1;
        if (isCoordinatesNotCorrect(row, col)) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
        }
        return new int[]{row, col};
    }

    public static void shot(char[][] field, char[][] hiddenField) {
        int[] hit;
        do {
            hit = textIntoCoordinates();
            if (!isCoordinatesNotCorrect(hit[0], hit[1])) {
                if (field[hit[0]][hit[1]] == 'O' || field[hit[0]][hit[1]] == 'X') {
                    hiddenField[hit[0]][hit[1]] = 'X';
                    field[hit[0]][hit[1]] = 'X';
                    if (IsEndOfGame(field)) {
                        System.out.println("\nYou sank the last ship. You won. Congratulations!");
                        break;
                    } else if (isCellAndAroundEmpty(field, hit[0], hit[1])) {
                        System.out.println("\nYou sank a ship!");
                    } else {
                        System.out.println("\nYou hit a ship!");
                    }
                } else if (field[hit[0]][hit[1]] == '~' || field[hit[0]][hit[1]] == 'M') {
                    hiddenField[hit[0]][hit[1]] = 'M';
                    field[hit[0]][hit[1]] = 'M';
                    System.out.println("\nYou missed!");
                }
            }
        } while (isCoordinatesNotCorrect(hit[0], hit[1]));
    }

    public static boolean IsEndOfGame(char[][] field) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
