package vista;

import java.awt.*;
import javax.swing.*;

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

       
        JButton btnEditFrases = new JButton("Edición de frases y letras");
        btnEditFrases.setPreferredSize(new Dimension(500, 50));
        btnEditFrases.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnEditFrases, gbc);

  
        JButton btnEditIdioma = new JButton("Edición de idioma");
        btnEditIdioma.setPreferredSize(new Dimension(500, 50));
        btnEditIdioma.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        add(btnEditIdioma, gbc);

   
        JButton btnAddAdmin = new JButton("Agregar nuevo administrador");
        btnAddAdmin.setPreferredSize(new Dimension(500, 50));
        btnAddAdmin.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 4;
        add(btnAddAdmin, gbc);

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(500, 50));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 5;
        add(btnCerrarSesion, gbc);

        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new finicial(); 
        });

        setVisible(true);
    }

}