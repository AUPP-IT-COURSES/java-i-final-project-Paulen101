public class SudokuSolver {

    public boolean solveSudoku(int[][] board) {
        int[] emptyCell = findEmptyCell(board);

        // If no empty cell is found, the puzzle is solved
        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        // Try placing digits 1 through 9 in the empty cell
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(board, row, col, num)) {
                // Place the number if it's a valid move
                board[row][col] = num;

                // Recursively try to solve the rest of the puzzle
                if (solveSudoku(board)) {
                    return true;
                }

                // If the current placement doesn't lead to a solution, backtrack
                board[row][col] = 0;
            }
        }

        // No valid number found, backtrack
        return false;
    }

    private int[] findEmptyCell(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        // All cells filled
        return null;
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        // Check if the number is not in the same row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check if the number is not in the same 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
