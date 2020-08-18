package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreWindow extends JFrame {

    private JPanel contentPane;
    private DefaultTableModel tableModel;
    private JTable table;

    public ScoreWindow() {
        setTitle("Pong - Score");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        setIconImage(icon.getImage());
        setSize(380, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setBackground(Color.black);
        setContentPane(contentPane);

        // Title.
        JLabel lblTitle = new JLabel("TopScore");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setForeground(Color.white);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Courier New", Font.BOLD, 36));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Table: Datos.
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        String[] columnas = {"Nickname", "Puntos"};
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane.setViewportView(table);

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

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

}