import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class sudokuGUI extends Application {
    private TextField[][] grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Game");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createGrid());
        borderPane.setBottom(createButtons(primaryStage));

        Scene scene = new Scene(borderPane, 400, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        grid = new TextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new TextField();
                grid[i][j].setPrefSize(40, 40);
                grid[i][j].setAlignment(Pos.CENTER);
                grid[i][j].setStyle("-fx-font-size: 16;");
                gridPane.add(grid[i][j], j, i);
            }
        }

        return gridPane;
    }

    private HBox createButtons(Stage primaryStage) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        Button solveButton = new Button("Solve");
        solveButton.setOnAction(e -> solveSudokuFromGUI());
        Button checkButton = new Button("Check");
        checkButton.setOnAction(e -> checkSudoku());
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearGrid());
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> startNewGame());

        hbox.getChildren().addAll(solveButton, checkButton, clearButton, newGameButton);
        return hbox;
    }

    private void solveSudokuFromGUI() {
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

        // Solve the Sudoku
        if (sudoku.solveSudoku(sudokuBoard)) {
            // Display the solution in the GUI
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j].setText(Integer.toString(sudokuBoard[i][j]));
                    grid[i][j].setStyle("-fx-text-fill: green;");
                }
            }
        } else {
            displayMessage("No solution exists.");
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

        // Check the correctness of the Sudoku
        if (sudoku.solveSudoku(sudokuBoard)) {
            displayMessage("Correct solution!");
        } else {
            displayMessage("Incorrect solution.");
        }
    }

    private void clearGrid() {
        // Clear all text fields in the grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].clear();
                grid[i][j].setStyle("-fx-text-fill: black;");
            }
        }
    }

    private void startNewGame() {
        // You can implement a logic to generate a new Sudoku puzzle here
        // For simplicity, let's just clear the grid for now
        clearGrid();
    }

    private void displayMessage(String message) {
        Stage stage = new Stage();
        stage.setTitle("Sudoku Game");
        stage.setScene(new Scene(new HBox(10), 200, 100));
        stage.show();
    }
}
