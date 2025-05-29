package modelo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La clase {@code BackupThread} extiende de {@link Thread} y se encarga de generar copias
 * de seguridad automáticas de la base de datos {@code ahorcado_db} cada 30 minutos.
 * <p>
 * Utiliza el comando {@code mysqldump} para realizar los respaldos y guarda los archivos
 * con marcas de tiempo en una carpeta específica.
 * </p>
 *
 * <p><b>Advertencia:</b> El nombre de usuario y contraseña están codificados directamente
 * en el código, lo que puede representar un riesgo de seguridad.</p>
 */
public class BackupThread extends Thread {

    /** Nombre de usuario para acceder a la base de datos. */
    private final String username = "root";

    /** Contraseña del usuario para acceder a la base de datos. */
    private final String password = "abcd1234";

    /** Nombre de la base de datos a respaldar. */
    private final String dbName = "ahorcado_db";

    /** Ruta del directorio donde se almacenarán las copias de seguridad. */
    private final String backupFolderPath = "copia_seguridad";

    /**
     * Método principal del hilo que ejecuta el respaldo periódico de la base de datos.
     * <p>
     * El método crea un directorio para almacenar los respaldos si no existe,
     * y luego, en un ciclo infinito, genera un nuevo archivo de respaldo cada 30 minutos.
     * </p>
     *
     * <p>
     * Cada respaldo se realiza mediante la herramienta {@code mysqldump} y se guarda
     * con un nombre único basado en la fecha y hora actual.
     * </p>
     */
    @Override
    public void run() {
        File backupDir = new File(backupFolderPath);
        if (!backupDir.exists()) {
            backupDir.mkdirs(); // Crear el directorio si no existe.
        }

        while (true) {
            try {
                // Generar nombre del archivo con timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File backupFile = new File(backupDir, "backup_ahorcado_" + timestamp + ".sql");

                // Comando para realizar el backup con mysqldump
                String command = String.format("mysqldump -u%s -p%s %s > \"%s\"",
                        username, password, dbName, backupFile.getAbsolutePath());

                // Ejecutar el comando en el sistema
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                // Esperar a que finalice el proceso
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Copia de seguridad creada: " + backupFile.getAbsolutePath());
                } else {
                    System.err.println("Error al ejecutar mysqldump (código " + exitCode + ")");
                }

                // Esperar 30 minutos (1800000 milisegundos)
                Thread.sleep(1800000);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Error en el hilo de respaldo: " + ex.getMessage());
            }
        }
    }
}
