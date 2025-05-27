package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private String usuario = "root";
    private String pwd = "abcd1234";
    private static String bd = "ahorcado_db";
    static String url = "jdbc:mysql://localhost:3306/" + bd;
    private Connection conn = null;

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

    public Connection getConnection() {
        return conn;
    }

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