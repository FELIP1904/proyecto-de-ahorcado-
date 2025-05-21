package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalabraDAO {
    private Connection conexion;

    public PalabraDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<String> obtenerPalabras(int idIdioma, Integer idCategoria, String dificultad, String tipo) {
        List<String> palabras = new ArrayList<>();
        String sql = "SELECT contenido FROM palabras WHERE id_idioma = ? AND tipo = ?";

        if (idCategoria != null) {
            sql += " AND id_categoria = ?";
        }
        if (dificultad != null) {
            sql += " AND dificultad = ?";
        }

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idIdioma);
            pstmt.setString(2, tipo);

            int paramIndex = 3;
            if (idCategoria != null) {
                pstmt.setInt(paramIndex++, idCategoria);
            }
            if (dificultad != null) {
                pstmt.setString(paramIndex, dificultad.toLowerCase());
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                palabras.add(rs.getString("contenido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    public int obtenerIdIdioma(String nombreIdioma) {
        String sql = "SELECT id_idioma FROM idiomas WHERE nombre = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreIdioma);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_idioma");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int obtenerIdCategoria(String nombreCategoria) {
        String sql = "SELECT id_categoria FROM categorias WHERE nombre = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreCategoria);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_categoria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Método para obtener lista de idiomas
    public List<String> obtenerIdiomas() {
        List<String> idiomas = new ArrayList<>();
        String sql = "SELECT nombre FROM idiomas";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                idiomas.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idiomas;
    }

    // Método para obtener lista de categorías
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre FROM categorias";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
