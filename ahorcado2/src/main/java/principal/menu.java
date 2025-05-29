package principal;

import javax.swing.*;

import modelo.BackupThread;
import vista.finicial;

/**
 * Clase principal que inicia la aplicación del juego del ahorcado.
 * <p>
 * Inicia un hilo para realizar copias de seguridad periódicas de la base de datos
 * y lanza la ventana inicial de la interfaz gráfica.
 */
public class menu extends JFrame {

    /**
     * Método principal que arranca la aplicación.
     *
     * @param args argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        // Iniciar el hilo de respaldo automático de la base de datos.
        BackupThread backup = new BackupThread();
        backup.start();

        // Iniciar la interfaz gráfica en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> new finicial());
    }
}
