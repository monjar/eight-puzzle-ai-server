package Server;

import Game.Board;
import Game.Directions;
import Game.WrongMoveException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Server is running, waiting for client to connect...");
            Socket client = connectSocket();
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());
            System.out.println("Client Connected...");
            if (args.length != 0) {
                if (args[0].equals("rand"))
                    runGame(dos, dis, args[0], args[1]);
                else
                    runGame(dos, dis, args[0]);
            } else
                runGame(dos, dis, "rand","100");
        } catch (IOException e) {
            System.out.println("Client Disconnected!");
        }
    }

    private static void runGame(DataOutputStream dos, DataInputStream dis, String... options) throws IOException {
        Board board = InitBoard(dis, options);
        while (!board.isFinished())
            doTurn(dos, dis, board);
        sendFinishedMessage(dos, board);
    }

    private static void sendFinishedMessage(DataOutputStream dos, Board board) throws IOException {
        dos.writeUTF("Finished!");
        sendBoardToClient(dos, board);
    }

    private static Board InitBoard(DataInputStream dis, String... options) throws IOException {
        String size = dis.readUTF();
        return new Board(Integer.valueOf(size), options);
    }

    private static void doTurn(DataOutputStream dos, DataInputStream dis, Board board) throws IOException {
        sendBoardToClient(dos, board);
        Directions dir = getDirection(dis);
        tryMakingMove(board, dir);
    }

    private static Directions getDirection(DataInputStream dis) throws IOException {
        String command = dis.readUTF().toUpperCase();
        return Directions.valueOf(command);
    }

    private static void tryMakingMove(Board board, Directions dir) {
        try {
            System.out.println("Client move: " + dir);
            board.makeMove(dir);
        } catch (WrongMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sendBoardToClient(DataOutputStream dos, Board board) throws IOException {
        dos.writeUTF(board.toString());
    }

    private static Socket connectSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8963);
        return serverSocket.accept();
    }
}
