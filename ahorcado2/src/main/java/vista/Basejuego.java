package vista;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que representa la ventana base del juego del ahorcado,
 * mostrando información sobre la historia, reglas y variantes del juego.
 */
public class Basejuego extends JFrame {

    /**
     * Constructor que inicializa la ventana con su diseño y contenido.
     * Configura título, tamaño, fondo, layout y agrega secciones de información.
     */
    public Basejuego() {
        setTitle("Base del Juego - Ahorcado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Base del Juego");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 60));
        gbc.gridy = 0;
        add(titulo, gbc);

        JPanel contenidoPanel = new JPanel();
        contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
        contenidoPanel.setBackground(new Color(154, 227, 170));
        contenidoPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        addSeccion(contenidoPanel, "Historia del Juego",
                "El juego del ahorcado tiene sus orígenes en el siglo XIX.\n" +
                        "Se cree que surgió como método para enseñar ortografía a los niños.\n" +
                        "El nombre proviene del dibujo que se completa con cada error.");

        addSeccion(contenidoPanel, "Reglas Básicas",
                "• Un jugador piensa una palabra y el otro trata de adivinarla\n" +
                        "• Por cada letra incorrecta, se añade una parte al dibujo\n" +
                        "• El juego termina cuando:\n" +
                        "   - Se adivina la palabra (gana el adivinador)\n" +
                        "   - Se completa el dibujo (pierde el adivinador)\n" +
                        "• Tradicionalmente se permiten 6 errores");

        addSeccion(contenidoPanel, "Estrategias para Ganar",
                "1. Comienza por las vocales (A, E, I, O, U)\n" +
                        "2. Luego prueba consonantes comunes (R, S, T, L, N)\n" +
                        "3. Observa la longitud de la palabra\n" +
                        "4. Recuerda palabras usadas antes\n" +
                        "5. Fíjate en patrones de palabras frecuentes");

        addSeccion(contenidoPanel, "Variantes del Juego",
                "• Juego cooperativo: Todos contra el reloj\n" +
                        "• Con categorías: Solo palabras de un tema\n" +
                        "• Con pistas: Dar una pista inicial\n" +
                        "• Con tiempo límite: Adivinar antes de X minutos");

        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setPreferredSize(new Dimension(250, 50));
        btnVolver.setFont(new Font("Arial", Font.BOLD, 20));
        btnVolver.addActionListener(e -> dispose());

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.CENTER;
        add(btnVolver, gbc);

        setVisible(true);
    }

    /**
     * Agrega una sección con un título y contenido de texto al panel dado.
     *
     * @param panel    el JPanel donde se añadirá la sección
     * @param titulo   el título de la sección
     * @param contenido el texto descriptivo de la sección
     */
    private void addSeccion(JPanel panel, String titulo, String contenido) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        // Expande horizontalmente
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTitulo.getPreferredSize().height));
        panel.add(lblTitulo);

        JTextArea textArea = new JTextArea(contenido);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(154, 227, 170));
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));

        // Expande horizontalmente
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, textArea.getPreferredSize().height * 5));

        panel.add(textArea);
    }
}
