package controller;

import model.Player;
import view.ScoreWindow;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreController {

    private boolean isWrite;
    private ArrayList<Player> playersScore = new ArrayList<Player>();
    private Player winner;
    private ScoreWindow scoreWindow;

    public ScoreController (ScoreWindow scoreWindow){
        super();
        this.scoreWindow = scoreWindow;
        playersScore.clear();
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream("scores.txt"));
            playersScore = (ArrayList<Player>) reader.readObject();
            reader.close();
            playersScore.sort(Comparator.comparingLong(Player::getScore));
            Collections.reverse(playersScore);
            scoreWindow.getTableModel().setRowCount(0);
            for (int i = 0; i < playersScore.size(); i++) {
                Object dataTemp[] = {playersScore.get(i).getNick(), playersScore.get(i).getScore()};
                scoreWindow.getTableModel().addRow(dataTemp);
            }
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public ScoreController (boolean isWrite, Player winner) {
        super();
        // Import.
        playersScore.clear();
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream("scores.txt"));
            playersScore = (ArrayList<Player>) reader.readObject();
            reader.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        // Export.
        playersScore.add(winner);
        this.isWrite = isWrite;
        this.winner = winner;
        if (isWrite) {
            try {
                ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("scores.txt"));
                writer.writeObject(playersScore);
                writer.close();
            } catch (FileNotFoundException except) {
                JOptionPane.showMessageDialog(null, except.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
            } catch (IOException except) {
                JOptionPane.showMessageDialog(null, except.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
