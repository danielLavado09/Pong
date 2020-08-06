package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import view.GameFrame;
import view.Window;
import view.sprites.*;

public class Controller {
	//Declaraciones
	GameFrame juego;
	Actions actions;
	Timer timer = new Timer();
	double random = Math.round(Math.random());
	int randomDirection;
	private final int movimiento = 5;

	//Constructor
	public Controller() {
		GameFrame juego = new GameFrame();
		Actions actions = new Actions();
		Window frame = new Window(juego);
		frame.setVisible(true);
		// Añadiendo Sprites al GamFrame.
		juego.setNet(new SNet((juego.getWidth() - 5) / 2, 0, juego.getHeight(), 5, Color.white));
		juego.setBall(new SBall((juego.getWidth() - 15) / 2, (juego.getHeight() - 15) / 2, 15, 15, Color.red));
		juego.setPlayerOne(new SPlayer(juego.getWidth() - (20), (juego.getHeight() - 70) / 2, 70, 10, Color.white));
		juego.setPlayerTwo(new SPlayer(10, (juego.getHeight() - 70) / 2, 70, 10, Color.white));
		// Prueba de dirección random.
		if (random == 0) {
			randomDirection = -1;
		} else {
			randomDirection = 1;
		}

		//Asignar teclas a actions
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "presionadoArribaJ1", actions.presionadoArribaJ1);	//false = Presionado
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "presionadoArribaJ2", actions.presionadoArribaJ2);
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "liberadoArribaJ1", actions.liberadoArribaJ1);	//true = Liberado
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "liberadooArribaJ2", actions.liberadoArribaJ2);

		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "presionadoAbajoJ1", actions.presionadoAbajoJ1);
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "presionadoAbajoJ2", actions.presionadoAbajoJ2);
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "liberadoAbajoJ1", actions.liberadoAbajoJ1);
		juego.asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "liberadoAbajoJ2", actions.liberadoAbajoJ2);
		
		//Actualizar posicion de los jugadores, si la tecla correspondiente es presionada
		TimerTask posicionJugadores = new TimerTask() {
			@Override
			public void run() {
				if(actions.isArribaJ1()) {
					if (juego.getPlayerOne().getY() >= movimiento) {
						juego.getPlayerOne().setY(juego.getPlayerOne().getY() - movimiento);
					}
				} else if(actions.isAbajoJ1()) {
					if (juego.getPlayerOne().getY() <= juego.getHeight() - 75) {
						juego.getPlayerOne().setY((juego.getPlayerOne().getY()) + movimiento);
					}
				}
	
				if(actions.isArribaJ2()) {
					if (juego.getPlayerTwo().getY() >= movimiento) {
						juego.getPlayerTwo().setY((juego.getPlayerTwo().getY()) - movimiento);
					}
				} else if(actions.isAbajoJ2()) {
					if (juego.getPlayerTwo().getY() <= juego.getHeight() - 75) {
						juego.getPlayerTwo().setY((juego.getPlayerTwo().getY()) + movimiento);
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
						(int) (juego.getBall().getX() + juego.getBall().getDx() * juego.getBall().getSpeed()),
						(int) (juego.getBall().getY() + juego.getBall().getDy() * juego.getBall().getSpeed()),
						(int) juego.getBall().getWidth(), (int) juego.getBall().getHeight());
				Rectangle boundsPlayer = new Rectangle((int) juego.getPlayerOne().getX(),
						(int) juego.getPlayerOne().getY(), (int) juego.getPlayerOne().getWidth(),
						(int) juego.getPlayerOne().getHeight());
				Rectangle boundsPlayerTwo = new Rectangle((int) juego.getPlayerTwo().getX(),
						(int) juego.getPlayerTwo().getY(), (int) juego.getPlayerTwo().getWidth(),
						(int) juego.getPlayerTwo().getHeight());
				// Colisión con las raquetas.
				if (boundsBall.intersects(boundsPlayer) || boundsBall.intersects(boundsPlayerTwo)) {
					juego.getBall().setDx(juego.getBall().getDx() * -1);
				}
				// Colisión con los lados superior e inferior.
				if (juego.getBall().getY() + juego.getBall().getDy() * juego.getBall().getSpeed() < 0) {
					juego.getBall().setDy(juego.getBall().getDy() * -1);
				} else if (juego.getBall().getY() + juego.getBall().getDy() * juego.getBall().getSpeed()
						+ juego.getBall().getHeight() >= juego.getHeight()) {
					juego.getBall().setDy(juego.getBall().getDy() * -1);
				}
				// Test de puntaje.
				if (juego.getBall().getX() < 0) {
					System.out.println("Punto");
					juego.getBall().setX(200);
				}
				// Se lanza la pelota.
				juego.getBall().setX(juego.getBall().getX() + juego.getBall().getDx() * juego.getBall().getSpeed());
				juego.getBall().setY(juego.getBall().getY() + juego.getBall().getDy() * juego.getBall().getSpeed());
			}
		};
		// Task, ms Inicio, ms Refresh.
		timer.schedule(moveBall, 0, 10);
	}
}