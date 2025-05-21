package principal;
import javax.swing.*;

import modelo.BackupThread;
import vista.finicial;


public class menu extends JFrame {
    public static void main(String[] args) {
        // Iniciar el hilo de copia de seguridad
        BackupThread backup = new BackupThread();
        backup.start();

        // Lanzar la ventana inicial
        SwingUtilities.invokeLater(() -> new finicial());
    }
}