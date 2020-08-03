package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import launcher.Logica;

//Figuras
public class GameFrame extends JPanel {	
	//Declaraciones
	private int jugador1Y = 245;
	private int jugador2Y = 245;
	private final int movimiento = 5;
	private final int correccion = 1;
	private boolean arribaJ1 = false;
	private boolean arribaJ2 = false;
	private boolean abajoJ1 = false;
	private boolean abajoJ2 = false;
	
	//Graphics2D
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle2D rectangulo1 = new Rectangle2D.Double(60, jugador1Y, 10, 70); //x, y, ancho, alto
		Rectangle2D rectangulo2 = new Rectangle2D.Double(714,jugador2Y, 10, 70);
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
	
	//Constructor
	public GameFrame() {
		//Actualizar posicion de los jugadores, si la tecla correspondiente es presionada
		Action actualizarPosicion = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arribaJ1) {
					moverArribaJ1();
				} else if(abajoJ1) {
					moverAbajoJ1();
				}
				
				if(arribaJ2) {
					moverArribaJ2();
				} else if(abajoJ2) {
					moverAbajoJ2();
				}
			}
		};
		
		//Timer
		Timer timer = new Timer (10, actualizarPosicion);	//Cada 10 milisegundos llama a actualizarPosicion
		
		timer.start();
		
		//Actions de las teclas
		//Arriba
		Action presionadoArribaJ1 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arribaJ1 = true;
			}
		};

		Action presionadoArribaJ2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arribaJ2 = true;
			}
		};
		
		Action liberadoArribaJ1 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arribaJ1 = false;
			}
		};

		Action liberadoArribaJ2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arribaJ2 = false;
			}
		};

		//Abajo
		Action presionadoAbajoJ1 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abajoJ1 = true;
			}
		};
		
		Action presionadoAbajoJ2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abajoJ2 = true;
			}
		};
		
		Action liberadoAbajoJ1 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abajoJ1 = false;
			}
		};
		
		Action liberadoAbajoJ2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abajoJ2 = false;
			}
		};

		//Asignar teclas a actions
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "presionadoArribaJ1", presionadoArribaJ1);	//false = Presionado
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "presionadoArribaJ2", presionadoArribaJ2);
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "liberadoArribaJ1", liberadoArribaJ1);	//true = Liberado
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "liberadooArribaJ2", liberadoArribaJ2);
		
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "presionadoAbajoJ1", presionadoAbajoJ1);
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "presionadoAbajoJ2", presionadoAbajoJ2);
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "liberadoAbajoJ1", liberadoAbajoJ1);
		asignarTecla(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "liberadoAbajoJ2", liberadoAbajoJ2);
	}
	
	public void asignarTecla(KeyStroke keyStroke, String clave, Action accion) {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, clave);
		getActionMap().put(clave, accion);
	}
	
	//Moviemiento de los jugadores
	public void moverArribaJ1() {
		repaint(60, jugador1Y, 10+correccion, 70+correccion);
		jugador1Y -= movimiento;
		
		if(jugador1Y <= 0) {	//No sobrepasar el borde superior de la ventana
			jugador1Y = 0;
		}else {
			repaint(60, jugador1Y, 10+correccion, 70+correccion);
		}
		
	}
	
	public void moverArribaJ2() {
		repaint(714, jugador2Y, 10+correccion, 70+correccion);
		jugador2Y -= movimiento;
		
		if(jugador2Y <= 0) {	//No sobrepasar el borde superior de la ventana
			jugador2Y = 0;
		}else {
			repaint(714, jugador2Y, 10+correccion, 70+correccion);
		}
	}
	
	public void moverAbajoJ1() {
		repaint(60, jugador1Y, 10+correccion, 70+correccion);
		jugador1Y += movimiento;
		
		if(jugador1Y >= 491) {	//No sobrepasar el borde inferior de la ventana
			jugador1Y = 491;
		}else {
			repaint(60, jugador1Y, 10+correccion, 70+correccion);
		}
	}
	public void moverAbajoJ2() {
		repaint(714, jugador2Y, 10+correccion, 70+correccion);
		jugador2Y += movimiento;
		
		if(jugador2Y >= 491) {	//No sobrepasar el borde inferior de la ventana
			jugador2Y = 491;
		}else {
			repaint(714, jugador2Y, 10+correccion, 70+correccion);
		}
	}
}