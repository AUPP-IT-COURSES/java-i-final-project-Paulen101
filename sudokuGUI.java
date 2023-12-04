import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Random;

public class sudokuGUI extends Application {
    private TextField[][] grid = new TextField[9][9];
    private SudokuSolver sudokuSolver = new SudokuSolver();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Game");

        GridPane sudokuGrid = createSudokuGrid();
        Button solveButton = new Button("Solve");
        solveButton.setOnAction(e -> solveSudoku());

        Button checkButton = new Button("Check Answer");
        checkButton.setOnAction(e -> checkSudoku());

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> generateRandomPuzzle());

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(solveButton, checkButton, newGameButton);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(sudokuGrid, buttonsHBox);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);

        generateRandomPuzzle(); // Generate a random puzzle on startup

        primaryStage.show();
    }

    private GridPane createSudokuGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new TextField();
                grid[i][j].setPrefSize(40, 40);
                grid[i][j].setAlignment(Pos.CENTER);
                grid[i][j].setStyle("-fx-font-size: 16; -fx-border-color: #000000; -fx-border-width: " +
                        (i % 3 == 0 ? 2 : 1) + " " + (j % 3 == 0 ? 2 : 1) + " 1 1;");

                int subgridIndex = (i / 3) * 3 + (j / 3);
                if (subgridIndex % 3 == 1) {
                    grid[i][j].setStyle(grid[i][j].getStyle() + "-fx-background-color: #D3D3D3;");
                }

                gridPane.add(grid[i][j], j, i);
            }
        }

        return gridPane;
    }

    private SudokuSolver sudokuSolver1 = new SudokuSolver();

    private void solveSudoku() {
        int[][] sudokuBoard = new int[9][9];

        // Populate the board with values from the GUI
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                if (!text.isEmpty()) {
                    sudokuBoard[i][j] = Integer.parseInt(text);
                } else {
                    sudokuBoard[i][j] = 0;
                }
            }
        }

        // Solve the Sudoku using the SudokuSolver class
        if (sudokuSolver.solveSudoku(sudokuBoard)) {
            // Display the solution in the GUI
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j].setText(Integer.toString(sudokuBoard[i][j]));
                    grid[i][j].setStyle("-fx-text-fill: green;");
                }
            }
        } else {
            // Display an error message
            System.out.println("No solution exists.");
        }
    }


    private void checkSudoku() {
        int[][] sudokuBoard = new int[9][9];

        // Populate the board with values from the GUI
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                if (!text.isEmpty()) {
                    sudokuBoard[i][j] = Integer.parseInt(text);
                } else {
                    sudokuBoard[i][j] = 0;
                }
            }
        }

        // Check the correctness of the Sudoku using the SudokuSolver class
        boolean isCorrect = sudokuSolver.solveSudoku(sudokuBoard);
        
        // Display the result in message box
        showAlert(isCorrect);
    }
    
    private void showAlert(boolean isCorrect) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sudoku Result");

        if (isCorrect) {
            alert.setHeaderText("Correct solution!");
            alert.setContentText("Congratulations, you've solved the Sudoku puzzle!");
        } else {
            alert.setHeaderText("Incorrect solution.");
            alert.setContentText("Sorry, the solution is incorrect. Keep trying!");
        }

        alert.showAndWait();
    }


    private void generateRandomPuzzle() {
        int[][] randomPuzzle = generateRandomCompletedPuzzle();

        // Hide some cells to create a puzzle
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int randomNumber = new Random().nextInt(10);
                if (randomNumber < 3) { // Adjust the threshold to control the number of hidden cells
                    grid[i][j].setText("");
                    grid[i][j].setEditable(true);
                } else {
                    grid[i][j].setText(Integer.toString(randomPuzzle[i][j]));
                    grid[i][j].setEditable(false);
                }
            }
        }
    }

    private int[][] generateRandomCompletedPuzzle() {
        int[][] sudokuBoard = new int[9][9];
        sudokuSolver.solveSudoku(sudokuBoard); // Generate a random completed puzzle
        return sudokuBoard;
    }
}
