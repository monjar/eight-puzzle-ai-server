import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            ServerSocket serverSocket = new ServerSocket(8963);
            Socket client = serverSocket.accept();
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String size = dis.readUTF();
            Board board = new Board(Integer.valueOf(size));
            board.printBoard();
            while (!board.isFinished()){
                dos.writeUTF(board.toString());
                String command = dis.readUTF().toUpperCase();
                Directions dir = Directions.valueOf(command);
                try {
                    board.makeMove(dir);
                } catch (WrongMoveException e) {

                    System.out.println(e.getMessage());
                    break;
                }
            }
            dos.writeUTF("Finished!");
            dos.writeUTF(board.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
//        while (!board.isFinished()){
//            System.out.println("DSDS");
//            String command = in.nextLine().toUpperCase();
//            board.makeMove(Directions.valueOf(command));
//            board.printBoard();
//        }

    }
}
