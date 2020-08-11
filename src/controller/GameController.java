package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.KeyStroke;

import model.sprites.*;
import view.GameFrame;

public class GameController {
    //Declaraciones
    GameFrame gameFrame;
    Actions actions;
    Timer timer = new Timer();
    double random = Math.round(Math.random());
    int randomDirection;
    private final int movimiento = 5;

    //Constructor
    public GameController(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        actions = new Actions();
        this.gameFrame.setNet(new SNet((this.gameFrame.getWidth() - 5) / 2, 0, this.gameFrame.getHeight(), 5, Color.white));
        this.gameFrame.setBall(new SBall((this.gameFrame.getWidth() - 15) / 2, (this.gameFrame.getHeight() - 15) / 2, 15, 15, Color.red));
        this.gameFrame.setPlayerOne(new SPlayer(this.gameFrame.getWidth() - (20), (this.gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        this.gameFrame.setPlayerTwo(new SPlayer(10, (this.gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        // Prueba de dirección random.
        if (random == 0) {
            randomDirection = -1;
        } else {
            randomDirection = 1;
        }

        //Asignar teclas a actions
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "presionadoArribaJ1", actions.presionadoArribaJ1);    //false = Presionado
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "presionadoArribaJ2", actions.presionadoArribaJ2);
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "liberadoArribaJ1", actions.liberadoArribaJ1);    //true = Liberado
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "liberadooArribaJ2", actions.liberadoArribaJ2);

        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "presionadoAbajoJ1", actions.presionadoAbajoJ1);
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "presionadoAbajoJ2", actions.presionadoAbajoJ2);
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "liberadoAbajoJ1", actions.liberadoAbajoJ1);
        this.gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "liberadoAbajoJ2", actions.liberadoAbajoJ2);

        //Actualizar posicion de los jugadores, si la tecla correspondiente es presionada
        TimerTask posicionJugadores = new TimerTask() {
            @Override
            public void run() {
                if (actions.isArribaJ1()) {
                    if (GameController.this.gameFrame.getPlayerOne().getY() >= movimiento) {
                        GameController.this.gameFrame.getPlayerOne().setY(GameController.this.gameFrame.getPlayerOne().getY() - movimiento);
                    }
                } else if (actions.isAbajoJ1()) {
                    if (GameController.this.gameFrame.getPlayerOne().getY() <= GameController.this.gameFrame.getHeight() - 75) {
                        GameController.this.gameFrame.getPlayerOne().setY((GameController.this.gameFrame.getPlayerOne().getY()) + movimiento);
                    }
                }

                if (actions.isArribaJ2()) {
                    if (GameController.this.gameFrame.getPlayerTwo().getY() >= movimiento) {
                        GameController.this.gameFrame.getPlayerTwo().setY((GameController.this.gameFrame.getPlayerTwo().getY()) - movimiento);
                    }
                } else if (actions.isAbajoJ2()) {
                    if (GameController.this.gameFrame.getPlayerTwo().getY() <= GameController.this.gameFrame.getHeight() - 75) {
                        GameController.this.gameFrame.getPlayerTwo().setY((GameController.this.gameFrame.getPlayerTwo().getY()) + movimiento);
                    }
                }
            }
        };

        //Timer
        timer.schedule(posicionJugadores, 0, 10);    //Cada 10 milisegundos llama a posicionJugadores

        // Moviendo la pelota.
        TimerTask moveBall = new TimerTask() {
            @Override
            public void run() {
                // HitBox.
                Rectangle boundsBall = new Rectangle(
                        (int) (GameController.this.gameFrame.getBall().getX() + GameController.this.gameFrame.getBall().getDx() * GameController.this.gameFrame.getBall().getSpeed()),
                        (int) (GameController.this.gameFrame.getBall().getY() + GameController.this.gameFrame.getBall().getDy() * GameController.this.gameFrame.getBall().getSpeed()),
                        (int) GameController.this.gameFrame.getBall().getWidth(), (int) GameController.this.gameFrame.getBall().getHeight());
                Rectangle boundsPlayer = new Rectangle((int) GameController.this.gameFrame.getPlayerOne().getX(),
                        (int) GameController.this.gameFrame.getPlayerOne().getY(), (int) GameController.this.gameFrame.getPlayerOne().getWidth(),
                        (int) GameController.this.gameFrame.getPlayerOne().getHeight());
                Rectangle boundsPlayerTwo = new Rectangle((int) GameController.this.gameFrame.getPlayerTwo().getX(),
                        (int) GameController.this.gameFrame.getPlayerTwo().getY(), (int) GameController.this.gameFrame.getPlayerTwo().getWidth(),
                        (int) GameController.this.gameFrame.getPlayerTwo().getHeight());
                // Colisi�n con las raquetas.
                if (boundsBall.intersects(boundsPlayer) || boundsBall.intersects(boundsPlayerTwo)) {
                    GameController.this.gameFrame.getBall().setDx(GameController.this.gameFrame.getBall().getDx() * -1);
                }
                // Colisi�n con los lados superior e inferior.
                if (GameController.this.gameFrame.getBall().getY() + GameController.this.gameFrame.getBall().getDy() * GameController.this.gameFrame.getBall().getSpeed() < 0) {
                    GameController.this.gameFrame.getBall().setDy(GameController.this.gameFrame.getBall().getDy() * -1);
                } else if (GameController.this.gameFrame.getBall().getY() + GameController.this.gameFrame.getBall().getDy() * GameController.this.gameFrame.getBall().getSpeed()
                        + GameController.this.gameFrame.getBall().getHeight() >= GameController.this.gameFrame.getHeight()) {
                    GameController.this.gameFrame.getBall().setDy(GameController.this.gameFrame.getBall().getDy() * -1);
                }
                // Test de puntaje.
                if (GameController.this.gameFrame.getBall().getX() < 0) {
                    System.out.println("Punto");
                    GameController.this.gameFrame.getBall().setX(200);
                }
                // Se lanza la pelota.
                GameController.this.gameFrame.getBall().setX(GameController.this.gameFrame.getBall().getX() + GameController.this.gameFrame.getBall().getDx() * GameController.this.gameFrame.getBall().getSpeed());
                GameController.this.gameFrame.getBall().setY(GameController.this.gameFrame.getBall().getY() + GameController.this.gameFrame.getBall().getDy() * GameController.this.gameFrame.getBall().getSpeed());
            }
        };
        // Task, ms Inicio, ms Refresh.
        timer.schedule(moveBall, 0, 10);
    }
}