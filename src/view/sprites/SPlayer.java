package view.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class SPlayer extends Sprite {

	public SPlayer(double x, double y, double height, double width, Color color) {
		super(x, y, height, width, color);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D racket = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g2.setColor(this.getColor());
		g2.draw(racket);
		g2.fill(racket);
	}

}