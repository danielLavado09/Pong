package model.sprites;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Sprite {

	protected double x;
	protected double y;
	protected double height; 
	protected double width;
	protected Color color;

	/**
	 * @param x La posición en el eje x del Sprite.
	 * @param y La posición en el eje y del Sprite.
	 * @param height La altura del Sprite.
	 * @param width El ancho del Sprite.
	 * @param color El color que tendrá el Sprite.
	 */
	
	public Sprite(double x, double y, double height, double width, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.color = color;
	}
	
	public abstract void paint(Graphics g);

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}