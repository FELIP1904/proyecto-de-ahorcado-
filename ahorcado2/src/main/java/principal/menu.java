package principal;
import javax.swing.*;

import modelo.BackupThread;
import vista.finicial;


public class menu extends JFrame {
    public static void main(String[] args) {

        BackupThread backup = new BackupThread();
        backup.start();


        SwingUtilities.invokeLater(() -> new finicial());
    }
}