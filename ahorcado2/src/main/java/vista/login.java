package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.ConexionBD;
import modelo.Sesion;
import modelo.Usuario;

/**
 * Clase que representa la ventana de inicio de sesión del juego Ahorcado.
 * Permite al usuario ingresar sus credenciales para acceder al sistema.
 * Si el usuario es administrador, se abrirá la interfaz de administrador,
 * de lo contrario, se abrirá el menú principal.
 */
public class login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    /**
     * Constructor que inicializa la ventana de inicio de sesión
     * con los campos, botones y funcionalidades necesarias.
     */
    public login() {
        setTitle("Ahorcado - Iniciar Sesión");
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLogin = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 50));
        lblLogin.setForeground(Color.BLACK);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblLogin, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JCheckBox chkRecordar = new JCheckBox("Recordar contraseña");
        chkRecordar.setFont(new Font("Arial", Font.PLAIN, 14));
        chkRecordar.setBackground(new Color(154, 227, 170));
        add(chkRecordar, gbc);

        gbc.gridy = 4;
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setPreferredSize(new Dimension(250, 50));
        btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnIniciarSesion, gbc);
        btnIniciarSesion.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            Usuario usuarioAutenticado = autenticarUsuario(usuario, password);
            if (usuarioAutenticado != null) {
                Sesion.iniciarSesion(usuarioAutenticado);
                dispose();

                if (usuarioAutenticado.esAdministrador()) {
                    new administrador();
                } else {
                    new MenuPrincipal();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                        "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 5;
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(250, 50));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnCancelar, gbc);
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    /**
     * Valida las credenciales del usuario contra la base de datos.
     * Este método realiza una consulta directa concatenando los parámetros,
     * lo que no es seguro frente a ataques de inyección SQL.
     * Se recomienda usar {@link #autenticarUsuario(String, String)} que usa consultas preparadas.
     *
     * @param usuario Nombre de usuario a validar.
     * @param password Contraseña asociada al usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    private boolean validarCredenciales(String usuario, String password) {
        ConexionBD conexion = new ConexionBD();
        try {
            String query = "SELECT * FROM usuarios WHERE username = '" + usuario +
                    "' AND password = '" + password + "'";
            ResultSet rs = conexion.getQuery(query);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Autentica al usuario usando una consulta preparada para mayor seguridad
     * y retorna un objeto {@link Usuario} con la información si la autenticación es exitosa.
     *
     * @param usuario Nombre de usuario.
     * @param password Contraseña del usuario.
     * @return Un objeto {@link Usuario} si la autenticación es correcta, null en caso contrario.
     */
    private Usuario autenticarUsuario(String usuario, String password) {
        ConexionBD conexion = new ConexionBD();
        try {
            String query = "SELECT u.id_usuario, u.username, u.tipo_cuenta " +
                    "FROM usuarios u " +
                    "LEFT JOIN administradores a ON u.id_usuario = a.id_admin " +
                    "WHERE u.username = ? AND u.password = ?";

            PreparedStatement pstmt = conexion.getConnection().prepareStatement(query);
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Usuario user = new Usuario(rs.getInt("id_usuario"), rs.getString("username"));
                user.setTipoCuenta(rs.getString("tipo_cuenta"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
