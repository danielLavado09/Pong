package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpWindow extends JFrame {

    private JPanel contentPane;

    public HelpWindow() {
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        setIconImage(icon.getImage());
        setTitle("Pong - Ayuda");
        setSize(430,250);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JTextArea txtHelp = new JTextArea();
        txtHelp.setText("1. Para jugar da clic en: Iniciar Juego." +
                "\n2. Controles Jugador Uno: W: Arriba, S: Abajo." +
                "\n3. Controles Jugador Dos: ↑: Arriba, ↓: Abajo." +
                "\n4. Pausar: P, o presionando el botón correspondiente." +
                "\n5. Reanudar: P, o presionando el botón correspondiente." +
                "\n6. Poderes" +
                "\n 6.1. Amarillo: Velocidad de pelota aumentada." +
                "\n 6.2. Azul: Lentitud en la raqueta." +
                "\n\n\nAutores: David Cleves, Daniel Lavado." +
                "\nAño: 2020.");
        txtHelp.setEditable(false);
        txtHelp.setFocusable(false);
        txtHelp.setFont(new Font("Courier New", Font.BOLD, 12));
        contentPane.add(txtHelp, BorderLayout.CENTER);

        JButton btnExit = new JButton("Salir ");
        btnExit.setFocusable(false);
        btnExit.setBackground(Color.black);
        btnExit.setForeground(Color.white);
        btnExit.setFont(new Font("Courier New", Font.BOLD, 11));
        contentPane.add(btnExit, BorderLayout.SOUTH);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}