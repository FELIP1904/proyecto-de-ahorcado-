package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La clase {@code ConexionBD} permite establecer una conexión con una base de datos MySQL
 * y ejecutar consultas o actualizaciones SQL.
 * <p>
 * Está diseñada para conectarse a la base de datos {@code ahorcado_db} ubicada en {@code localhost}.
 * </p>
 */
public class ConexionBD {
    /** Nombre de usuario para la conexión a la base de datos. */
    private String usuario = "root";

    /** Contraseña para la conexión a la base de datos. */
    private String pwd = "abcd1234";

    /** Nombre de la base de datos. */
    private static String bd = "ahorcado_db";

    /** URL de conexión a la base de datos. */
    static String url = "jdbc:mysql://localhost:3306/" + bd;

    /** Objeto {@link Connection} que mantiene la conexión activa. */
    private Connection conn = null;

    /**
     * Constructor de la clase. Intenta establecer la conexión con la base de datos
     * al crear una instancia de la clase.
     */
    public ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, pwd);
            if (conn != null) {
                System.out.println("Conexión a base de datos " + url + " ... Ok");
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse a la base de datos " + url);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Retorna el objeto {@link Connection} actual.
     *
     * @return la conexión establecida a la base de datos.
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Ejecuta una consulta SQL de tipo {@code SELECT} y retorna un {@link ResultSet} con los resultados.
     *
     * @param _query la sentencia SQL de consulta.
     * @return un objeto {@link ResultSet} con los resultados de la consulta.
     */
    public ResultSet getQuery(String _query) {
        Statement state = null;
        ResultSet resultado = null;
        try {
            state = conn.createStatement();
            resultado = state.executeQuery(_query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Ejecuta una sentencia SQL de tipo {@code INSERT}, {@code UPDATE} o {@code DELETE}.
     *
     * @param _query la sentencia SQL a ejecutar.
     * @return el número de filas afectadas por la operación.
     */
    public int updateQuery(String _query) {
        Statement state = null;
        int resultado = 0;
        try {
            state = conn.createStatement();
            resultado = state.executeUpdate(_query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
