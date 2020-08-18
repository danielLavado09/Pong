package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.sprites.*;


public class GameFrame extends JPanel {

    private SBall ball;
    private Sprite net;
    private SPlayer playerOne;
    private SPlayer playerTwo;
    private Sprite buff;
    private Sprite debuff;

    public GameFrame() {
    }

    public GameFrame(SBall ball, Sprite net, SPlayer playerOne, SPlayer playerTwo, Sprite buff, Sprite debuff) {
        super();
        this.ball = ball;
        this.net = net;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.buff = buff;
        this.debuff = debuff;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ball = getBall();
        net = getNet();
        playerOne = getPlayerOne();
        playerTwo = getPlayerTwo();
        buff = getBuff();
        if (ball != null && net != null && playerOne != null && playerTwo != null && buff != null && debuff != null) {
            net.paint(g2);
            ball.paint(g2);
            playerOne.paint(g2);
            playerTwo.paint(g2);
            buff.paint(g2);
            debuff.paint(g2);
        }
        repaint();
    }

    public void asignarTecla(KeyStroke keyStroke, String clave, Action accion) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, clave);
        getActionMap().put(clave, accion);
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

    public SPlayer getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(SPlayer playerOne) {
        this.playerOne = playerOne;
    }

    public SPlayer getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(SPlayer playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Sprite getBuff() {
        return buff;
    }

    public void setBuff(Sprite buff) {
        this.buff = buff;
    }

    public Sprite getDebuff() {
        return debuff;
    }

    public void setDebuff(Sprite debuff) {
        this.debuff = debuff;
    }

}