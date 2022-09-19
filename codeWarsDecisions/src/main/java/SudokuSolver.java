
/*
  https://www.codewars.com/kata/5588bd9f28dbb06f43000085/train/java
*/


import java.util.ArrayList;
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

                if (nowI == 2) {
                    nowGrid++;
                    nowI = 0;
                } else nowI++;
            }
            if ((j + 1) % 3 == 0) {
                lastGrid = nowGrid;
            } else if (nowJ == 2) {
                nowJ = 0;
                nowGrid = lastGrid;
            } else {
                nowJ++;
                nowGrid = lastGrid;
            }
        }

    }

    public int[][] solve() {

        for (int i = 0; i < grid9x3x3.length; i++) {
            for (int j = 0; j < grid9x3x3[i].length; j++) {
                for (int k = 0; k < grid9x3x3[i][j].length; k++) {
                    int result = checkConst(i, j, k);
//                    System.out.println(result);
                }
//                System.out.println();
            }
//            System.out.println();
        }

        return null;
    }

    private int checkConst(int i, int j, int k) {
        System.out.printf(String.valueOf(grid9x3x3[i][j][k]));
        ArrayList<Integer> lineJ = new ArrayList<Integer>();
        ArrayList<Integer> lineK = new ArrayList<Integer>();

        int iK = (i - (i + 1) % 3 == -1 ? 0 : i - (i + 1) % 3);
        int iJ = 0;


        for (int l = 0; l < 3; l++) {
            iK += l;
            for (int m = 0; m < 3; m++) {
                Integer number = grid9x3x3[iK][j][m];
                if (number != 0) lineK.add(number);
            }
        }

        for (int l = 0; l < 3; l++) {
            iJ += (3 * l - 1 == -1 ? 0 : 3 * l - 1);
            for (int m = 0; m < 3; m++) {
                Integer number = grid9x3x3[iK][m][l];
                if (number != 0) lineK.add(number);
            }
        }

        return 0;
    }

}