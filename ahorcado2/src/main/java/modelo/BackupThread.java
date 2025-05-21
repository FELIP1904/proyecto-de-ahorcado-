package modelo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupThread extends Thread {

    private final String username = "root";
    private final String password = "abcd1234"; // ⚠️ Mejor usar desde config segura
    private final String dbName = "ahorcado_db";
    private final String backupFolderPath = "copia_seguridad"; // Ruta donde guardar backups

    @Override
    public void run() {
        File backupDir = new File(backupFolderPath);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        while (true) {
            try {
                // Fecha y hora para nombre de archivo
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File backupFile = new File(backupDir, "backup_ahorcado_" + timestamp + ".sql");

                // Comando mysqldump
                String command = String.format("mysqldump -u%s -p%s %s > \"%s\"",
                        username, password, dbName, backupFile.getAbsolutePath());

                ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Copia de seguridad creada: " + backupFile.getAbsolutePath());
                } else {
                    System.err.println(" Error al ejecutar mysqldump (código " + exitCode + ")");
                }


                Thread.sleep(1800000);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Error en el hilo de respaldo: " + ex.getMessage());
            }
        }
    }
}
