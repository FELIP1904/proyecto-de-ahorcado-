package vista;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class finicial extends JFrame{

    public finicial() {
        setTitle("Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        getContentPane().setBackground(new Color(154, 227, 170)); 
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Ahorcado");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 80));
        gbc.gridy = 0;
        add(titulo, gbc);
       

        JButton btnIniciarSesion = new JButton("Iniciar sesión");
        btnIniciarSesion.setPreferredSize(new Dimension(250, 50));
        btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        add(btnIniciarSesion, gbc);
        btnIniciarSesion.addActionListener(e -> {
            new login(); 
        });

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setPreferredSize(new Dimension(250, 50));
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnRegistrarse, gbc);
        btnRegistrarse.addActionListener(e -> {
            new Registrase(); 
        });
        
        JButton btnbaseJuego = new JButton("Base de juego");
        btnbaseJuego.setPreferredSize(new Dimension(250, 50));
         btnbaseJuego.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        add(btnbaseJuego, gbc);
        btnbaseJuego.addActionListener(e -> {
            new Basejuego();
        });


        JButton btnsalir = new JButton("Salir");
        btnsalir.setPreferredSize(new Dimension(250, 50));
        btnsalir.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 4;
        add(btnsalir, gbc);
        btnsalir.addActionListener(e -> {
            System.exit(0);
        });
        
        setVisible(true);

        
    }
}

