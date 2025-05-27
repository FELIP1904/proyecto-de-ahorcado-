package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class jinicio extends JFrame {
    private JTextField campoPalabra;
    private JPasswordField campoPassword;
    private JButton botonIniciar;
    private boolean modoDosJugadores;

    public jinicio(boolean modoDosJugadores) {
        this.modoDosJugadores = modoDosJugadores;
        setTitle(modoDosJugadores ? "Jugador 1: Introduce la palabra" : "Inicio del Ahorcado");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel instruccion = new JLabel(modoDosJugadores ?
                "Jugador 1, introduce la palabra secreta:" :
                "Introduce la palabra secreta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(instruccion, gbc);

        if (modoDosJugadores) {

            campoPassword = new JPasswordField(15);
            campoPassword.setEchoChar('*');
            gbc.gridy = 1;
            panel.add(campoPassword, gbc);
        } else {

            campoPalabra = new JTextField(15);
            gbc.gridy = 1;
            panel.add(campoPalabra, gbc);
        }

        botonIniciar = new JButton("Iniciar Juego");
        gbc.gridy = 2;
        panel.add(botonIniciar, gbc);

        if (modoDosJugadores) {
            JButton btnMostrar = new JButton("Mostrar");
            btnMostrar.addActionListener(e -> {
                campoPassword.setEchoChar((char)0); // Mostrar caracteres
                Timer timer = new Timer(2000, ev -> {
                    campoPassword.setEchoChar('*');
                    btnMostrar.setEnabled(true);
                });
                timer.setRepeats(false);
                timer.start();
                btnMostrar.setEnabled(false);
            });
            gbc.gridy = 3;
            panel.add(btnMostrar, gbc);
        }

        add(panel);

        botonIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String palabra;
                if (modoDosJugadores) {
                    palabra = new String(campoPassword.getPassword()).trim();
                } else {
                    palabra = campoPalabra.getText().trim();
                }

                if (!palabra.isEmpty()) {
                    setVisible(false);
                    if (modoDosJugadores) {
                        JOptionPane.showMessageDialog(null,
                                "Ahora pasa el control al Jugador 2 para que adivine",
                                "Cambio de jugador",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    new fjuego(palabra, modoDosJugadores);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingresa una palabra válida.");
                }
            }
        });

        setVisible(true);
    }
}