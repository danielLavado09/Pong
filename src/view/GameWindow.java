package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameWindow extends JFrame {

	private GameFrame gameFrame;
	private JPanel contentPane;
	private JPanel dataPanel;
	private JPanel optionsPanel;
	private JLabel lblPlayerOneScore;
	private JLabel lblPlayerTwoScore;
	private JButton btnExit;
	private JButton btnPause;

	public GameWindow(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		setResizable(false);
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
		setIconImage(icon.getImage());
		setTitle("Pong");
		setSize(800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		gameFrame.setBackground(Color.BLACK);
		gameFrame.setFocusable(true);
		contentPane.add(gameFrame, BorderLayout.CENTER);

		// Panel: Información.
		dataPanel = new JPanel(new BorderLayout());
		dataPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dataPanel.setBackground(Color.white);
		contentPane.add(dataPanel, BorderLayout.NORTH);
		// Textos Score.
		lblPlayerOneScore = new JLabel();
		lblPlayerTwoScore = new JLabel();
		dataPanel.add(lblPlayerOneScore, BorderLayout.WEST);
		dataPanel.add(lblPlayerTwoScore, BorderLayout.EAST);

		// Panel: Buttons.
		optionsPanel = new JPanel(new GridLayout(0,2));
		optionsPanel.setBorder(new EmptyBorder(1, 0, 0, 0));
		contentPane.add(optionsPanel, BorderLayout.SOUTH);
		// Button: Pause.
		btnPause = new JButton("Pausar/Reanudar Juego");
		btnPause.setFocusable(false);
		btnPause.setBackground(Color.black);
		btnPause.setForeground(Color.white);
		btnPause.setFont(new Font("Courier New", Font.BOLD, 11));
		optionsPanel.add(btnPause);
		// Button: Exit.
		btnExit = new JButton("Salir del Juego");
		btnExit.setFocusable(false);
		btnExit.setBackground(Color.black);
		btnExit.setForeground(Color.white);
		btnExit.setFont(new Font("Courier New", Font.BOLD, 11));
		optionsPanel.add(btnExit);
	}

	public JButton getBtnExit() {
		return btnExit;
	}


	public JButton getBtnPause() {
		return btnPause;
	}

	public JLabel getLblPlayerOneScore() {
		return lblPlayerOneScore;
	}

	public void setLblPlayerOneScore(JLabel lblPlayerOneScore) {
		this.lblPlayerOneScore = lblPlayerOneScore;
	}

	public JLabel getLblPlayerTwoScore() {
		return lblPlayerTwoScore;
	}

	public void setLblPlayerTwoScore(JLabel lblPlayerTwoScore) {
		this.lblPlayerTwoScore = lblPlayerTwoScore;
	}

}