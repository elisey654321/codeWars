import java.util.ArrayList;
import java.util.HashSet;

public class SudokuSolver {

    int[][] arr = new int[9][9];
    private int[][][] grid9x3x3 = new int[9][3][3];
    private int[] gridNoZero = new int[9];

    public SudokuSolver(int[][] grid) {

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


        this.arr = grid;
    }

    private boolean checkInvalidGrids(int[][][] grid_1) {
        int[][][] localGrid = new int[9][3][3];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    localGrid[i][j][k] = grid_1[i][j][k];

                        int result = getVariable(i, j, k, grid_1);
                        if (result == 0)
                            return true;

                }
            }
        }
        return false;
    }

    private int getVariable(int i, int j, int k, int[][][] grid) {

//        if (grid[i][j][k] != 0) return 0;

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

    public int[][] solve() {
        try {
//            System.out.printf("{");
//            for (int i = 0; i < arr.length; i++) {
//                System.out.printf("{");
//                for (int j = 0; j < arr[i].length; j++) {
//                    System.out.printf(String.valueOf(arr[i][j]) + ",");
//                }
//                System.out.printf("}");
//                System.out.println();
//            }
//            System.out.printf("}");

            boolean invalidGrids = checkInvalidGrids(grid9x3x3);

            boolean answer = sudoku(arr);
            if (!answer || invalidGrids){
                throw new IllegalArgumentException();
            }
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
        return arr;
    }

    public static boolean sudoku(int[][] grid) {
        int[] ra = Unassigned(grid);
        if (ra[0] == -1) {
            return true;
        }

        int row = ra[0];
        int col = ra[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, col, num)) {

                grid[row][col] = num;
                boolean check = sudoku(grid);
                if (check == true) {
                    return true;
                }
                grid[row][col] = 0;


            }
        }
        return false;
    }

    public static int[] Unassigned(int[][] arr) {

        int[] ra = new int[2]; //returns the position of first unassigned position
        ra[0] = -1;
        ra[1] = -1;

        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr.length; col++) {
                if (arr[row][col] == 0) {
                    ra[0] = row;
                    ra[1] = col;
                    return ra;
                }
            }
        }

        return ra;


    }//returns the first unassigned position

    public static boolean usedInRow(int[][] grid, int row, int num) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[row][i] == num) {
                return true;
            }
        }
        return false;
    }//is it used in that row?

    public static boolean usedIncol(int[][] grid, int col, int num) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][col] == num) {
                return true;
            }
        }
        return false;
    }//is it used in that col?

    public static boolean usedInBox(int[][] grid, int row1Start, int col1Start, int num) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid[row + row1Start][col + col1Start] == num) {
                    return true;
                }
        return false;

    }//is it used in that box?

    public static boolean isSafe(int[][] grid, int row, int col, int num) {//is it safe to place that number at that position, might not be correct nut just safe

        return (!usedIncol(grid, col, num) && !usedInRow(grid, row, num) && !usedInBox(grid, row - row % 3, col - col % 3, num));

    }

}