package controller;

import model.Player;
import view.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller {

    GameWindow gameWindow;
    MainWindow mainWindow;
    HelpWindow helpWindow;
    ScoreWindow scoreWindow;
    GameController gameController;
    GameFrame gameFrame;
    Player playerOne;
    Player playerTwo;

    public Controller() {
        mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        mainWindow.getBtnStart().addActionListener(e -> startGame());
        mainWindow.getBtnTopScore().addActionListener(e -> scoreGame());
        mainWindow.getBtnHelp().addActionListener(e -> helpGame());
        mainWindow.getBtnExit().addActionListener(e -> exitGame());
    }

    private void startGame() {
        gameFrame = new GameFrame();
        gameWindow = new GameWindow(gameFrame);
        String regexName = ("^[A-Z]{3}$");
        String nickPlayerOne = null;
        String nickPlayerTwo = null;
        do {
            // Nickname: PlayerOne.
            try {
                do {
                    nickPlayerOne = JOptionPane.showInputDialog(null, "Jugador Uno.\nIngrese su Nickname (Máximo 3 caracteres, en mayúsculas)");
                } while (!nickPlayerOne.matches(regexName));
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null, "Datos incorrectos!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Nickname: PlayerTwo.
            try {
                do {
                    nickPlayerTwo = JOptionPane.showInputDialog(null, "Jugador Dos.\nIngrese su Nickname (Máximo 3 caracteres, en mayúsculas)");
                } while (!nickPlayerTwo.matches(regexName));
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null, "Datos incorrectos!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }while(nickPlayerOne == nickPlayerTwo || nickPlayerOne == null || nickPlayerTwo == null);

        playerOne = new Player(nickPlayerOne);
        playerTwo = new Player(nickPlayerTwo);

        mainWindow.setVisible(false);
        gameWindow.setVisible(true);
        gameController = new GameController(gameFrame, gameWindow, playerOne, playerTwo);
        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.out.println("Se cerro.");
                gameController.getTimer().purge();
                gameController.getTimer().cancel();
                System.out.println(gameController.getWinner().getNick());
                new ScoreController(true, gameController.getWinner());
                mainWindow.setVisible(true);
            }
        });
    }

    private void scoreGame() {
        scoreWindow = new ScoreWindow();
        new ScoreController(scoreWindow);
        scoreWindow.setVisible(true);
    }

    private void helpGame() {
        helpWindow = new HelpWindow();
        helpWindow.setVisible(true);
    }

    private void exitGame() {
        mainWindow.dispose();
        System.exit(0);
    }

}