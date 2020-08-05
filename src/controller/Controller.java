package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import view.GameFrame;
import view.Window;
import view.sprites.*;

public class Controller {
	GameFrame juego;
	Timer timer = new Timer();
	double random = Math.round(Math.random());
	int randomDirection;

	public Controller() {
		GameFrame juego = new GameFrame();
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
		juego.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Movimiento jugador uno.
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (juego.getPlayerOne().getY() >= 30) {
						juego.getPlayerOne().setY((juego.getPlayerOne().getY()) - 30);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (juego.getPlayerOne().getY() <= juego.getHeight() - 90) {
						juego.getPlayerOne().setY((juego.getPlayerOne().getY()) + 30);
					}
				}

				// Movimiento jugador dos.
				if (e.getKeyCode() == KeyEvent.VK_W) {
					if (juego.getPlayerTwo().getY() >= 30) {
						juego.getPlayerTwo().setY((juego.getPlayerTwo().getY()) - 30);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					if (juego.getPlayerTwo().getY() <= juego.getHeight() - 90) {
						juego.getPlayerTwo().setY((juego.getPlayerTwo().getY()) + 30);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
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