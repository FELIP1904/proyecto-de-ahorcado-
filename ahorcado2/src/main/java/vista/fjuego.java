package vista;

import modelo.ConexionBD;
import modelo.RankingDAO;
import modelo.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class fjuego extends JFrame {
    private ahorcado juego;
    private JLabel lblProgreso, lblJugador, lblImagen, lblLetrasUsadas;
    private JTextField txtLetra;
    private JButton btnAdivinar;
    private boolean modoDosJugadores;
    private ImageIcon[] imagenesAhorcado;

    public fjuego(String palabra, boolean modoDosJugadores) {
        this.modoDosJugadores = modoDosJugadores;
        juego = new ahorcado(palabra, 6);


        cargarImagenes();

        setTitle("Juego del Ahorcado");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(154, 227, 170));


        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(154, 227, 170));

        if (modoDosJugadores) {
            lblJugador = new JLabel("Jugador 2: Adivina la palabra", SwingConstants.CENTER);
            panelSuperior.add(lblJugador, BorderLayout.NORTH);
        }

        lblProgreso = new JLabel(juego.getProgreso(), SwingConstants.CENTER);
        lblProgreso.setFont(new Font("Monospaced", Font.BOLD, 24));
        panelSuperior.add(lblProgreso, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);


        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        actualizarImagen();
        panelPrincipal.add(lblImagen, BorderLayout.CENTER);


        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(154, 227, 170));

        lblLetrasUsadas = new JLabel("Letras usadas: " + juego.getLetrasUsadas(), SwingConstants.CENTER);
        panelInferior.add(lblLetrasUsadas, BorderLayout.NORTH);

        JPanel panelEntrada = new JPanel();
        panelEntrada.setBackground(new Color(154, 227, 170));
        panelEntrada.add(new JLabel("Letra:"));
        txtLetra = new JTextField(1);
        panelEntrada.add(txtLetra);
        btnAdivinar = new JButton("Adivinar");
        panelEntrada.add(btnAdivinar);

        panelInferior.add(panelEntrada, BorderLayout.SOUTH);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);


        btnAdivinar.addActionListener(e -> procesarIntento());
        txtLetra.addActionListener(e -> procesarIntento());

        setVisible(true);
    }

    private void cargarImagenes() {
        imagenesAhorcado = new ImageIcon[8];
        try {

            for (int i = 0; i <= 6; i++) {
                imagenesAhorcado[i] = new ImageIcon(getClass().getResource("/vista/img/ahorcado_"+i+".png"));
                imagenesAhorcado[i] = escalarImagen(imagenesAhorcado[i], 300, 300);
            }

            imagenesAhorcado[7] = new ImageIcon(getClass().getResource("/vista/img/ahorcado_ganador.png"));
            imagenesAhorcado[7] = escalarImagen(imagenesAhorcado[7], 300, 300);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar imágenes: " + e.getMessage() +
                            "\nSe usarán imágenes por defecto",
                    "Error", JOptionPane.ERROR_MESSAGE);


            for (int i = 0; i < imagenesAhorcado.length; i++) {
                imagenesAhorcado[i] = crearImagenPorDefecto(i);
            }
        }
    }

    private ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {
        if (icono == null) return null;
        Image img = icono.getImage();
        Image newImg = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private ImageIcon crearImagenPorDefecto(int errores) {
        BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();


        g2d.setColor(new Color(154, 227, 170));
        g2d.fillRect(0, 0, 300, 300);


        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Intentos: " + (6 - errores) + "/6", 20, 30);


        g2d.setStroke(new BasicStroke(3));
        if (errores > 0) g2d.drawLine(50, 250, 150, 250); // base
        if (errores > 1) g2d.drawLine(100, 250, 100, 50); // poste
        if (errores > 2) g2d.drawLine(100, 50, 180, 50);  // travesaño
        if (errores > 3) g2d.drawLine(180, 50, 180, 80);  // soga
        if (errores > 4) g2d.drawOval(170, 80, 20, 20);   // cabeza
        if (errores > 5) g2d.drawLine(180, 100, 180, 150); // cuerpo

        g2d.dispose();
        return new ImageIcon(img);
    }

    private void actualizarImagen() {
        if (juego.juegoGanado()) {
            lblImagen.setIcon(imagenesAhorcado[7]);
        } else {
            int errores = juego.getErrores();
            lblImagen.setIcon(imagenesAhorcado[errores]);
        }
    }

    private void procesarIntento() {
        String entrada = txtLetra.getText().toUpperCase();
        if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Introduce solo una letra.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        char letra = entrada.charAt(0);
        boolean acierto = juego.adivinarLetra(letra);

        lblProgreso.setText(juego.getProgreso());
        lblLetrasUsadas.setText("Letras usadas: " + juego.getLetrasUsadas());
        actualizarImagen();


        if (juego.juegoTerminado()) {
            boolean partidaGanada = juego.juegoGanado();


            if (!modoDosJugadores && Sesion.hayUsuarioLogueado()) {
                ConexionBD conexionBD = new ConexionBD();
                RankingDAO rankingDAO = new RankingDAO(conexionBD.getConnection());
                rankingDAO.actualizarRanking(Sesion.getUsuarioActual().getId(), partidaGanada);
            }

            String mensaje = partidaGanada ?
                    (modoDosJugadores ? "¡Jugador 2 ganó!" : "¡Ganaste!") :
                    (modoDosJugadores ? "¡Jugador 2 perdió!" : "¡Perdiste!");

            JOptionPane.showMessageDialog(this, mensaje + "\nLa palabra era: " + juego.getPalabraSecreta(),
                    "Fin del juego", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new JuegoNuevo();
        }

        txtLetra.setText("");
        txtLetra.requestFocus();
    }
}