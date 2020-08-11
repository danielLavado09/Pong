package controller;

import view.GameFrame;
import view.MainWindow;
import view.GameWindow;

public class Controller {

    GameWindow gameWindow;
    MainWindow mainWindow;
    GameController gameController;
    GameFrame gameFrame;

    public Controller () {
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
        mainWindow.setVisible(false);
        gameWindow.setVisible(true);
        gameController = new GameController(gameFrame);
    }

    private void scoreGame() {

    }

    private void helpGame() {

    }

    private void exitGame() {
        mainWindow.dispose();
    }

}