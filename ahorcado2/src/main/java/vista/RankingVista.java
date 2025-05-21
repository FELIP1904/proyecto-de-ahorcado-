package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingVista extends JFrame {
    public RankingVista() {
        setTitle("Ranking de Jugadores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(154, 227, 170));
        setLayout(new BorderLayout());


        ConexionBD conexionBD = new ConexionBD();
        RankingDAO rankingDAO = new RankingDAO(conexionBD.getConnection());
        List<Ranking> ranking = rankingDAO.obtenerRanking();

        // Crear modelo de tabla
        String[] columnNames = {"Posición", "Usuario", "Puntuación", "Partidas Ganadas", "Partidas Jugadas", "% Victorias"};
        Object[][] data = new Object[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            Ranking r = ranking.get(i);
            data[i][0] = i + 1;
            data[i][1] = r.getUsername();
            data[i][2] = r.getPuntuacion();
            data[i][3] = r.getPartidasGanadas();
            data[i][4] = r.getPartidasJugadas();
            data[i][5] = String.format("%.1f%%", r.getPorcentajeVictorias());
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(154, 227, 170));
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}