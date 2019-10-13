import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(3);
        board.printBoard();
        Scanner in = new Scanner(System.in);
        while (!board.isFinished()){
//            System.out.println("DSDS");
            String command = in.nextLine().toUpperCase();
            board.makeMove(Directions.valueOf(command));
            board.printBoard();
        }

    }
}
