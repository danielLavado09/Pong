package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.KeyStroke;

import model.sprites.*;
import view.GameFrame;

public class GameController {

    GameFrame gameFrame;
    Actions actions;
    Timer timer = new Timer();
    private TimerTask loopPoderes;
    private TimerTask posicionamientoPoderes;
    private TimerTask ocultarPoderes;
    private TimerTask activarBuffJugador1;
    private TimerTask quitarBuffJugador1;
    private TimerTask activarBuffJugador2;
    private TimerTask quitarBuffJugador2;
    private TimerTask debuffJugador1;
    private TimerTask debuffJugador2;
    private double random = Math.round(Math.random());
    private int movimientoJ1 = 5;
    private int movimientoJ2 = 5;
    private final int reduccionVelocidad = 3;
    private int jugadorActivo;
    private int randomDirection;
    private int poderAleatorio;
    private int xAleatorio;
    private int yAleatorio;

    //Constructor
    public GameController(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        Actions actions = new Actions();
        // Añadiendo Sprites al GamFrame.
        gameFrame.setNet(new SNet((gameFrame.getWidth() - 5) / 2, 0, gameFrame.getHeight(), 5, Color.white));
        gameFrame.setBall(new SBall((gameFrame.getWidth() - 15) / 2, (gameFrame.getHeight() - 15) / 2, 15, 15, Color.red));
        gameFrame.setPlayerOne(new SPlayer(gameFrame.getWidth() - (20), (gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        gameFrame.setPlayerTwo(new SPlayer(10, (gameFrame.getHeight() - 70) / 2, 70, 10, Color.white));
        gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
        gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));
        // Prueba de dirección random.
        if (random == 0) {
            randomDirection = -1;
        } else {
            randomDirection = 1;
        }

        //Asignar teclas a actions
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "presionadoArribaJ1", actions.presionadoArribaJ1);	//false = Presionado
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "presionadoArribaJ2", actions.presionadoArribaJ2);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "liberadoArribaJ1", actions.liberadoArribaJ1);	//true = Liberado
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "liberadooArribaJ2", actions.liberadoArribaJ2);

        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "presionadoAbajoJ1", actions.presionadoAbajoJ1);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "presionadoAbajoJ2", actions.presionadoAbajoJ2);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "liberadoAbajoJ1", actions.liberadoAbajoJ1);
        gameFrame.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "liberadoAbajoJ2", actions.liberadoAbajoJ2);

        //Actualizar posicion de los jugadores, si la tecla correspondiente es presionada
        TimerTask posicionJugadores = new TimerTask() {
            @Override
            public void run() {
                if(actions.isArribaJ1()) {
                    if (gameFrame.getPlayerOne().getY() >= movimientoJ1) {
                        gameFrame.getPlayerOne().setY(gameFrame.getPlayerOne().getY() - movimientoJ1);
                    }
                } else if(actions.isAbajoJ1()) {
                    if (gameFrame.getPlayerOne().getY() <= gameFrame.getHeight() - 75) {
                        gameFrame.getPlayerOne().setY((gameFrame.getPlayerOne().getY()) + movimientoJ1);
                    }
                }

                if(actions.isArribaJ2()) {
                    if (gameFrame.getPlayerTwo().getY() >= movimientoJ2) {
                        gameFrame.getPlayerTwo().setY((gameFrame.getPlayerTwo().getY()) - movimientoJ2);
                    }
                } else if(actions.isAbajoJ2()) {
                    if (gameFrame.getPlayerTwo().getY() <= gameFrame.getHeight() - 75) {
                        gameFrame.getPlayerTwo().setY((gameFrame.getPlayerTwo().getY()) + movimientoJ2);
                    }
                }
            }
        };

        //Timer
        timer.schedule(posicionJugadores, 0, 10);	//Cada 10 milisegundos llama a posicionJugadores

        // Moviendo la pelota.
        TimerTask moveBall = new TimerTask() {
            @Override
            public void run() {
                // HitBox.
                Rectangle boundsBall = new Rectangle(
                        (int) (gameFrame.getBall().getX() + gameFrame.getBall().getDx() * gameFrame.getBall().getSpeed()),
                        (int) (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed()),
                        (int) gameFrame.getBall().getWidth(), (int) gameFrame.getBall().getHeight());
                Rectangle boundsPlayer = new Rectangle((int) gameFrame.getPlayerOne().getX(),
                        (int) gameFrame.getPlayerOne().getY(), (int) gameFrame.getPlayerOne().getWidth(),
                        (int) gameFrame.getPlayerOne().getHeight());
                Rectangle boundsPlayerTwo = new Rectangle((int) gameFrame.getPlayerTwo().getX(),
                        (int) gameFrame.getPlayerTwo().getY(), (int) gameFrame.getPlayerTwo().getWidth(),
                        (int) gameFrame.getPlayerTwo().getHeight());
                Rectangle boundsBuff = new Rectangle((int) gameFrame.getBuff().getX(),
                        (int) gameFrame.getBuff().getY(), (int) gameFrame.getBuff().getWidth(),
                        (int) gameFrame.getBuff().getHeight());
                Rectangle boundsDebuff = new Rectangle((int) gameFrame.getDebuff().getX(),
                        (int) gameFrame.getDebuff().getY(), (int) gameFrame.getDebuff().getWidth(),
                        (int) gameFrame.getDebuff().getHeight());
                // Colisión con las raquetas.
                if (boundsBall.intersects(boundsPlayer) || boundsBall.intersects(boundsPlayerTwo)) {
                    if(boundsBall.intersects(boundsPlayer)) {
                        jugadorActivo = 1;
                    }else if(boundsBall.intersects(boundsPlayerTwo)) {
                        jugadorActivo = 2;
                    }

                    gameFrame.getBall().setDx(gameFrame.getBall().getDx() * -1);
                }
                // Colisión con los lados superior e inferior.
                if (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed() < 0) {
                    gameFrame.getBall().setDy(gameFrame.getBall().getDy() * -1);
                } else if (gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed()
                        + gameFrame.getBall().getHeight() >= gameFrame.getHeight()) {
                    gameFrame.getBall().setDy(gameFrame.getBall().getDy() * -1);
                }
                // Colision con los poderes
                if(boundsBall.intersects(boundsBuff)) {
                    gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
                    if(jugadorActivo == 1) {	//Implementar aumento de velocidad de la pelota
                        gameFrame.getPlayerOne().setColor(Color.YELLOW);
                        jugadorActivo = 0;

                        activarBuffJugador1 = new TimerTask() {
                            @Override
                            public void run() {
                                if(jugadorActivo == 1) {
                                    gameFrame.getPlayerOne().setColor(Color.WHITE);
                                    gameFrame.getBall().setColor(Color.YELLOW);

                                    gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() * 3);
                                    timer.schedule(quitarBuffJugador1, 0, 10);
                                    cancel();
                                }
                            }
                        };
                        timer.schedule(activarBuffJugador1, 0, 10);

                        quitarBuffJugador1 = new TimerTask() {
                            @Override
                            public void run() {
                                if(jugadorActivo == 2) {
                                    gameFrame.getBall().setColor(Color.RED);

                                    gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed()/3);
                                    cancel();
                                }
                            }
                        };

                    }else {
                        gameFrame.getPlayerTwo().setColor(Color.YELLOW);
                        jugadorActivo = 0;

                        activarBuffJugador2 = new TimerTask() {
                            @Override
                            public void run() {
                                if(jugadorActivo == 2) {
                                    gameFrame.getPlayerTwo().setColor(Color.WHITE);
                                    gameFrame.getBall().setColor(Color.YELLOW);

                                    gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed() * 3);
                                    timer.schedule(quitarBuffJugador2, 0, 10);
                                    cancel();
                                }
                            }
                        };
                        timer.schedule(activarBuffJugador2, 0, 10);

                        quitarBuffJugador2 = new TimerTask() {
                            @Override
                            public void run() {
                                if(jugadorActivo == 1) {
                                    gameFrame.getBall().setColor(Color.RED);

                                    gameFrame.getBall().setSpeed(gameFrame.getBall().getSpeed()/3);
                                    cancel();
                                }
                            }
                        };
                    }
                } else if (boundsBall.intersects(boundsDebuff)) {
                    gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));
                    if(jugadorActivo == 1) {
                        gameFrame.getPlayerOne().setColor(Color.BLUE);
                        movimientoJ1 -= reduccionVelocidad;

                        debuffJugador1 = new TimerTask() {
                            @Override
                            public void run() {
                                gameFrame.getPlayerOne().setColor(Color.WHITE);
                                movimientoJ1 += reduccionVelocidad;
                                cancel();
                            }
                        };
                        timer.schedule(debuffJugador1, 5000, 1);
                    }else {
                        gameFrame.getPlayerTwo().setColor(Color.BLUE);
                        movimientoJ2 -= reduccionVelocidad;

                        debuffJugador2 = new TimerTask() {
                            @Override
                            public void run() {
                                gameFrame.getPlayerTwo().setColor(Color.WHITE);
                                movimientoJ2 += reduccionVelocidad;
                                cancel();
                            }
                        };
                        timer.schedule(debuffJugador2, 5000, 1);
                    }
                }
                // Test de puntaje.
                if (gameFrame.getBall().getX() < 0) {
                    System.out.println("Punto");
                    gameFrame.getBall().setX(200);
                }
                // Se lanza la pelota.
                gameFrame.getBall().setX(gameFrame.getBall().getX() + gameFrame.getBall().getDx() * gameFrame.getBall().getSpeed());
                gameFrame.getBall().setY(gameFrame.getBall().getY() + gameFrame.getBall().getDy() * gameFrame.getBall().getSpeed());
            }
        };
        // Task, ms Inicio, ms Refresh.
        timer.schedule(moveBall, 0, 10);

        /*----- LEER -----
         * PosicionamientoPoderes define el poder y su posicion.
         * OcultarPoderes oculta el poder.
         *
         * LoopPoderes hace un ciclo entre PosicionamientoPoderes y VisibilidadPoderes:
         * PosPoderes -> OcuPoderes -> PosPoderes -> OcuPoderes -> PosPoderes -> OcuPoderes -> ...
         *
         * timer.schedule(x, y, z) recibe tres parametros.
         * timer.schedule(TimerTask, Delay antes del primer ciclo, Periodo (delay) para cada ciclo posterior al primero.
         *
         * Debido a la naturaleza del ciclo, el Periodo (Tercer parametro) en PosicionamientoPoderes y OcultarPoderes NO INFLUYE
         * en el ciclo. Sin embargo, debe ser un numero positivo mayor que cero.
         *
         * El ciclo funciona de la siguiente manera:
         * 1. Delay de PosicionamientoPoderes
         * 2. Dispara PosicionamientoPoderes
         * 3. Delay de OcultarPoderes
         * 4. Dispara de OcultarPoderes
         * 5. Se repite el ciclo
         *
         * En LoopPoderes no debe haber Delay (Segundo parametro).
         * El Periodo de Loop poderes debe ser la suma del Delay de PosicionamientoPoderes y OcultarPoderes
         */

        loopPoderes = new TimerTask() {
            public void run() {
                posicionamientoPoderes = new TimerTask() {
                    @Override
                    public void run() {
                        poderAleatorio = (int) (Math.random() * 2) + 1;
                        xAleatorio = ThreadLocalRandom.current().nextInt(gameFrame.getX()+70, gameFrame.getWidth()-80 + 1);
                        yAleatorio = ThreadLocalRandom.current().nextInt(gameFrame.getY()+50, gameFrame.getHeight()-50 + 1);
                        System.out.println(xAleatorio + " "+ yAleatorio);
                        if(poderAleatorio == 1) {
                            gameFrame.setBuff(new SPower(xAleatorio, yAleatorio, 20, 20, Color.YELLOW));
                        }else {
                            gameFrame.setDebuff(new SPower(xAleatorio, yAleatorio, 20, 20, Color.BLUE));
                        }
                        cancel();
                        timer.schedule(ocultarPoderes, 10000, 1);	//Delay: Tiempo que el poder estara visible
                    }
                };
                timer.schedule(posicionamientoPoderes, 5000, 1);	//Delay: Tiempo que el poder NO estara visible

                ocultarPoderes = new TimerTask() {
                    @Override
                    public void run() {
                        if(poderAleatorio == 1) {
                            gameFrame.setBuff(new SPower(0, 0, 0, 0, Color.YELLOW));
                        }else {
                            gameFrame.setDebuff(new SPower(0, 0, 0, 0, Color.BLUE));
                        }

                        cancel();
                    }
                };
            }
        };
        timer.schedule(loopPoderes, 0, 15000);	//En el Periodo poner la suma de los dos anteriores Delays
    }
}