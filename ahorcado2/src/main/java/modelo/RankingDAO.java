package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankingDAO {
    private Connection conexion;

    public RankingDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean actualizarRanking(int idUsuario, boolean partidaGanada) {
        String sqlSelect = "SELECT * FROM ranking WHERE id_usuario = ?";
        String sqlInsert = "INSERT INTO ranking (id_usuario, puntuacion, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE ranking SET puntuacion = ?, partidas_ganadas = ?, partidas_jugadas = ? WHERE id_usuario = ?";

        try {
            // Verificar si ya existe un registro para este usuario
            PreparedStatement pstmtSelect = conexion.prepareStatement(sqlSelect);
            pstmtSelect.setInt(1, idUsuario);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {

                int puntuacionActual = rs.getInt("puntuacion");
                int partidasGanadas = rs.getInt("partidas_ganadas");
                int partidasJugadas = rs.getInt("partidas_jugadas");


                int nuevaPuntuacion = puntuacionActual + (partidaGanada ? 10 : 0);
                int nuevasGanadas = partidasGanadas + (partidaGanada ? 1 : 0);
                int nuevasJugadas = partidasJugadas + 1;

                PreparedStatement pstmtUpdate = conexion.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, nuevaPuntuacion);
                pstmtUpdate.setInt(2, nuevasGanadas);
                pstmtUpdate.setInt(3, nuevasJugadas);
                pstmtUpdate.setInt(4, idUsuario);

                return pstmtUpdate.executeUpdate() > 0;
            } else {

                PreparedStatement pstmtInsert = conexion.prepareStatement(sqlInsert);
                pstmtInsert.setInt(1, idUsuario);
                pstmtInsert.setInt(2, partidaGanada ? 10 : 0);
                pstmtInsert.setInt(3, partidaGanada ? 1 : 0);
                pstmtInsert.setInt(4, 1);

                return pstmtInsert.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ranking> obtenerRanking() {
        List<Ranking> ranking = new ArrayList<>();
        String sql = "SELECT u.username, r.puntuacion, r.partidas_ganadas, r.partidas_jugadas " +
                "FROM ranking r JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                "ORDER BY r.puntuacion DESC LIMIT 10";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("username");
                int puntuacion = rs.getInt("puntuacion");
                int partidasGanadas = rs.getInt("partidas_ganadas");
                int partidasJugadas = rs.getInt("partidas_jugadas");

                ranking.add(new Ranking(username, puntuacion, partidasGanadas, partidasJugadas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }
}