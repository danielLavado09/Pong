package model.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class SBall extends Sprite {

    private double speed = 5;
    private double dy;
    private double dx;
    private double angle;

    public SBall(double x, double y, double height, double width, Color color) {
        super(x, y, height, width, color);

        angle = 202;
        this.dx = Math.cos(Math.toRadians(angle));
        this.dy = Math.sin(Math.toRadians(angle));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D ball = new Ellipse2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.draw(ball);
        g2.fill(ball);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}