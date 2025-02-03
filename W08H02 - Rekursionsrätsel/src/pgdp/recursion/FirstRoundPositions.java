package pgdp.recursion;

public class FirstRoundPositions {
    protected BigGameField bigGameField;
    protected boolean solutionFound;

    public FirstRoundPositions(int[][] initialGrid) {
        this.bigGameField = new BigGameField(initialGrid);
        this.solutionFound = false;
    }

    protected boolean placeAllPenguins(int row, int col) {
        if (row == 9) {
            return true;
        }

        int nextRow;
        int nextCol;
        if (col == 8) {
            nextRow = row + 1;
            nextCol = 0;
        } else {
            nextRow = row;
            nextCol = col + 1;
        }
        if (bigGameField.isFixed(row, col)) {
            return placeAllPenguins(nextRow, nextCol);
        }
        for (int i = 1; i <= 9; i++) {
            if (bigGameField.isSafe(row, col, i)) {
                bigGameField.placePenguin(row, col, i);
                if (placeAllPenguins(nextRow, nextCol)) {
                    return true;
                }
                bigGameField.removePenguin(row, col);
            }
        }
        return false;
        // TODO : Place all penguins on the 9x9 field recursively. If possible, return true.
    }

    public void solvePuzzle() {
        if (placeAllPenguins(0, 0)) {
            System.out.println("Solution found:");
            bigGameField.printGrid();
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        int[][] mediumGrid = {
                {0, 0, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        System.out.println("Solution found - medium grid:");
        FirstRoundPositions solver2 = new FirstRoundPositions(mediumGrid);
        solver2.solvePuzzle();

//        int[][] hardGrid = {
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 3, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 1, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 2, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 6, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 4, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0}
//        };
//
//        System.out.println("Solution found - hard grid:");
//        FirstRoundPositions solver3 = new FirstRoundPositions(hardGrid);
//        solver3.solvePuzzle();
    }
}