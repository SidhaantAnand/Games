import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sandeep on 7/24/2017.
 */
public class TicTacToe extends JFrame implements ActionListener {

    public static final int BOARD_SIZE = 3;
    public static boolean CrossTurn = false;
    public JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE]; // stores the objects of JButton and thus is able access each  button and its test


    private enum GameStatus {
        Xwins, Zwins, TIE, Incomplete; // enum stores 0-3 in Xwins - Incomplete. (Thus they store integer values. Access them by <enum-name>.variable name
    }

    public TicTacToe() {
        super.setTitle("TIC TAC TOE");
        super.setResizable(false);
        super.setSize(600, 600); // sets pixels for JFrame window

        // To make a grid/table

        GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
        super.setLayout(layout);

        // To set font

        Font font = new Font("Comic Sans", 1, 125); // type, bold/italic, size
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton(""); // can take text,icon, or action in parameters

                button.setFont(font);


                button.addActionListener(this); // imagine that this adds the ability for the button object at (i,j = row,col) to tske action

                buttons[row][col] = button;

                super.add(button); //adds button to the grid

            }
        }


        super.setVisible(true); // has to be the last line in the constructor
    }

    @Override
    public void actionPerformed(ActionEvent e) { // When we will press the button it will automatically be called


        JButton button = (JButton) e.getSource();//
        makeMove(button);

        GameStatus val = checkGameStatus();

        if (val != GameStatus.Incomplete)
            declareWinner(val);
    }

    private void makeMove(JButton button) {
        if (button.getText().length() > 0) {
            JOptionPane.showMessageDialog(this, "invalid");
            return;
        }
        if (CrossTurn == true)
            button.setText("X");
        if (CrossTurn == false)
            button.setText("O");

        CrossTurn = !CrossTurn;
    }

    private GameStatus checkGameStatus() {
        String text1 = "";
        String text2 = "";

        // checks left diagonal only
        int row = 0;
        int col = 0;

        while (row < BOARD_SIZE - 1) {
            text1 = buttons[row][col].getText();
            text2 = buttons[row + 1][col + 1].getText();

            if (text1.equals(text2) == false || text1.length() == 0)
                break;

            row++;
            col++;
        }

        if (row == BOARD_SIZE - 1) {
            if (text1.equals("X") == true)
                return GameStatus.Xwins;
            else if (text1.equals("O") == true)
                return GameStatus.Zwins;
        }

        // checks row wise

        row = 0;
        col = 0;
        text1 = "";
        text2 = "";
        boolean flag = false;

        for (; row < BOARD_SIZE; row++) {
            for (col = 0; col < BOARD_SIZE - 1; col++) {
                text1 = buttons[row][col].getText();
                text2 = buttons[row][col + 1].getText();

                if (!text1.equals(text2) || text1.length() == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                if (text1.equals("X"))
                    return GameStatus.Xwins;

                else if (text1.equals("O"))
                    return GameStatus.Zwins;
            }

            flag = false;
        }

        // checks column wise

        row = 0;
        col = 0;
        text1 = "";
        text2 = "";
        flag = false;

        for (; col < BOARD_SIZE; col++) {
            for (row = 0; row < BOARD_SIZE - 1; row++) {
                text1 = buttons[row][col].getText();
                text2 = buttons[row + 1][col].getText();

                if (!text1.equals(text2) || text1.length() == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                if (text1.equals("X"))
                    return GameStatus.Xwins;

                else if (text1.equals("O"))
                    return GameStatus.Zwins;
            }

            flag = false;
        }

        // checks right diagonal

        row = 0;
        col = BOARD_SIZE - 1;

        while (row != BOARD_SIZE - 1) {
            text1 = buttons[row][col].getText();
            text2 = buttons[row + 1][col - 1].getText();

            if (text1.equals(text2) == false || text1.length() == 0)
                break;

            row++;
            col--;
        }

        if (row == BOARD_SIZE - 1) {
            if (text1.equals("X") == true)
                return GameStatus.Xwins;
            else if (text1.equals("O") == true)
                return GameStatus.Zwins;

        }


        // checking if empty

        col = 0;
        row = 0;

        for (; row < BOARD_SIZE; row++) {
            for (col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().length() == 0)
                    return GameStatus.Incomplete;

            }
        }

        return GameStatus.TIE;
    }

    private void declareWinner(GameStatus value) {
        if (value == GameStatus.Xwins)
            JOptionPane.showMessageDialog(this, "X wins");

        else if (value == GameStatus.Zwins)
            JOptionPane.showMessageDialog(this, "Z wins");

        else if (value == GameStatus.TIE)
            JOptionPane.showMessageDialog(this, "Its a tie");

       int choice = JOptionPane.showConfirmDialog(this, "Restart the game?");

       if(choice == JOptionPane.YES_OPTION) // YES option is 0
       {
           for(int i = 0; i<BOARD_SIZE; i++)
           {
               for(int j = 0; j<BOARD_SIZE; j++)
               {
                   buttons[i][j].setText("");
               }
           }
       }

       if(choice == JOptionPane.NO_OPTION)
           super.dispose();

    }


}
