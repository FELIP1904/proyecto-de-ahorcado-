package vista;

import java.awt.*;
import javax.swing.*;

public class JuegoNuevo extends JFrame {

    public JuegoNuevo() {
        setTitle("Juego Nuevo - Ahorcado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("JUEGO NUEVO", SwingConstants.CENTER);
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        
        JPanel tipoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tipoPanel.setBackground(new Color(154, 227, 170));
        
        JRadioButton rbPalabras = new JRadioButton("Palabras");
        JRadioButton rbFrases = new JRadioButton("Frases");
        rbPalabras.setBackground(new Color(154, 227, 170));
        rbFrases.setBackground(new Color(154, 227, 170));
        tipoPanel.setBackground(new  Color(154, 227, 170));
        
        ButtonGroup tipoGroup = new ButtonGroup();
        tipoGroup.add(rbPalabras);
        tipoGroup.add(rbFrases);
        rbPalabras.setSelected(true);
        
        tipoPanel.add(rbPalabras);
        tipoPanel.add(rbFrases);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(tipoPanel, gbc);

        
        JLabel lblJugadores = new JLabel("Número de jugadores:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblJugadores, gbc);

        JComboBox<Integer> cbJugadores = new JComboBox<>(new Integer[]{1, 2});
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(cbJugadores, gbc);

      
        JLabel lblIdioma = new JLabel("Idioma:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblIdioma, gbc);

        JComboBox<String> cbIdioma = new JComboBox<>(new String[]{"Español", "Inglés", "Francés" , "Valeciano"});
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(cbIdioma, gbc);

        
        JLabel lblDificultad = new JLabel("Nivel de dificultad:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblDificultad, gbc);

        JComboBox<String> cbDificultad = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(cbDificultad, gbc);

   
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botonesPanel.setBackground(new Color(154, 227, 170));
        
        JButton btnOk = new JButton("OK");
        btnOk.setPreferredSize(new Dimension(100, 30));
       
        
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setPreferredSize(new Dimension(100, 30));
       
       
        
        btnCancel.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });
        
        botonesPanel.add(btnOk);
        botonesPanel.add(btnCancel);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(botonesPanel, gbc);

        setVisible(true);
    }

   
}