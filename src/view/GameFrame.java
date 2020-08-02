package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.JPanel;

//Figuras
public class GameFrame extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle2D rectangulo1 = new Rectangle2D.Double(60, 245, 10, 70); //x, y, ancho, alto
		Rectangle2D rectangulo2 = new Rectangle2D.Double(714, 245, 10, 70);
		Rectangle2D mitad = new Rectangle2D.Double(389, 40, 5, 481);
		g2.setColor(Color.WHITE);
		g2.fill(rectangulo1);
		g2.fill(rectangulo2);
		g2.fill(mitad);
		g2.draw(rectangulo1);
		g2.draw(rectangulo2);
		g2.draw(mitad);
		
		Ellipse2D pelota = new Ellipse2D.Double(385, 273, 14, 14);
		g2.setColor(Color.YELLOW);
		g2.fill(pelota);
		g2.draw(pelota);
	}
}