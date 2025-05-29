package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de inicio para el juego del ahorcado.
 * Permite al jugador ingresar la palabra secreta que se debe adivinar.
 * Soporta modo de un jugador y modo de dos jugadores.
 *
 * @author [Nombre del autor]
 * @version 1.0
 */
public class jinicio extends JFrame {
    /**
     * Campo de texto para ingresar la palabra secreta visiblemente en modo un jugador.
     */
    private JTextField campoPalabra;

    /**
     * Campo de contraseña para ingresar la palabra secreta de forma oculta en modo dos jugadores.
     */
    private JPasswordField campoPassword;

    /**
     * Botón para iniciar el juego después de validar la palabra ingresada.
     */
    private JButton botonIniciar;

    /**
     * Indica si el juego está en modo dos jugadores (true) o un jugador (false).
     */
    private boolean modoDosJugadores;

    /**
     * Constructor que inicializa la ventana de inicio del juego.
     * Configura la interfaz gráfica según el modo de juego seleccionado.
     *
     * @param modoDosJugadores true para modo dos jugadores (oculta la palabra),
     *                         false para modo un jugador (muestra la palabra)
     */
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

        /**
         * Manejador de eventos para el botón de iniciar juego.
         * Valida la palabra ingresada y procede a iniciar el juego principal.
         */
        botonIniciar.addActionListener(new ActionListener() {
            /**
             * Método invocado cuando se presiona el botón de iniciar juego.
             * Obtiene la palabra secreta, valida que no esté vacía y abre la ventana del juego.
             *
             * @param e El evento de acción generado al hacer clic en el botón
             */
            @Override
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