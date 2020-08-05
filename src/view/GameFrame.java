package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import view.sprites.*;

//Figuras
public class GameFrame extends JPanel {

	private SBall ball;
	private Sprite net;
	private Sprite playerOne;
	private Sprite playerTwo;

	public GameFrame() {
	}

	public GameFrame(SBall ball, Sprite net, Sprite playerOne, Sprite playerTwo) {
		super();
		this.ball = ball;
		this.net = net;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		ball = getBall();
		net = getNet();
		playerOne = getPlayerOne();
		playerTwo = getPlayerTwo();
		if (ball != null && net != null && playerOne != null && playerTwo != null) {
			net.paint(g2);
			ball.paint(g2);
			playerOne.paint(g2);
			playerTwo.paint(g2);
		}
		repaint();
	}

	public SBall getBall() {
		return ball;
	}

	public void setBall(SBall ball) {
		this.ball = ball;
	}

	public Sprite getNet() {
		return net;
	}

	public void setNet(Sprite net) {
		this.net = net;
	}

	public Sprite getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Sprite playerOne) {
		this.playerOne = playerOne;
	}

	public Sprite getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Sprite playerTwo) {
		this.playerTwo = playerTwo;
	}

}