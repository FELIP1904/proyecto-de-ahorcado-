package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code RankingDAO} se encarga de gestionar el acceso a los datos
 * del ranking de jugadores en la base de datos.
 */
public class RankingDAO {
    private Connection conexion;

    /**
     * Crea un nuevo DAO de ranking utilizando la conexión proporcionada.
     *
     * @param conexion la conexión activa a la base de datos.
     */
    public RankingDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Actualiza el ranking del usuario en función del resultado de la partida.
     * Si el usuario no tiene un registro en la tabla {@code ranking}, se crea uno nuevo.
     * Si ya existe, se actualiza la puntuación, partidas ganadas y jugadas.
     *
     * @param idUsuario     el ID del usuario.
     * @param partidaGanada indica si el usuario ganó la partida.
     * @return {@code true} si se insertó o actualizó correctamente, {@code false} en caso de error.
     */
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
                // Usuario existente: actualizar datos
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
                // Usuario nuevo: insertar registro
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

    /**
     * Obtiene una lista de los 10 mejores jugadores ordenados por puntuación descendente.
     *
     * @return una lista de objetos {@link Ranking} representando el top 10 del ranking.
     */
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
