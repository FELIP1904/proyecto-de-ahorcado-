package vista;

import modelo.ConexionBD;
import modelo.PalabraDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class JuegoNuevo extends JFrame {
    private JComboBox<String> cbIdioma, cbCategoria, cbDificultad;

    public JuegoNuevo() {
        setTitle("Juego Nuevo - Ahorcado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
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

        // Selector de idioma
        JLabel lblIdioma = new JLabel("Idioma:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblIdioma, gbc);

        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.getConnection();
            PalabraDAO palabraDAO = new PalabraDAO(conexion);
            List<String> idiomas = palabraDAO.obtenerIdiomas();
            cbIdioma = new JComboBox<>(idiomas.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            cbIdioma = new JComboBox<>(new String[]{"Español"}); // Fallback
        }

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(cbIdioma, gbc);

        // Selector de categoría (dinámico)
        JLabel lblCategoria = new JLabel("Categoría:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblCategoria, gbc);

        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.getConnection();
            PalabraDAO palabraDAO = new PalabraDAO(conexion);
            List<String> categorias = palabraDAO.obtenerCategorias();
            cbCategoria = new JComboBox<>();
            cbCategoria.addItem("Todas las categorías");
            for (String cat : categorias) {
                cbCategoria.addItem(cat);
            }
            cbCategoria.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
            cbCategoria = new JComboBox<>(new String[]{"Todas las categorías"});
        }

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(cbCategoria, gbc);

        // Selector de dificultad
        JLabel lblDificultad = new JLabel("Dificultad:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblDificultad, gbc);

        cbDificultad = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        cbDificultad.insertItemAt("Todas las dificultades", 0);
        cbDificultad.setSelectedIndex(0);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(cbDificultad, gbc);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botonesPanel.setBackground(new Color(154, 227, 170));

        JButton btnOk = new JButton("OK");
        btnOk.setPreferredSize(new Dimension(100, 30));
        btnOk.addActionListener(e -> {
            int jugadores = (int) cbJugadores.getSelectedItem();
            String tipo = rbPalabras.isSelected() ? "palabra" : "frase";
            String idioma = (String) cbIdioma.getSelectedItem();
            String categoria = (String) cbCategoria.getSelectedItem();
            String dificultad = (String) cbDificultad.getSelectedItem();

            dispose();

            if (jugadores == 1) {
                String palabra = obtenerPalabraAleatoria(idioma,
                        categoria.equals("Todas las categorías") ? null : categoria,
                        dificultad.equals("Todas las dificultades") ? null : dificultad,
                        tipo);

                if (palabra != null) {
                    new fjuego(palabra, false);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontraron palabras con esos parámetros",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    new JuegoNuevo();
                }
            } else {
                new jinicio(true);
            }
        });

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        botonesPanel.add(btnOk);
        botonesPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(botonesPanel, gbc);

        setVisible(true);
    }

    private String obtenerPalabraAleatoria(String idioma, String categoria, String dificultad, String tipo) {
        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.getConnection();
            PalabraDAO palabraDAO = new PalabraDAO(conexion);

            int idIdioma = palabraDAO.obtenerIdIdioma(idioma);
            Integer idCategoria = categoria != null ? palabraDAO.obtenerIdCategoria(categoria) : null;

            String dif = null;
            if (dificultad != null) {
                switch (dificultad) {
                    case "Fácil": dif = "facil"; break;
                    case "Medio": dif = "medio"; break;
                    case "Difícil": dif = "dificil"; break;
                }
            }

            List<String> palabras = palabraDAO.obtenerPalabras(idIdioma, idCategoria, dif, tipo);

            if (!palabras.isEmpty()) {
                return palabras.get((int) (Math.random() * palabras.size()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
