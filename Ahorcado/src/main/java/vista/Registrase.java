package vista;

import java.awt.*;
import javax.swing.*;

public class Registrase extends JFrame {

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
        add(new JTextField(15), gbc);
        
 
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        add(new JTextField(15), gbc);
        

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(new JTextField(15), gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(new JTextField(15), gbc);
        
    
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;    
        add(new JPasswordField(15), gbc);
        
    
        gbc.gridy = 6;
        gbc.gridx = 0;
        add(new JLabel("Confirmar:"), gbc);
        gbc.gridx = 1;
        add(new JPasswordField(15), gbc);

 
        gbc.gridy = 7;
        gbc.gridx = 0;
        add(new JLabel("Tipo de cuenta:"), gbc);
        
        gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grupo = new ButtonGroup();
        JRadioButton normal = new JRadioButton("Normal", true);
        JRadioButton premium = new JRadioButton("Premium");
        normal.setBackground(new Color(154, 227, 170));
        premium.setBackground(new Color(154, 227, 170));
        radioPanel.setBackground(new  Color(154, 227, 170));
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
            dispose();
            new MenuPrincipal(); 
        });
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);
        btnCancelar.addActionListener(e -> {
            dispose();
       
        });
        

        setVisible(true);
    }
}