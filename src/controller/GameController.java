package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.KeyStroke;

import model.Player;
import model.sprites.*;
import view.GameFrame;
import view.GameWindow;

public class GameController {
    GameFrame gameFrame;
    Actions actions;
    private Timer timer = new Timer();
    private TimerTask moveBall;
    private TimerTask manejoPoderes;
    private double random = Math.round(Math.random());
    private final int reduccionVelocidad = 3;
    private int jugadorActivo;
    private int poderAleatorio;
    private int xAleatorio;
    private int yAleatorio;
    private boolean pointOne = false;
    private boolean pointTwo = false;
    private boolean pelotaCongelada;
    private long delayPosicionamientoPoderes;
    private long delayOcultarPoderes;
    private long delayDebuffJugador1;
    private long delayDebuffJugador2;
    private long delayCongelarPelota;
    private boolean activoPosicionamientoPoderes = true;
    private boolean activoOcultarPoderes;
    private boolean activoActivarBuffJugador1;
    private boolean activoActivarBuffJugador2;
    private boolean activoQuitarBuffJugador1;
    private boolean activoQuitarBuffJugador2;
    private boolean activoDebuffJugador1;
    private boolean activoDebuffJugador2;
    private boolean activoCongelarPelota;
    private Player playerOne;
    private Player playerTwo;
    private GameWindow gameWindow;

    //Constructor
    public GameController(GameFrame gameFrame, GameWindow gameWindow, Player playerOne, Player playerTwo) {
        this.gameWindow = gameWindow;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameFrame = gameFrame;

        Actions actions = new Actions();
        pelotaCongelada = false;
        //Delay en milisegundos
        delayCongelarPelota = 3000;
        delayDebuffJugador1 = 5000;
        delayDebuffJugador2 = 5000;
        delayOcultarPoderes = 10000;
        delayPosicionamientoPoderes = 5000;

        // ActionListener: GameWindow.
        gameWindow.getBtnExit().addActionListener(e -> exitGame());

        // JLabels: GameWindow.
        gameWindow.getLblPlayerOneScore().setText(playerOne.getNick() + ": " + playerOne.getScore());
        gameWindow.getLblPlayerTwoScore().setText(playerTwo.getNick() + ": " + playerTwo.getScore());

        // Sprites.
        gameFrame.setNet(new SNet((gameFrame.getWidth() - 5) / 2, 0, gameFrame.getHeight(), 5, Color.white));
        gameFrame.setBall(new SBall((gameFrame.getWidth() - 15) / 2, (gameFrame.getHeight() - 15) / 2, 15, 15, Color.red));
        gameFrame.setPlayerTwo(new SPlayer(gameFrame.getWidth() - (20), (gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        gameFrame.setPlayerOne(new SPlayer(10, (gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
        gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));

        //Asignar teclas a actions
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "presionadoArribaJ1", actions.presionadoArribaJ1);    //false = Presionado
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "presionadoArribaJ2", actions.presionadoArribaJ2);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "liberadoArribaJ1", actions.liberadoArribaJ1);    //true = Liberado
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "liberadooArribaJ2", actions.liberadoArribaJ2);

        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "presionadoAbajoJ1", actions.presionadoAbajoJ1);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "presionadoAbajoJ2", actions.presionadoAbajoJ2);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "liberadoAbajoJ1", actions.liberadoAbajoJ1);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "liberadoAbajoJ2", actions.liberadoAbajoJ2);

        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), "pausa", actions.pausarJuego);

        //Actualizar posicion de los jugadores, si la tecla correspondiente es presionada
        TimerTask posicionJugadores = new TimerTask() {
            @Override
            public void run() {
                if (!actions.isPausado()) {
                    if (actions.isArribaJ1()) {
                        if (gameFrame.getPlayerOne().getY() >= gameFrame.getPlayerOne().getSpeed()) {
                            gameFrame.getPlayerOne().setY(gameFrame.getPlayerOne().getY() - gameFrame.getPlayerOne().getSpeed());
                        }
                    } else if (actions.isAbajoJ1()) {
                        if (gameFrame.getPlayerOne().getY() <= gameFrame.getHeight() - 75) {
                            gameFrame.getPlayerOne().setY((gameFrame.getPlayerOne().getY()) + gameFrame.getPlayerOne().getSpeed());
                        }
                    }

                    if (actions.isArribaJ2()) {
                        if (gameFrame.getPlayerTwo().getY() >= gameFrame.getPlayerTwo().getSpeed()) {
                            gameFrame.getPlayerTwo().setY((gameFrame.getPlayerTwo().getY()) - gameFrame.getPlayerTwo().getSpeed());
                        }
                    } else if (actions.isAbajoJ2()) {
                        if (gameFrame.getPlayerTwo().getY() <= gameFrame.getHeight() - 75) {
                            gameFrame.getPlayerTwo().setY((gameFrame.getPlayerTwo().getY()) + gameFrame.getPlayerTwo().getSpeed());
                        }
                    }
                }
            }
        };

        //Timer
        timer.schedule(posicionJugadores, 0, 10);    //Cada 10 milisegundos llama a posicionJugadores

        // Moviendo la pelota.
        moveBall = new TimerTask() {
            @Override
            public void run() {
                if (!actions.isPausado()) {
                    if (!pelotaCongelada) {
                        // Hit-box: Ball.
                        Rectangle boundsBall = new Rectangle(
                                (int) (gameFrame.getBall().getX() + gameFrame.getBall().getDx() * gameFrame.getBall().getSpeed()),
                                (int) (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed()),
                                (int) gameFrame.getBall().getWidth(), (int) gameFrame.getBall().getHeight());
                        // Hit-box: Player One.
                        Rectangle boundsPlayerOne = new Rectangle((int) gameFrame.getPlayerOne().getX(),
                                (int) gameFrame.getPlayerOne().getY(), (int) gameFrame.getPlayerOne().getWidth(),
                                (int) gameFrame.getPlayerOne().getHeight());
                        // Hit-box: Player Two.
                        Rectangle boundsPlayerTwo = new Rectangle((int) gameFrame.getPlayerTwo().getX(),
                                (int) gameFrame.getPlayerTwo().getY(), (int) gameFrame.getPlayerTwo().getWidth(),
                                (int) gameFrame.getPlayerTwo().getHeight());
                        Rectangle boundsBuff = new Rectangle((int) gameFrame.getBuff().getX(),
                                (int) gameFrame.getBuff().getY(), (int) gameFrame.getBuff().getWidth(),
                                (int) gameFrame.getBuff().getHeight());
                        Rectangle boundsDebuff = new Rectangle((int) gameFrame.getDebuff().getX(),
                                (int) gameFrame.getDebuff().getY(), (int) gameFrame.getDebuff().getWidth(),
                                (int) gameFrame.getDebuff().getHeight());
                        // Colision con las raquetas.
                        if (boundsBall.intersects(boundsPlayerOne) || boundsBall.intersects(boundsPlayerTwo)) {
                            if (boundsBall.intersects(boundsPlayerOne)) {
                                jugadorActivo = 1;
                            } else if (boundsBall.intersects(boundsPlayerTwo)) {
                                jugadorActivo = 2;
                            }

                            gameFrame.getBall().setDx(gameFrame.getBall().getDx() * -1);
                        }
                        // Colision con los lados superior e inferior.
                        if (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed() < 0) {
                            gameFrame.getBall().setDy(gameFrame.getBall().getDy() * -1);
                        } else if (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed()
                                + gameFrame.getBall().getHeight() >= gameFrame.getHeight()) {
                            gameFrame.getBall().setDy(gameFrame.getBall().getDy() * -1);
                        }
                        // Colision con los poderes
                        if (boundsBall.intersects(boundsBuff)) {
                            gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
                            if (jugadorActivo == 1) {    //Implementar aumento de velocidad de la pelota
                                gameFrame.getPlayerOne().setColor(Color.YELLOW);
                                jugadorActivo = 0;

                                activoActivarBuffJugador1 = true;
                            } else if (jugadorActivo == 2) {
                                gameFrame.getPlayerTwo().setColor(Color.YELLOW);
                                jugadorActivo = 0;

                                activoActivarBuffJugador2 = true;
                            }
                        } else if (boundsBall.intersects(boundsDebuff)) {
                            gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));
                            if (jugadorActivo == 1) {
                                gameFrame.getPlayerOne().setColor(Color.BLUE);
                                gameFrame.getPlayerOne().setSpeed(gameFrame.getPlayerOne().getSpeed() - reduccionVelocidad);

                                activoDebuffJugador1 = true;
                            } else if (jugadorActivo == 2) {
                                gameFrame.getPlayerTwo().setColor(Color.BLUE);
                                gameFrame.getPlayerTwo().setSpeed(gameFrame.getPlayerTwo().getSpeed() - reduccionVelocidad);

                                activoDebuffJugador2 = true;
                            }
                        }
                    }

                    // Test de puntaje.
                    if (gameFrame.getBall().getX() < 0) {
                        System.out.println("Punto 2");
                        playerTwo.setScore(playerTwo.getScore() + 1);
                        gameWindow.getLblPlayerTwoScore().setText(playerTwo.getNick() + ": " + playerTwo.getScore());

                        //jugadorActivo = 1;
                        gameFrame.getBall().setX((gameFrame.getWidth() / 2) - (gameFrame.getBall().getWidth() / 2));
                        gameFrame.getBall().setY((gameFrame.getHeight() / 2) - (gameFrame.getBall().getHeight() / 2));
                        gameFrame.getBall().setDx(gameFrame.getBall().getDx() * -1);
                        gameFrame.getBall().setDy(gameFrame.getBall().getDx() * -1);
                        gameFrame.getBall().setSpeed(0);
                        jugadorActivo = 0;
                        pelotaCongelada = true;

                        if (gameFrame.getPlayerOne().getColor() != Color.WHITE || gameFrame.getPlayerTwo().getColor() != Color.WHITE
                                || gameFrame.getBall().getColor() != Color.RED) {

                            if (gameFrame.getPlayerOne().getColor() == Color.YELLOW) {
                                gameFrame.getPlayerOne().setColor(Color.WHITE);

                                activoActivarBuffJugador1 = false;
                            }

                            if (gameFrame.getPlayerTwo().getColor() == Color.YELLOW) {
                                gameFrame.getPlayerTwo().setColor(Color.WHITE);

                                activoActivarBuffJugador2 = false;
                            }

                            if (gameFrame.getPlayerOne().getColor() == Color.BLUE) {
                                gameFrame.getPlayerOne().setColor(Color.WHITE);

                                activoDebuffJugador1 = false;
                                delayDebuffJugador1 = 5000;
                                gameFrame.getPlayerOne().setSpeed(5);
                            }

                            if (gameFrame.getPlayerTwo().getColor() == Color.BLUE) {
                                gameFrame.getPlayerTwo().setColor(Color.WHITE);

                                activoDebuffJugador2 = false;
                                delayDebuffJugador2 = 5000;
                                gameFrame.getPlayerTwo().setSpeed(5);
                            }

                            if (gameFrame.getBall().getColor() == Color.YELLOW) {
                                gameFrame.getBall().setColor(Color.RED);
                                activoQuitarBuffJugador2 = false;
                            }
                        }

                        activoCongelarPelota = true;
                        //pointOne = true;
                    } else if (gameFrame.getBall().getX() > gameFrame.getWidth() - gameFrame.getBall().getWidth()) {
                        //jugadorActivo = 2;
                        System.out.println("Punto 1");
                        playerOne.setScore(playerOne.getScore() + 1);
                        gameWindow.getLblPlayerOneScore().setText(playerOne.getNick() + ": " + playerOne.getScore());

                        gameFrame.getBall().setX((gameFrame.getWidth() / 2) - (gameFrame.getBall().getWidth() / 2));
                        gameFrame.getBall().setY(gameFrame.getHeight() / 2);
                        gameFrame.getBall().setDx(gameFrame.getBall().getDx() * -1);
                        gameFrame.getBall().setDy(gameFrame.getBall().getDx() * -1);
                        gameFrame.getBall().setSpeed(0);
                        jugadorActivo = 0;
                        pelotaCongelada = true;

                        if (gameFrame.getPlayerOne().getColor() != Color.WHITE || gameFrame.getPlayerTwo().getColor() != Color.WHITE
                                || gameFrame.getBall().getColor() != Color.RED) {

                            if (gameFrame.getPlayerOne().getColor() == Color.YELLOW) {
                                gameFrame.getPlayerOne().setColor(Color.WHITE);

                                activoActivarBuffJugador1 = false;
                            }

                            if (gameFrame.getPlayerTwo().getColor() == Color.YELLOW) {
                                gameFrame.getPlayerTwo().setColor(Color.WHITE);

                                activoActivarBuffJugador2 = false;
                            }

                            if (gameFrame.getPlayerOne().getColor() == Color.BLUE) {
                                gameFrame.getPlayerOne().setColor(Color.WHITE);

                                activoDebuffJugador1 = false;
                                delayDebuffJugador1 = 5000;
                                gameFrame.getPlayerOne().setSpeed(5);
                            }

                            if (gameFrame.getPlayerTwo().getColor() == Color.BLUE) {
                                gameFrame.getPlayerTwo().setColor(Color.WHITE);

                                activoDebuffJugador2 = false;
                                delayDebuffJugador2 = 5000;
                                gameFrame.getPlayerTwo().setSpeed(5);
                            }

                            if (gameFrame.getBall().getColor() == Color.YELLOW) {
                                gameFrame.getBall().setColor(Color.RED);
                                activoQuitarBuffJugador1 = false;
                            }
                        }

                        activoCongelarPelota = true;
                        //pointTwo = true;
                    }
                    // Se lanza la pelota.
                    gameFrame.getBall().setX(gameFrame.getBall().getX() + gameFrame.getBall().getDx() * gameFrame.getBall().getSpeed());
                    gameFrame.getBall().setY(gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed());
                }
            }
        };
        // Task, ms Inicio, ms Refresh.
        timer.schedule(moveBall, 0, 10);

        //Manejo de poderes y congelar pelota al iniciar turno
        manejoPoderes = new TimerTask() {
            @Override
            public void run() {
                if (!actions.isPausado()) {
                    //Reducir delay cuando la accion este activa
                    if (activoCongelarPelota) {
                        delayCongelarPelota -= 10;
                    }
                    if (activoDebuffJugador1) {
                        delayDebuffJugador1 -= 10;
                    }
                    if (activoDebuffJugador2) {
                        delayDebuffJugador2 -= 10;
                    }
                    if (activoOcultarPoderes) {
                        delayOcultarPoderes -= 10;
                    }
                    if (activoPosicionamientoPoderes) {
                        delayPosicionamientoPoderes -= 10;
                    }

                    //Los buff no tiene delay, si estan activos se verifican cada 10 milisegundos
                    if (activoActivarBuffJugador1) {
                        if (jugadorActivo == 1) {
                            gameFrame.getPlayerOne().setColor(Color.WHITE);
                            gameFrame.getBall().setColor(Color.YELLOW);

                            gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() * 3);
                            activoActivarBuffJugador1 = false;
                            activoQuitarBuffJugador1 = true;
                        }
                    }

                    if (activoQuitarBuffJugador1) {
                        if (jugadorActivo == 2) {
                            gameFrame.getBall().setColor(Color.RED);

                            gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() / 3);
                            activoQuitarBuffJugador1 = false;
                        }
                    }

                    if (activoActivarBuffJugador2) {
                        if (jugadorActivo == 2) {
                            gameFrame.getPlayerTwo().setColor(Color.WHITE);
                            gameFrame.getBall().setColor(Color.YELLOW);

                            gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() * 3);
                            activoActivarBuffJugador2 = false;
                            activoQuitarBuffJugador2 = true;
                        }
                    }

                    if (activoQuitarBuffJugador2) {
                        if (jugadorActivo == 1) {
                            gameFrame.getBall().setColor(Color.RED);

                            gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() / 3);
                            activoQuitarBuffJugador2 = false;
                        }
                    }

                    //Desactivar accion cuando el delay se halla completado
                    if (delayCongelarPelota <= 0) {
                        activoCongelarPelota = false;
                    }
                    if (delayDebuffJugador1 <= 0) {
                        activoDebuffJugador1 = false;
                    }
                    if (delayDebuffJugador2 <= 0) {
                        activoDebuffJugador2 = false;
                    }
                    if (delayOcultarPoderes <= 0) {
                        activoOcultarPoderes = false;
                    }
                    if (delayPosicionamientoPoderes <= 0) {
                        activoPosicionamientoPoderes = false;
                    }

                    //Aplicar accion cuando el delay se halla completado
                    if (delayCongelarPelota <= 0) {
                        pelotaCongelada = false;

                        gameFrame.getBall().setSpeed(5);
                        activoCongelarPelota = false;

                        delayCongelarPelota = 3000;
                    }

                    if (delayDebuffJugador1 <= 0) {
                        gameFrame.getPlayerOne().setColor(Color.WHITE);
                        gameFrame.getPlayerOne().setSpeed(gameFrame.getPlayerOne().getSpeed() + reduccionVelocidad);
                        activoDebuffJugador1 = false;

                        delayDebuffJugador1 = 5000;
                    }

                    if (delayDebuffJugador2 <= 0) {
                        gameFrame.getPlayerTwo().setColor(Color.WHITE);
                        gameFrame.getPlayerTwo().setSpeed(gameFrame.getPlayerTwo().getSpeed() + reduccionVelocidad);
                        activoDebuffJugador2 = false;

                        delayDebuffJugador2 = 5000;
                    }

                    if (delayOcultarPoderes <= 0) {
                        if (poderAleatorio == 1) {
                            gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
                        } else {
                            gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));
                        }
                        activoOcultarPoderes = false;
                        activoPosicionamientoPoderes = true;

                        delayOcultarPoderes = 10000;
                    }

                    if (delayPosicionamientoPoderes <= 0) {
                        poderAleatorio = (int) (Math.random() * 2) + 1;
                        xAleatorio = ThreadLocalRandom.current().nextInt(gameFrame.getX() + 70, gameFrame.getWidth() - 80 + 1);
                        yAleatorio = ThreadLocalRandom.current().nextInt(gameFrame.getY() + 50, gameFrame.getHeight() - 50 + 1);
                        System.out.println(xAleatorio + " " + yAleatorio);
                        if (poderAleatorio == 1) {
                            gameFrame.setBuff(new SPower(xAleatorio, yAleatorio, 20, 20, Color.YELLOW));
                        } else {
                            gameFrame.setDebuff(new SPower(xAleatorio, yAleatorio, 20, 20, Color.BLUE));
                        }
                        activoPosicionamientoPoderes = false;
                        activoOcultarPoderes = true;

                        delayPosicionamientoPoderes = 5000;
                    }
                }
            }
        };
        timer.schedule(manejoPoderes, 0, 10);
    }

    private void exitGame() {
        timer.cancel();
        timer.purge();
        gameWindow.dispose();
    }

    public Timer getTimer() {
        return timer;
    }

    public Player getWinner() {
        if (playerOne.getScore() > playerTwo.getScore()) {
            return playerOne;
        } else if (playerOne.getScore() == playerTwo.getScore()) {
            if (Math.random() == 1) {
                return playerOne;
            } else {
                return playerTwo;
            }
        } else {
            return playerTwo;
        }
    }

}