package modelo;

/**
 * La clase {@code Usuario} representa a un usuario del sistema.
 * Contiene información como el ID, nombre de usuario y tipo de cuenta.
 */
public class Usuario {
    private int id;
    private String username;
    private String tipoCuenta;

    /**
     * Crea un nuevo objeto {@code Usuario} con ID y nombre de usuario.
     *
     * @param id       el identificador único del usuario.
     * @param username el nombre de usuario.
     */
    public Usuario(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Devuelve el ID del usuario.
     *
     * @return el identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre de usuario.
     *
     * @return el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devuelve el tipo de cuenta del usuario (por ejemplo, "admin" o "usuario").
     *
     * @return el tipo de cuenta.
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * Establece el tipo de cuenta del usuario.
     *
     * @param tipoCuenta el tipo de cuenta (por ejemplo, "admin", "usuario").
     */
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * Verifica si el usuario tiene permisos de administrador.
     *
     * @return {@code true} si el tipo de cuenta es "admin", {@code false} en caso contrario.
     */
    public boolean esAdministrador() {
        return "admin".equalsIgnoreCase(tipoCuenta);
    }
}
