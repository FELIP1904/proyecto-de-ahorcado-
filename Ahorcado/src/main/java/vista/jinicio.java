package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class jinicio extends JFrame {
    private JTextField campoPalabra;
    private JButton botonIniciar;

    public jinicio() {
        setTitle("Inicio del Ahorcado");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoPalabra = new JTextField(15);
        botonIniciar = new JButton("Iniciar Juego");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Palabra secreta:"));
        panel.add(campoPalabra);
        panel.add(botonIniciar);

        add(panel);

        botonIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String palabra = campoPalabra.getText().trim();
                if (!palabra.isEmpty()) {
                    setVisible(false);
                    new fjuego(palabra);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingresa una palabra válida.");
                }
            }
        });

        setVisible(true);
    }
}
