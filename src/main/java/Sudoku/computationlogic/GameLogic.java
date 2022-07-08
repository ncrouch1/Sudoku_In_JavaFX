package Sudoku.computationlogic;

import Sudoku.SudokuDomain.SudokuGame;
import Sudoku.constants.constants.GameState;
import Sudoku.constants.constants.Rows;

import java.util.*;

import static Sudoku.SudokuDomain.SudokuGame.GRID_BOUNDARY;

/*
 * 7/7/22 Game logic patch --
 * Fixed faulty chained if statements with changed or statements instead such that the programm
 * checks each row of squares completely instead of only checkings one or two and returning true. Also
 * in the validation method I chained the chaining if statements to an or statement too because the implementation
 * simply did not make sense
 *
 */



public class GameLogic {

    public static SudokuGame getNewGame() {
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }
    public static GameState checkForCompletion(int[][] grid) {
        if (sudokuIsInvalid(grid)
                || tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    public static boolean sudokuIsInvalid(int[][] grid) {
        if (rowsAreInvalid(grid)
                || columnsAreInvalid(grid)
                || squaresAreInvalid(grid)) return true;
        return false;
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                row.add(grid[x][y]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }

    private static boolean columnsAreInvalid(int[][] grid) {
        for (int y = 0; y < GRID_BOUNDARY; y++) {
            List<Integer> col = new ArrayList<>();
            for (int x = 0; x < GRID_BOUNDARY; x++) {
                col.add(grid[x][y]);
            }
            if (collectionHasRepeats(col)) return true;
        }
        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid) {
        if (rowOfSquaresAreInvalid(Rows.TOP, grid)
                || rowOfSquaresAreInvalid(Rows.MIDDLE, grid)
                || rowOfSquaresAreInvalid(Rows.BOTTOM, grid)) return true;
        return false;
    }

    private static boolean rowOfSquaresAreInvalid(Rows value, int[][] grid) {
        switch (value) {
            case TOP -> {
                if (squareIsInvalid(0, 0, grid)
                        || squareIsInvalid(3, 0, grid)
                        || squareIsInvalid(6, 0, grid)) return true;
                return false;
            }
            case MIDDLE -> {
                if (squareIsInvalid(0, 3, grid)
                        || squareIsInvalid(3, 3, grid)
                        || squareIsInvalid(6, 3, grid)) return true;
                return false;
            }
            case BOTTOM -> {
                if (squareIsInvalid(0, 6, grid)
                        || squareIsInvalid(3, 6, grid)
                        || squareIsInvalid(6, 6, grid)) return true;
                return false;
            }
        }
        return true;
    }

    private static boolean squareIsInvalid(int xIndex, int yIndex, int[][] grid) {
        int yIndexEnd = yIndex + 3;
        int xIndexEnd = xIndex + 3;

        List<Integer> square = new ArrayList<>();

        while (yIndex < yIndexEnd) {
            while (xIndex < xIndexEnd) {
                square.add(grid[xIndex][yIndex]);
                xIndex++;
            }
            xIndex -= 3;
            yIndex++;
        }

        return collectionHasRepeats(square);
    }

    private static boolean collectionHasRepeats(List<Integer> collection) {
        for (int index = 1; index <= GRID_BOUNDARY; index++) {
            if (Collections.frequency(collection, index) > 1) return true;
        }
        return false;
    }


    private static boolean tilesAreNotFilled(int[][] grid) {
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                if (grid[x][y] == 0) return true;
            }
        }

        return false;
    }

}
