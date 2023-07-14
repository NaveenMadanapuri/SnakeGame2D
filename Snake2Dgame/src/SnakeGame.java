// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import javax.swing.*;
public class SnakeGame extends JFrame{
    Board board;
    SnakeGame(){
        board=new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {

        SnakeGame snakegame=new SnakeGame();
        }
    }
