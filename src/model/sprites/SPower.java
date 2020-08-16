package model.sprites;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SPower extends Sprite {

    public SPower(double x, double y, double height, double width, Color color) {
        super(x, y, height, width, color);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D buff = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.draw(buff);
        g2.fill(buff);
    }

}