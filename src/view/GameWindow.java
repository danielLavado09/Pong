package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameWindow extends JFrame {

	private GameFrame gameFrame;
	private JPanel contentPane;
	private JPanel dataPanel;
	private JLabel lblPlayerOneScore;
	private JLabel lblPlayerTwoScore;

	public GameWindow(GameFrame gameFrame) {
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

		// Panel de información.
		dataPanel = new JPanel(new BorderLayout());
		dataPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dataPanel.setBackground(Color.white);
		contentPane.add(dataPanel, BorderLayout.NORTH);
		// Textos Score.
		lblPlayerOneScore = new JLabel("P1: ");
		lblPlayerTwoScore = new JLabel("P2: ");
		dataPanel.add(lblPlayerOneScore, BorderLayout.WEST);
		dataPanel.add(lblPlayerTwoScore, BorderLayout.EAST);


	}

}