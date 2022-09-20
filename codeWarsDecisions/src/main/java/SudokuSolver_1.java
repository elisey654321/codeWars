
/*
  https://www.codewars.com/kata/5588bd9f28dbb06f43000085/train/java
*/


import java.util.ArrayList;
import java.util.HashSet;

public class SudokuSolver_1 {
    //test
    private int[][] grid;
    private int[][][] grid9x3x3 = new int[9][3][3];
    private int[][][] grid9x3x3_1 = new int[9][3][3];
    private int[][][] grid9x3x3_2 = new int[9][3][3];
    private int[] gridNoZero = new int[9];
    private boolean working = true;

    public SudokuSolver_1(int[][] grid) {
        this.grid = grid;

        int nowGrid = 0;
        int nowI = 0;
        int nowJ = 0;
        int lastGrid = 0;

        for (int j = 0; j < grid.length; j++) {

            for (int i = 0; i < grid[j].length; i++) {
                grid9x3x3[nowGrid][nowJ][nowI] = grid[j][i];
                if (grid[i][j] != 0) gridNoZero[nowGrid]++;
                if (nowI == 2) {
                    nowGrid++;
                    nowI = 0;
                } else nowI++;
            }
            if ((j + 1) % 3 == 0) {
                lastGrid = nowGrid;
                nowJ = 0;
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

        System.out.printf("{");

        for (int i = 0; i < grid.length; i++) {
            System.out.printf("{");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.printf(String.valueOf(grid[i][j]) + ",");
            }
            System.out.printf("}");
            System.out.println();
        }
        System.out.printf("}");

        int[][][] gridAnswer = checkGrid(grid9x3x3);
        int[][] answer = reversMatrix(gridAnswer);
        return answer;
    }

    private int[][][] checkGrid(int[][][] grid_1) {
        int[][][] localGrid_1 = new int[9][3][3];
        int[][][] localGrid = new int[9][3][3];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    localGrid[i][j][k] = grid_1[i][j][k];
                    if (grid_1[i][j][k] == 0) {
                        int result = getVariable(i, j, k, grid_1);
                        if (result == 0)
                            return null;
                    }
                }
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 3; k++) {
                    if (localGrid[i][j][k] != 0) continue;
                    int result = getVariable(i, j, k, localGrid);

                    if (result != 0 && result < 10) {
                        localGrid[i][j][k] = result;
                    } else {
                        String variable = String.valueOf(result);
                        for (int l = 0; l < variable.length(); l++) {
                            int number = Integer.valueOf(variable.substring(l, l + 1));
                            if (number == 0)
                                return null;
                            localGrid[i][j][k] = number;
                            localGrid_1 = checkGrid(localGrid);
                            if (localGrid_1 != null) {
                                return localGrid_1;
                            }
                        }
                    }
                }
            }
        }

        return localGrid;
    }

    private int getVariable(int i, int j, int k, int[][][] grid) {

        if (grid[i][j][k] != 0) return 0;

//        if (i == 0 && j == 1 && k == 2)
//            System.out.println("test");


        Integer resultInt = 0;
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> lineK = new ArrayList<>();
        HashSet<Integer> lineAll = new HashSet<>();

        int beginIK = i - i % 3;
        int iJ = 0;
        int iK = 0;

        for (int l = 0; l < 3; l++) {
            iK = beginIK + l;
            for (int m = 0; m < 3; m++) {
                try {
                    Integer number = grid[iK][j][m];
                    if (number != 0) {
                        lineK.add(number);
                        lineAll.add(number);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        for (int l = 0; l < 3; l++) {
            iJ = i % 3 + l * 3;
            for (int m = 0; m < 3; m++) {
                try {
                    Integer number = grid[iJ][m][k];
                    if (number != 0) {
                        lineK.add(number);
                        lineAll.add(number);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                Integer number = grid[i][l][m];
                if (number != 0)
                    lineAll.add(number);
            }
        }
        for (int l = 1; l < 10; l++) {
            if (!lineAll.contains(l)) {
                result.append(l);
            }
        }

        if (!result.toString().equals("")) {
            resultInt = Integer.valueOf(result.toString());
        }
//            System.out.println("test");

        return resultInt;
    }

    private int checkSingle(int i, int j, int k, int[][][] grid9x3x3) {

        int resultInt = getVariable(i, j, k, grid9x3x3);

        if (!(String.valueOf(resultInt).length() == 1)) {
            resultInt = 0;
        }

        return resultInt;
    }

    private int[][] reversMatrix(int[][][] grid) {
        int nowGrid = 0;
        int nowI = 0;
        int nowJ = 0;
        int lastGrid = 0;

        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            nowJ = ((3 * (i%3)) == -1 ? 0 : (3 * (i%3)));
            nowI = i -(i%3);
            for (int j = 0; j < 3; j++) {

                for (int k = 0; k < 3; k++) {
                    try {
                        result[nowI][nowJ] = grid[i][j][k];
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    nowJ++;
                }
                nowI++;
                nowJ = ((3 * (i%3)) == -1 ? 0 : (3 * (i%3)));
            }
        }

        return result;
    }

}