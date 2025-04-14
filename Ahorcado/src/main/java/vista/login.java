
package vista;

import javax.swing.*;
import java.awt.*;


public class login extends JFrame {

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
        JTextField txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        add(txtUsuario, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        JPasswordField txtPassword = new JPasswordField(20);
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
            dispose();
               
            new administrador();
        });
        gbc.gridy = 5;

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(250, 50));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnCancelar, gbc);
        btnCancelar.addActionListener(e -> {
            dispose();
       
        });
     
 

        setVisible(true);
    }


    
}
