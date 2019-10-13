package Game;

public class WrongMoveException extends ArrayIndexOutOfBoundsException {
    WrongMoveException(){
        super("Wrong Move!");
    }
}
