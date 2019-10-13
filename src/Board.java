import java.util.*;

public class Board {
    String[][] grid;

    int emptyX, emptyY;

    Board(int size) {
        grid = new String[size][size];
        randomFillBoard(size);
        printBoard(size);
    }


    public void makeMove()

    private void randomFillBoard(int size) {
        List<String> numSet = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            numSet.add(String.valueOf(i));
        }
        Collections.shuffle(numSet);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = numSet.get(numSet.size() - 1);
                numSet.remove(numSet.size() - 1);
            }
        }
    }

    private void printBoard(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].equals("0"))
                    System.out.print(" " + "\t");
                else
                    System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
