package modelo;

/**
 * La clase {@code Sesion} gestiona la sesión actual del usuario.
 * Utiliza un atributo estático para mantener una única sesión activa durante la ejecución.
 */
public class Sesion {
    private static Usuario usuarioActual;

    /**
     * Inicia una sesión con el usuario proporcionado.
     *
     * @param usuario el objeto {@link Usuario} que inicia sesión.
     */
    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    /**
     * Cierra la sesión actual, eliminando la referencia al usuario.
     */
    public static void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Devuelve el usuario actualmente logueado en el sistema.
     *
     * @return el objeto {@link Usuario} que está en sesión o {@code null} si no hay sesión activa.
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Verifica si hay un usuario actualmente logueado.
     *
     * @return {@code true} si hay un usuario en sesión, {@code false} en caso contrario.
     */
    public static boolean hayUsuarioLogueado() {
        return usuarioActual != null;
    }
}
