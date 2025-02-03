package pgdp.recursion;

public class BigGameField {
    protected int[][] board; // 9x9 game field (0 = empty)
    protected boolean[][] fixed; // Indicates whether a cell is fixed

    public BigGameField(int[][] initialGrid) {
        board = new int[9][9];
        fixed = new boolean[9][9];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) { //x j = x
                board[i][j] = initialGrid[i][j];
                if (initialGrid[i][j] == 0) {
                    fixed[i][j] = false;
                } else {
                    fixed[i][j] = true;
                }
            }
        }
        //TODO : Initialize the field at the beginning.
    }

    protected boolean isSafe(int row, int col, int num) {
        int startrow = (row / 3) * 3;
        int startcol = (col / 3) * 3;
        return isRowSafe(row, num) && isColSafe(col, num) && isBlockSafe(startrow, startcol, num);
        //TODO : Check if the position (row, col) is safe for the penguin from family number num.
    }

    protected boolean isRowSafe(int row, int num) {
        return isRowSafeHelper(row, num, 0);
    }

    private boolean isRowSafeHelper(int row, int num, int col) {
        if (col == 9) {
            return true;
        }
        if (board[row][col] == num) {
            return false;
        }
        return isRowSafeHelper(row, num, col + 1);
    }

    protected boolean isColSafe(int col, int num) {
        return isColSafeHelper(col, num, 0);
    }

    private boolean isColSafeHelper(int col, int num, int row) {
        if (row >= 9) {
            return true;
        }
        if (board[row][col] == num) {
            return false;
        }
        return isColSafeHelper(col, num, row + 1);
        //TODO : Check recursively if the column is safe.
    }

    protected boolean isBlockSafe(int startRow, int startCol, int num) {
        //TODO : Check recursively if the column is safe.
        return isBlockSafeHelper(startRow, startCol, num, startRow + 2, startCol + 2);
    }

    private boolean isBlockSafeHelper(int startRow, int startCol, int num, int endRow, int endCol) {
        if (board[startRow][startCol] == num) {
            return false;
        }
        if (startRow == endRow && startCol == endCol) {
            return true;
        }
        if (startCol == endCol) {
            return isBlockSafeHelper(startRow + 1, startCol - 2, num, endRow, endCol);
        }
        return isBlockSafeHelper(startRow, startCol + 1, num, endRow, endCol);
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    protected void placePenguin(int row, int col, int num) {
        //TODO : Place a penguin with family number (num) at position (row, col).
        if (!fixed[row][col] && isSafe(row, col, num)) {
            board[row][col] = num;
        }
    }


    protected void removePenguin(int row, int col) {
        //TODO : Remove a penguin from position (row, col).
        if (!fixed[row][col]) {
            board[row][col] = 0;
        }
    }

    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
