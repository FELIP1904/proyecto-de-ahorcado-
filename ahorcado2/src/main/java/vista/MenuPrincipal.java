package vista;

import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;


        JLabel titulo = new JLabel("Ahorcado");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 80));
        gbc.gridy = 0;
        add(titulo, gbc);


        JButton btnJugar = new JButton("Jugar");
        btnJugar.setPreferredSize(new Dimension(250, 50));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        btnJugar.addActionListener(e -> {
            dispose();
            new JuegoNuevo();
        });
        add(btnJugar, gbc);



        JButton btnRanking = new JButton("Ranking");
        btnRanking.setPreferredSize(new Dimension(250, 50));
        btnRanking.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnRanking, gbc);

        btnRanking.addActionListener(e ->
                new RankingVista()
        );

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(250, 50));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        add(btnCerrarSesion, gbc);
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new finicial();
        });

        setVisible(true);
    }




}