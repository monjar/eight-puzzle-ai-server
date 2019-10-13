import java.util.*;

public class Board {
    String[][] grid;
    int size;

    int emptyX, emptyY;

    Board(int size) {
        this.size = size;
        this.grid = new String[size][size];
        randomFillBoard();
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

    public void moveEmptyCell(int y, int x) throws WrongMoveException {
        if (isCellOutOfBounds(y, x))
            throw new WrongMoveException();
        else {
            String temp = this.grid[y][x];
            this.grid[y][x] = this.grid[this.emptyY][this.emptyX];
            this.grid[this.emptyY][this.emptyX] = temp;
            this.emptyY = y;
            this.emptyX = x;

        }
    }

    private boolean isCellOutOfBounds(int y, int x) {
        return y >= this.size || x >= this.size || y < 0 || x < 0;
    }

    private void randomFillBoard() {
        List<String> numSet = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            numSet.add(String.valueOf(i));
        }
        Collections.shuffle(numSet);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = numSet.get(numSet.size() - 1);
                if (grid[i][j].equals("0")){
                    this.emptyX = j;
                    this.emptyY = i;
                }
                numSet.remove(numSet.size() - 1);
            }
        }
    }
    private void fillToWin(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <size ; j++) {
                this.grid[i][j] = String.valueOf(i*this.size + j + 1);
            }
        }

        String temp = this.grid[size -1 ][ size -1];
        this.grid[size -1][size -1] = this.grid[size -1][size -2];
        this.grid[size -1][size -2] = "0";
        this.emptyY = size -1;
        this.emptyX = size - 2;




    }

    public void printBoard() {
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

    public boolean isFinished(){
        boolean isSequential = true;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (isLastCell(i, j))
                    break;
                if (isCellValueWrong(i, j))
                    isSequential = false;
            }
        }
        return isSequential;
    }

    private boolean isLastCell(int i, int j) {
        return j == this.size -1 && i == this.size - 1;
    }

    private boolean isCellValueWrong(int i, int j) {
        return Integer.valueOf(this.grid[i][j]) != i*this.size + j + 1;
    }
}
