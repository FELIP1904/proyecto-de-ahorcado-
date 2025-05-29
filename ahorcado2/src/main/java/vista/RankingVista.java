package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana que muestra el ranking de jugadores con sus puntuaciones y estadísticas.
 */
public class RankingVista extends JFrame {

    /**
     * Constructor que configura y muestra la ventana con la tabla del ranking.
     * Obtiene los datos desde la base de datos a través de RankingDAO.
     */
    public RankingVista() {
        setTitle("Ranking de Jugadores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new BorderLayout());

        // Obtener lista de rankings desde la base de datos
        ConexionBD conexionBD = new ConexionBD();
        RankingDAO rankingDAO = new RankingDAO(conexionBD.getConnection());
        List<Ranking> ranking = rankingDAO.obtenerRanking();

        // Definir nombres de columnas para la tabla
        String[] columnNames = {"Posición", "Usuario", "Puntuación", "Partidas Ganadas", "Partidas Jugadas", "% Victorias"};

        // Crear arreglo de datos para la tabla a partir de la lista de rankings
        Object[][] data = new Object[ranking.size()][6];
        for (int i = 0; i < ranking.size(); i++) {
            Ranking r = ranking.get(i);
            data[i][0] = i + 1; // posición en el ranking
            data[i][1] = r.getUsername();
            data[i][2] = r.getPuntuacion();
            data[i][3] = r.getPartidasGanadas();
            data[i][4] = r.getPartidasJugadas();
            data[i][5] = String.format("%.1f%%", r.getPorcentajeVictorias());
        }

        // Crear tabla con los datos y configuración visual
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false); // tabla no editable
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);

        // Añadir la tabla dentro de un scrollpane para manejar contenido grande
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para cerrar la ventana
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(154, 227, 170));
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
