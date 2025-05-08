package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class fjuego extends JFrame {
    private ahorcado juego;
    private JLabel lblProgreso, lblIntentos;
    private JTextField txtLetra;
    private JButton btnAdivinar;

    public fjuego(String palabra) {
        juego = new ahorcado(palabra, 6); // puedes cambiar los intentos aquí

        setTitle("Juego del Ahorcado");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblProgreso = new JLabel(juego.getProgreso(), SwingConstants.CENTER);
        lblProgreso.setFont(new Font("Monospaced", Font.BOLD, 24));

        lblIntentos = new JLabel("Intentos restantes: " + juego.getIntentosRestantes(), SwingConstants.CENTER);

        txtLetra = new JTextField(1);
        btnAdivinar = new JButton("Adivinar");

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Letra:"));
        panelEntrada.add(txtLetra);
        panelEntrada.add(btnAdivinar);

        add(lblProgreso, BorderLayout.NORTH);
        add(lblIntentos, BorderLayout.CENTER);
        add(panelEntrada, BorderLayout.SOUTH);

        btnAdivinar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String entrada = txtLetra.getText().toUpperCase();
                if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                    JOptionPane.showMessageDialog(null, "Introduce solo una letra.");
                    return;
                }

                char letra = entrada.charAt(0);
                boolean acierto = juego.adivinarLetra(letra);

                lblProgreso.setText(juego.getProgreso());
                lblIntentos.setText("Intentos restantes: " + juego.getIntentosRestantes());

                if (juego.juegoTerminado()) {
                    if (juego.juegoGanado()) {
                        JOptionPane.showMessageDialog(null, "¡Ganaste! La palabra era: " + juego.getPalabraSecreta());
                    } else {
                        JOptionPane.showMessageDialog(null, "¡Perdiste! La palabra era: " + juego.getPalabraSecreta());
                    }
                    dispose(); // cerrar ventana
                    new JuegoNuevo(); // volver a iniciar
                }

                txtLetra.setText("");
                txtLetra.requestFocus();
            }
        });

        setVisible(true);
    }
}
