package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code PalabraDAO} es un objeto de acceso a datos (DAO) que permite
 * interactuar con la base de datos para obtener información relacionada con
 * palabras, idiomas y categorías en el contexto del juego del ahorcado.
 */
public class PalabraDAO {
    /** Conexión activa a la base de datos. */
    private Connection conexion;

    /**
     * Constructor que recibe una conexión a la base de datos.
     *
     * @param conexion la conexión {@link Connection} activa a la base de datos.
     */
    public PalabraDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene una lista de palabras filtradas por idioma, tipo, categoría (opcional)
     * y dificultad (opcional).
     *
     * @param idIdioma   ID del idioma.
     * @param idCategoria ID de la categoría (puede ser {@code null}).
     * @param dificultad Nivel de dificultad (puede ser {@code null}).
     * @param tipo       Tipo de palabra (ej. "frase", "letra").
     * @return una lista de palabras que cumplen con los filtros dados.
     */
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

    /**
     * Obtiene el ID del idioma a partir de su nombre.
     *
     * @param nombreIdioma el nombre del idioma (ej. "Español").
     * @return el ID del idioma si se encuentra, o {@code -1} si no.
     */
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

    /**
     * Obtiene el ID de una categoría a partir de su nombre.
     *
     * @param nombreCategoria el nombre de la categoría (ej. "Animales").
     * @return el ID de la categoría si se encuentra, o {@code -1} si no.
     */
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

    /**
     * Obtiene una lista con todos los nombres de idiomas disponibles en la base de datos.
     *
     * @return una lista de nombres de idiomas.
     */
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

    /**
     * Obtiene una lista con todos los nombres de categorías disponibles en la base de datos.
     *
     * @return una lista de nombres de categorías.
     */
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
