
/*
  https://www.codewars.com/kata/5588bd9f28dbb06f43000085/train/java
*/


import java.util.Arrays;

public class SudokuSolver {

    private int[][] grid;
    private int[][][] grid9x3x3 = new int[9][3][3];
    private int[] gridNoZero = new int[9];

    public SudokuSolver(int[][] grid) {
        this.grid = grid;

        int nowGrid = 0;
        int nowI = 0;
        int nowJ = 0;
        int lastGrid = 0;

        for (int j = 0; j < grid.length; j++) {
            for (int i = 0; i < grid[j].length; i++) {
                grid9x3x3[nowGrid][nowJ][nowI] = grid[i][j];
                if (grid[i][j] != 0) gridNoZero[nowGrid]++;

                if (nowI == 2){
                    nowGrid++;
                    nowI = 0;
                }else nowI++;
            }
            if ((j+1)%3 == 0){
                lastGrid = nowGrid;
            } else if (nowJ == 2){
                nowJ = 0;
                nowGrid = lastGrid;
            }else {
                nowJ++;
                nowGrid = lastGrid;
            }
        }

        for (int i = 0; i < grid9x3x3.length; i++) {
            for (int j = 0; j < grid9x3x3[i].length; j++) {
                for (int k = 0; k < grid9x3x3[i][j].length; k++) {
                    System.out.printf(String.valueOf(grid9x3x3[i][j][k]));
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    public int[][] solve() {

        return null;
    }
}