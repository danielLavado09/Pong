package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {
	
	private GameFrame gameFrame;
	private JPanel contentPane;

	public Window(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		setResizable(false);
		setTitle("Pong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		gameFrame.setBackground(Color.BLACK);
		gameFrame.setFocusable(true);
		contentPane.add(gameFrame, BorderLayout.CENTER);
	
	}
	
}
