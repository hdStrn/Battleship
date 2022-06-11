package battleship;

public class ShipCoordinates {

    public static int[][] coordinates(String textCoordinates, String shipName, int shipLength) {
        int startRow = 0;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;
        String[] coordinates = textCoordinates.split(" ");
        if (coordinates.length != 2) {
            System.out.println("\nError! Wrong input format!\n");
        } else {
            startRow = coordinates[0].charAt(0) - 65;
            startCol = Integer.parseInt(coordinates[0].substring(1)) - 1;
            endRow = coordinates[1].charAt(0) - 65;
            endCol = Integer.parseInt(coordinates[1].substring(1)) - 1;
            if (isCoordinatesNotCorrect(startRow, startCol) || isCoordinatesNotCorrect(endRow, endCol)) {
                System.out.println("\nError! Wrong coordinates!\n");
            }
        }
        int[][] shipEnds = new int[][]{{startRow, startCol}, {endRow, endCol}};
        if (isShipNotStraight(startRow, startCol, endRow, endCol)) {
            System.out.println("\nError! Wrong ship location! Try again:\n");
        } else if (isShipLengthNotCorrect(startRow, startCol, endRow, endCol, shipLength)) {
            System.out.println("\nError! Wrong length of the " + shipName + "! Try again:\n");
        }
        return shipEnds;
    }

    public static boolean isCoordinatesNotCorrect(int row, int col) {
        return row < 0 || row > 9 || col < 0 || col > 9;
    }

    public static boolean isShipNotStraight(int startRow, int startCol, int endRow, int endCol) {
        return startRow != endRow && startCol != endCol;
    }

    public static boolean isShipLengthNotCorrect(int startRow, int startCol, int endRow, int endCol, int length) {
        return Math.abs(startRow - endRow) != length - 1 && Math.abs(startCol - endCol) != length - 1;
    }
}
