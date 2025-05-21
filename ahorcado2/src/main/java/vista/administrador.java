package vista;

import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import modelo.ConexionBD;

public class administrador extends JFrame {

    public administrador() {
        setTitle("Administrador - Ahorcado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Administrador");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 60));
        gbc.gridy = 0;
        add(titulo, gbc);

        JButton btnBackup = new JButton("Copia de seguridad");
        btnBackup.setPreferredSize(new Dimension(500, 50));
        btnBackup.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        add(btnBackup, gbc);


        JButton btnAgregarFrases = new JButton("Agregar frases y palabras");
        btnAgregarFrases.setPreferredSize(new Dimension(500, 50));
        btnAgregarFrases.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnAgregarFrases, gbc);

        JButton btnAddCategoria = new JButton("Agregar nueva categoría");
        btnAddCategoria.setPreferredSize(new Dimension(500, 50));
        btnAddCategoria.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        add(btnAddCategoria, gbc);
        gbc.gridy = 4; // Mover el resto hacia abajo

        JButton btnEditIdioma = new JButton("Edición de idioma");
        btnEditIdioma.setPreferredSize(new Dimension(500, 50));
        btnEditIdioma.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 4;
        add(btnEditIdioma, gbc);

        JButton btnAddAdmin = new JButton("Agregar nuevo administrador");
        btnAddAdmin.setPreferredSize(new Dimension(500, 50));
        btnAddAdmin.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 5;
        add(btnAddAdmin, gbc);

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(500, 50));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 6;
        add(btnCerrarSesion, gbc);

        // Función para el botón de Copia de seguridad
        btnBackup.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar destino para la copia de seguridad");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File destinationDir = fileChooser.getSelectedFile();
                String outputFile = "backup_ahorcado_" + System.currentTimeMillis() + ".sql";
                File backupFile = new File(destinationDir, outputFile);

                String username = "root";
                String password = "abcd1234";
                String dbName = "ahorcado_db";

                // Comando mysqldump
                String command = String.format("mysqldump -u%s -p%s %s", username, password, dbName);

                try {
                    ProcessBuilder pb = new ProcessBuilder("bash", "-c", command + " > \"" + backupFile.getAbsolutePath() + "\"");
                    pb.redirectErrorStream(true);
                    Process process = pb.start();

                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        JOptionPane.showMessageDialog(this, "Copia de seguridad creada en:\n" + backupFile.getAbsolutePath());
                        registrarBackup(backupFile.getName(), backupFile.length());
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al ejecutar mysqldump (código " + exitCode + ")", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | InterruptedException ex) {
                    JOptionPane.showMessageDialog(this, "Error al crear la copia de seguridad:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnAgregarFrases.addActionListener(e -> {
            new EditorFrasesWindow();
        });


        btnEditIdioma.addActionListener(e -> {
            String listaIdiomas = obtenerListaIdiomas();

            JPanel panel = new JPanel(new BorderLayout());

            JTextArea areaIdiomas = new JTextArea(listaIdiomas);
            areaIdiomas.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(areaIdiomas);
            panel.add(scrollPane, BorderLayout.NORTH);

            JPanel panelNuevo = new JPanel(new GridLayout(0, 1));
            JTextField nombreField = new JTextField();
            JTextField codigoField = new JTextField();

            panelNuevo.add(new JLabel("Nombre del nuevo idioma:"));
            panelNuevo.add(nombreField);
            panelNuevo.add(new JLabel("Código (ej. es, en, fr):"));
            panelNuevo.add(codigoField);

            panel.add(panelNuevo, BorderLayout.CENTER);

            String[] opciones = {"Agregar", "Cancelar"};

            int resultado = JOptionPane.showOptionDialog(
                    this,
                    panel,
                    "Gestión de Idiomas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (resultado == 0) {
                String nombre = nombreField.getText().trim();
                String codigo = codigoField.getText().trim();

                if (!nombre.isEmpty() && !codigo.isEmpty()) {
                    if (agregarIdioma(nombre, codigo)) {
                        JOptionPane.showMessageDialog(this,
                                "Idioma agregado exitosamente: " + nombre,
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Error al agregar el idioma",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe completar todos los campos",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnAddCategoria.addActionListener(e -> {
            JTextField nombreField = new JTextField();

            Object[] mensaje = {
                    "Nombre de la nueva categoría:", nombreField
            };

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    "Agregar categoría",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (opcion == JOptionPane.OK_OPTION) {
                String nombreCategoria = nombreField.getText().trim();
                if (!nombreCategoria.isEmpty()) {
                    ConexionBD conexion = new ConexionBD();
                    try {
                        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
                        PreparedStatement stmt = conexion.getConnection().prepareStatement(sql);
                        stmt.setString(1, nombreCategoria);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Categoría agregada correctamente.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al agregar la categoría:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El nombre de la categoría no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        btnAddAdmin.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            Object[] message = {
                    "Nombre de usuario:", usernameField,
                    "Contraseña:", passwordField
            };

            int option = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Registrar nuevo administrador",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (registrarNuevoAdministrador(username, password)) {
                    JOptionPane.showMessageDialog(this, "Administrador registrado:\nUsuario: " + username);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new finicial();
        });

        setVisible(true);
    }

    private boolean registrarNuevoAdministrador(String username, String password) {
        ConexionBD conexion = new ConexionBD();
        try {

            String verificarSql = "SELECT id_usuario FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement verificarStmt = conexion.getConnection().prepareStatement(verificarSql);
            verificarStmt.setString(1, username);
            verificarStmt.setString(2, password);
            ResultSet rs = verificarStmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");


                String verificarAdminSql = "SELECT COUNT(*) FROM administradores WHERE id_admin = ?";
                PreparedStatement verificarAdminStmt = conexion.getConnection().prepareStatement(verificarAdminSql);
                verificarAdminStmt.setInt(1, idUsuario);
                ResultSet rsAdmin = verificarAdminStmt.executeQuery();
                if (rsAdmin.next() && rsAdmin.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "El usuario ya es administrador.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return false;
                }


                String insertSql = "INSERT INTO administradores (id_admin) VALUES (?)";
                PreparedStatement insertStmt = conexion.getConnection().prepareStatement(insertSql);
                insertStmt.setInt(1, idUsuario);
                int filasAfectadas = insertStmt.executeUpdate();

                if (filasAfectadas > 0) {

                    String updateSql = "UPDATE usuarios SET tipo_cuenta = 'admin' WHERE id_usuario = ?";
                    PreparedStatement updateStmt = conexion.getConnection().prepareStatement(updateSql);
                    updateStmt.setInt(1, idUsuario);
                    updateStmt.executeUpdate();
                    return true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion.getConnection() != null) {
                    conexion.getConnection().close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    private boolean agregarIdioma(String nombre, String codigo) {
        ConexionBD conexion = new ConexionBD();
        try {
            String verificarSql = "SELECT COUNT(*) FROM idiomas WHERE nombre = ? OR codigo = ?";
            PreparedStatement verificarStmt = conexion.getConnection().prepareStatement(verificarSql);
            verificarStmt.setString(1, nombre);
            verificarStmt.setString(2, codigo);
            ResultSet rs = verificarStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "El idioma o código ya existe", "Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            String insertSql = "INSERT INTO idiomas (nombre, codigo) VALUES (?, ?)";
            PreparedStatement insertStmt = conexion.getConnection().prepareStatement(insertSql);
            insertStmt.setString(1, nombre);
            insertStmt.setString(2, codigo);

            int filasAfectadas = insertStmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion.getConnection() != null) conexion.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String obtenerListaIdiomas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Idiomas existentes:\n");
        sb.append("------------------\n");

        ConexionBD conexion = new ConexionBD();
        try {
            String sql = "SELECT nombre, codigo FROM idiomas ORDER BY nombre";
            PreparedStatement stmt = conexion.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("nombre")).append(" (").append(rs.getString("codigo")).append(")\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error al cargar los idiomas existentes";
        } finally {
            try {
                if (conexion.getConnection() != null) conexion.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void registrarBackup(String nombreArchivo, long tamano) {
        ConexionBD conexion = new ConexionBD();
        try {
            String insertSql = "INSERT INTO backups (nombre_archivo, tamano, creado_por) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conexion.getConnection().prepareStatement(insertSql);
            insertStmt.setString(1, nombreArchivo);
            insertStmt.setLong(2, tamano);
            insertStmt.setInt(3, obtenerIdUsuarioActual());
            insertStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conexion.getConnection() != null) conexion.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private int obtenerIdUsuarioActual() {

        return 4;
    }

    private static class EditorFrasesWindow extends JFrame {
        private JTextArea textArea;
        private JComboBox<String> idiomaComboBox;
        private JComboBox<String> categoriaComboBox;
        private JComboBox<String> dificultadComboBox;
        private JComboBox<String> tipoContenidoComboBox;
        private Map<String, Integer> idiomasMap = new HashMap<>();
        private Map<String, Integer> categoriasMap = new HashMap<>();

        public EditorFrasesWindow() {
            setTitle("Agregar Contenido - Administrador");
            setSize(700, 650);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Panel superior para los controles de selección
            JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 5, 5));
            panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Selector de idioma
            panelSuperior.add(new JLabel("Idioma:"));
            idiomaComboBox = new JComboBox<>();
            cargarIdiomas();
            panelSuperior.add(idiomaComboBox);

            // Selector de tipo de contenido
            panelSuperior.add(new JLabel("Tipo de contenido:"));
            tipoContenidoComboBox = new JComboBox<>(new String[]{"Palabra", "Frase"});
            panelSuperior.add(tipoContenidoComboBox);

            // Selector de categoría
            panelSuperior.add(new JLabel("Categoría:"));
            categoriaComboBox = new JComboBox<>();
            cargarCategorias();
            panelSuperior.add(categoriaComboBox);

            // Selector de dificultad
            panelSuperior.add(new JLabel("Nivel de dificultad:"));
            dificultadComboBox = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
            panelSuperior.add(dificultadComboBox);

            add(panelSuperior, BorderLayout.NORTH);

            // Área de texto para el contenido
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane, BorderLayout.CENTER);

            // Panel de instrucciones
            JPanel panelInstrucciones = new JPanel();
            panelInstrucciones.setLayout(new BoxLayout(panelInstrucciones, BoxLayout.Y_AXIS));
            panelInstrucciones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel lblInstrucciones = new JLabel("<html><b>Instrucciones:</b><br>"
                    + "- Ingrese un elemento por línea<br>"
                    + "- Palabras: una sola palabra sin espacios<br>"
                    + "- Frases: deben tener al menos 3 palabras</html>");
            panelInstrucciones.add(lblInstrucciones);

            add(panelInstrucciones, BorderLayout.SOUTH);

            // Panel inferior con botón de guardar
            JPanel panelInferior = new JPanel();
            JButton btnGuardar = new JButton("Guardar contenido");
            btnGuardar.addActionListener(e -> {
                if (guardarContenido(textArea.getText())) {
                    JOptionPane.showMessageDialog(this, "Contenido agregado exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar contenido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelInferior.add(btnGuardar);
            add(panelInferior, BorderLayout.SOUTH);

            setVisible(true);
        }

        private void cargarIdiomas() {
            ConexionBD conexion = new ConexionBD();
            try {
                String sql = "SELECT id_idioma, nombre FROM idiomas ORDER BY nombre";
                PreparedStatement stmt = conexion.getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                idiomaComboBox.addItem("Seleccione un idioma");
                while (rs.next()) {
                    String nombreIdioma = rs.getString("nombre");
                    idiomasMap.put(nombreIdioma, rs.getInt("id_idioma"));
                    idiomaComboBox.addItem(nombreIdioma);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar idiomas", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (conexion.getConnection() != null) conexion.getConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void cargarCategorias() {
            ConexionBD conexion = new ConexionBD();
            try {
                String sql = "SELECT id_categoria, nombre FROM categorias ORDER BY nombre";
                PreparedStatement stmt = conexion.getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                categoriaComboBox.addItem("Seleccione una categoría");
                while (rs.next()) {
                    String nombreCategoria = rs.getString("nombre");
                    categoriasMap.put(nombreCategoria, rs.getInt("id_categoria"));
                    categoriaComboBox.addItem(nombreCategoria);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar categorías", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (conexion.getConnection() != null) conexion.getConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private boolean guardarContenido(String contenido) {
            // Validar selección de idioma
            if (idiomaComboBox.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un idioma", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Validar selección de categoría
            if (categoriaComboBox.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una categoría", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Obtener IDs seleccionados
            String idiomaSeleccionado = (String) idiomaComboBox.getSelectedItem();
            int idIdioma = idiomasMap.get(idiomaSeleccionado);

            String categoriaSeleccionada = (String) categoriaComboBox.getSelectedItem();
            int idCategoria = categoriasMap.get(categoriaSeleccionada);

            // Obtener tipo de contenido seleccionado
            String tipoContenido = (String) tipoContenidoComboBox.getSelectedItem();
            String tipoBD = tipoContenido.equalsIgnoreCase("Palabra") ? "palabra" : "frase";

            // Obtener nivel de dificultad seleccionado
            String dificultad = (String) dificultadComboBox.getSelectedItem();
            int nivelDificultad = convertirDificultadANumero(dificultad);

            // Validar contenido según el tipo
            String[] lineas = contenido.split("\\r?\\n");
            if (!validarContenidoSegunTipo(lineas, tipoContenido)) {
                return false;
            }

            ConexionBD conexion = new ConexionBD();
            try {
                String insertSql = "INSERT INTO palabras (contenido, tipo, id_idioma, id_categoria, dificultad) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conexion.getConnection().prepareStatement(insertSql);

                for (String linea : lineas) {
                    if (linea.trim().isEmpty()) continue;
                    insertStmt.setString(1, linea.trim());
                    insertStmt.setString(2, tipoBD);
                    insertStmt.setInt(3, idIdioma);
                    insertStmt.setInt(4, idCategoria);
                    insertStmt.setInt(5, nivelDificultad);
                    insertStmt.executeUpdate();
                }
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } finally {
                try {
                    if (conexion.getConnection() != null) conexion.getConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private boolean validarContenidoSegunTipo(String[] lineas, String tipoContenido) {
            for (String linea : lineas) {
                if (linea.trim().isEmpty()) continue;

                if (tipoContenido.equals("Palabra")) {
                    if (linea.trim().contains(" ")) {
                        JOptionPane.showMessageDialog(this,
                                "Error: '" + linea.trim() + "' no es una palabra válida.\n" +
                                        "Las palabras no deben contener espacios.",
                                "Error de validación",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else { // Frase
                    int countPalabras = linea.trim().split("\\s+").length;
                    if (countPalabras < 3) {
                        JOptionPane.showMessageDialog(this,
                                "Error: '" + linea.trim() + "' no es una frase válida.\n" +
                                        "Las frases deben tener al menos 3 palabras.",
                                "Error de validación",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            return true;
        }

        private int convertirDificultadANumero(String dificultad) {
            switch (dificultad) {
                case "Fácil":
                    return 1;
                case "Medio":
                    return 2;
                case "Difícil":
                    return 3;
                default:
                    return 1; // Por defecto fácil
            }
        }
    }
}



