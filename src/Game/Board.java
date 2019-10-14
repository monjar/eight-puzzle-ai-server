package Game;

import java.util.*;

public class Board {
    private String[][] grid;
    private int size;

    private int emptyX, emptyY;

    public Board(int size, String ...options) {
        this.size = size;
        this.grid = new String[size][size];
        if (options[0].equals("input"))
            fillWithInput();
        if (options[0].equals("rand"))
            randomFillBoard(Integer.valueOf(options[1]));
    }

    private void fillWithInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("enter board initial state: ");
        for (int i = 0; i < size; i++) {
            grid[i] = in.nextLine().split("\t");
        }
        findEmpty();
        System.out.println("board initialized.");
    }

    private void findEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].equals("0"))
                    updateEmptyCell(i, j);
            }
        }
    }


    public void makeMove(Directions dir) throws WrongMoveException {
        switch (dir) {
            case RIGHT:
                this.moveEmptyCell(this.emptyY, this.emptyX + 1);
                break;
            case LEFT:
                this.moveEmptyCell(this.emptyY, this.emptyX - 1);
                break;
            case DOWN:
                this.moveEmptyCell(this.emptyY + 1, this.emptyX);
                break;
            case UP:
                this.moveEmptyCell(this.emptyY - 1, this.emptyX);
                break;
        }
    }

    private void moveEmptyCell(int y, int x) throws WrongMoveException {
        if (isCellOutOfBounds(y, x))
            throw new WrongMoveException();
        else
            swapCell(y, x);
    }

    private void swapCell(int y, int x) {
        String temp = this.grid[y][x];
        this.grid[y][x] = this.grid[this.emptyY][this.emptyX];
        this.grid[this.emptyY][this.emptyX] = temp;
        this.updateEmptyCell(y, x);
    }

    private boolean isCellOutOfBounds(int y, int x) {
        return y >= this.size || x >= this.size || y < 0 || x < 0;
    }

    private void randomFillBoard(int movesNum) {
        System.out.println("Initializing random board ");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                grid[i][j] = String.valueOf(i * this.size + j + 1);

        grid[size - 1][size - 1] = "0";
        updateEmptyCell(size - 1, size - 1);
        makeRandomMoves(movesNum);
        System.out.println("board initialized.");
    }

    private void makeRandomMoves(int movesNum) {
        Directions[] directions = {Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT};
        for (int i = 0; i < movesNum; i++) {
            try {
                makeMove(directions[(int) (Math.random() * 4)]);
            } catch (WrongMoveException e) {
            }
        }
    }

    private void putNumberInCell(List<String> numSet, int i, int j) {
        grid[i][j] = numSet.get(numSet.size() - 1);
        if (grid[i][j].equals("0"))
            updateEmptyCell(i, j);
        numSet.remove(numSet.size() - 1);
    }

    private void updateEmptyCell(int i, int j) {
        this.emptyY = i;
        this.emptyX = j;
    }

    private List<String> makeRandomOrderNumbers() {
        List<String> numSet = new ArrayList<>();
        for (int i = 0; i < size * size; i++)
            numSet.add(String.valueOf(i));

        Collections.shuffle(numSet);
        return numSet;
    }

    public void printBoard() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder printString = new StringBuilder();
        buildGridString(printString);
        return printString.toString();
    }

    private void buildGridString(StringBuilder printString) {
        for (int i = 0; i < size; i++) {
            appendRowToString(printString, grid[i]);
            printString.append("\n");
        }
    }

    private void appendRowToString(StringBuilder printString, String[] strings) {
        for (int j = 0; j < size; j++)
            appendCellToString(printString, strings[j]);
    }

    private void appendCellToString(StringBuilder printString, String string) {
        if (string.equals("0"))
            printString.append(" ").append("\t");
        else
            printString.append(string).append("\t");
    }

    public boolean isFinished() {
        boolean isSequential = true;
        for (int i = 0; i < this.size; i++)
            isSequential = checkRow(isSequential, i);
        return isSequential;
    }

    private boolean checkRow(boolean isSequential, int i) {
        for (int j = 0; j < this.size; j++) {
            if (isLastCell(i, j))
                break;
            if (isCellValueWrong(i, j))
                isSequential = false;
        }
        return isSequential;
    }

    private boolean isLastCell(int i, int j) {
        return j == this.size - 1 && i == this.size - 1;
    }

    private boolean isCellValueWrong(int i, int j) {
        return Integer.valueOf(this.grid[i][j]) != i * this.size + j + 1;
    }
}
