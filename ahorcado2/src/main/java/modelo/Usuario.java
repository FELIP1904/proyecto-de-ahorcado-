package modelo;

public class Usuario {
    private int id;
    private String username;
    private String tipoCuenta;

    public Usuario(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public boolean esAdministrador() {
        return "admin".equalsIgnoreCase(tipoCuenta);
    }
}