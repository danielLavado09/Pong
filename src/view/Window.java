package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//Ventana principal
public class Window extends JFrame {
	//Declaraciones
	private GameFrame panel;
	private JPanel contentPane;

	//Constructor
	public Window(GameFrame panel) {
		this.panel = panel;
		
		setTitle("Pong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Tablero de juego
		panel = new GameFrame();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 784, 561);	//x, y, ancho, altura
		contentPane.add(panel);
	}
}
