package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    private JLabel titleText;
    private JPanel optionsPanel;
    private JButton btnStart;
    private JButton btnExit;
    private JButton btnHelp;
    private JButton btnTopScore;

    public MainWindow() {
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        setIconImage(icon.getImage());
        setTitle("Pong - Main Menu");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.black);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Titulo del juego.
        titleText = new JLabel("Pong");
        titleText.setForeground(Color.white);
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setFont(new Font("Courier New", Font.BOLD, 56));
        contentPane.add(titleText, BorderLayout.NORTH);

        // Panel de opciones.
        optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(Color.black);
        contentPane.add(optionsPanel, BorderLayout.CENTER);

        // Button: Iniciar juego.
        btnStart = new JButton("INICIAR JUEGO");
        btnStart.setFocusable(false);
        btnStart.setBackground(Color.black);
        btnStart.setForeground(Color.white);
        btnStart.setFont(new Font("Courier New", Font.BOLD, 18));
        GridBagConstraints gbcStart = new GridBagConstraints();
        gbcStart.insets = new Insets(0, 0, 50, 0);
        gbcStart.gridx = 0;
        gbcStart.gridy = 0;
        optionsPanel.add(btnStart, gbcStart);

        // Button: Top Score.
        btnTopScore = new JButton("TOP SCORE");
        btnTopScore.setFocusable(false);
        btnTopScore.setBackground(Color.black);
        btnTopScore.setForeground(Color.white);
        btnTopScore.setFont(new Font("Courier New", Font.BOLD, 18));
        GridBagConstraints gbcTopScore = new GridBagConstraints();
        gbcTopScore.insets = new Insets(0, 0, 50, 0);
        gbcTopScore.gridx = 0;
        gbcTopScore.gridy = 1;
        optionsPanel.add(btnTopScore, gbcTopScore);

        // Button: Ayuda.
        btnHelp = new JButton("AYUDA");
        btnHelp.setFocusable(false);
        btnHelp.setBackground(Color.black);
        btnHelp.setForeground(Color.white);
        btnHelp.setFont(new Font("Courier New", Font.BOLD, 18));
        GridBagConstraints gbcHelp = new GridBagConstraints();
        gbcHelp.insets = new Insets(0, 0, 50, 0);
        gbcHelp.gridx = 0;
        gbcHelp.gridy = 2;
        optionsPanel.add(btnHelp, gbcHelp);

        // Button: Salir.
        btnExit = new JButton("SALIR");
        btnExit.setFocusable(false);
        btnExit.setBackground(Color.black);
        btnExit.setForeground(Color.white);
        btnExit.setFont(new Font("Courier New", Font.BOLD, 18));
        GridBagConstraints gbcExit = new GridBagConstraints();
        gbcExit.gridx = 0;
        gbcExit.gridy = 3;
        optionsPanel.add(btnExit, gbcExit);
    }

    public JLabel getTitleText() {
        return titleText;
    }

    public void setTitleText(JLabel titleText) {
        this.titleText = titleText;
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void setOptionsPanel(JPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public void setBtnStart(JButton btnStart) {
        this.btnStart = btnStart;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public JButton getBtnHelp() {
        return btnHelp;
    }

    public void setBtnHelp(JButton btnHelp) {
        this.btnHelp = btnHelp;
    }

    public JButton getBtnTopScore() {
        return btnTopScore;
    }

    public void setBtnTopScore(JButton btnTopScore) {
        this.btnTopScore = btnTopScore;
    }

}