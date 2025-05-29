package vista;

import java.awt.*;
import javax.swing.*;
import modelo.ConexionBD;

/**
 * Ventana para el registro de nuevos usuarios en el sistema.
 * Permite ingresar datos personales, credenciales y tipo de cuenta.
 */
public class Registrase extends JFrame {

    private JTextField txtNombre, txtApellido, txtEmail, txtUsername;
    private JPasswordField txtPassword, txtConfirmar;
    private JRadioButton normal, premium;

    /**
     * Constructor que inicializa la interfaz gráfica del formulario de registro.
     * Configura los campos, botones y sus acciones.
     */
    public Registrase() {
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("REGISTRO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        add(txtNombre, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        add(txtApellido, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        add(txtEmail, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        add(new JLabel("Confirmar:"), gbc);
        gbc.gridx = 1;
        txtConfirmar = new JPasswordField(15);
        add(txtConfirmar, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        add(new JLabel("Tipo de cuenta:"), gbc);

        gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grupo = new ButtonGroup();
        normal = new JRadioButton("Normal", true);
        premium = new JRadioButton("Premium");
        normal.setBackground(new Color(154, 227, 170));
        premium.setBackground(new Color(154, 227, 170));
        radioPanel.setBackground(new Color(154, 227, 170));
        grupo.add(normal);
        grupo.add(premium);
        radioPanel.add(normal);
        radioPanel.add(premium);
        add(radioPanel, gbc);

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        JButton btnRegistrar = new JButton("Registrar");
        add(btnRegistrar, gbc);
        btnRegistrar.addActionListener(e -> {
            if (validarCampos()) {
                if (registrarUsuario()) {
                    dispose();
                    new MenuPrincipal();
                }
            }
        });

        gbc.gridy = 9;
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    /**
     * Valida que todos los campos estén completos y que las contraseñas coincidan.
     *
     * @return true si la validación es correcta, false en caso contrario.
     */
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtUsername.getText().isEmpty() ||
                txtPassword.getPassword().length == 0 || txtConfirmar.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new String(txtPassword.getPassword()).equals(new String(txtConfirmar.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Inserta un nuevo usuario en la base de datos con los datos del formulario.
     *
     * @return true si el registro fue exitoso, false en caso de error.
     */
    private boolean registrarUsuario() {
        ConexionBD conexion = new ConexionBD();
        try {
            String tipoCuenta = normal.isSelected() ? "normal" : "premium";
            String query = "INSERT INTO usuarios (username, password, nombre, apellido, email, tipo_cuenta) " +
                    "VALUES ('" + txtUsername.getText() + "', '" +
                    new String(txtPassword.getPassword()) + "', '" +
                    txtNombre.getText() + "', '" + txtApellido.getText() + "', '" +
                    txtEmail.getText() + "', '" + tipoCuenta + "')";

            int resultado = conexion.updateQuery(query);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
