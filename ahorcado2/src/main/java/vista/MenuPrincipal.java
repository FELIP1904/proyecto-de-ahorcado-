package vista;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que representa la ventana principal del juego Ahorcado.
 * Desde aquí el usuario puede iniciar un nuevo juego, ver el ranking o cerrar sesión.
 */
public class MenuPrincipal extends JFrame {

    /**
     * Constructor que configura y muestra la ventana principal con las opciones disponibles.
     */
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

        // Título principal de la ventana
        JLabel titulo = new JLabel("Ahorcado");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 80));
        gbc.gridy = 0;
        add(titulo, gbc);

        // Botón para iniciar un nuevo juego
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setPreferredSize(new Dimension(250, 50));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        btnJugar.addActionListener(e -> {
            dispose();          // Cierra esta ventana
            new JuegoNuevo();   // Abre la ventana para iniciar un juego nuevo
        });
        add(btnJugar, gbc);

        // Botón para mostrar el ranking de jugadores
        JButton btnRanking = new JButton("Ranking");
        btnRanking.setPreferredSize(new Dimension(250, 50));
        btnRanking.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        btnRanking.addActionListener(e -> new RankingVista());
        add(btnRanking, gbc);

        // Botón para cerrar sesión y volver a la pantalla inicial
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(250, 50));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        btnCerrarSesion.addActionListener(e -> {
            dispose();       // Cierra esta ventana
            new finicial();  // Abre la ventana inicial (login o bienvenida)
        });
        add(btnCerrarSesion, gbc);

        setVisible(true);
    }
}
