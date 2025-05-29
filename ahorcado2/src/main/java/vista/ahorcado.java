package vista;

/**
 * Clase que representa la lógica del juego del Ahorcado.
 * <p>
 * Maneja la palabra secreta, los intentos restantes, letras usadas
 * y el progreso actual del jugador.
 */
public class ahorcado {
    private String palabraSecreta;
    private StringBuilder progreso;
    private int intentosRestantes;
    private int intentosTotales;
    private StringBuilder letrasUsadas;

    /**
     * Constructor que inicializa el juego con una palabra secreta y un número de intentos.
     * Las letras de la palabra se ocultan con guiones bajos (_) inicialmente.
     *
     * @param palabraSecreta la palabra que el jugador debe adivinar.
     * @param intentos       el número total de intentos permitidos.
     */
    public ahorcado(String palabraSecreta, int intentos) {
        this.palabraSecreta = palabraSecreta.toUpperCase();
        this.intentosRestantes = intentos;
        this.intentosTotales = intentos;
        this.progreso = new StringBuilder();
        this.letrasUsadas = new StringBuilder();

        for (int i = 0; i < palabraSecreta.length(); i++) {
            char c = palabraSecreta.charAt(i);
            if (Character.isLetter(c)) {
                progreso.append("_");
            } else {
                progreso.append(c);
            }
        }
    }

    /**
     * Procesa un intento de adivinar una letra.
     *
     * @param letra la letra que el jugador intenta adivinar.
     * @return {@code true} si la letra está en la palabra secreta, {@code false} si ya fue usada o no está.
     */
    public boolean adivinarLetra(char letra) {
        letra = Character.toUpperCase(letra);

        if (letrasUsadas.indexOf(String.valueOf(letra)) != -1) {
            return false;
        }

        letrasUsadas.append(letra).append(" ");
        boolean acertado = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (Character.toUpperCase(palabraSecreta.charAt(i)) == letra) {
                progreso.setCharAt(i, palabraSecreta.charAt(i));
                acertado = true;
            }
        }

        if (!acertado) {
            intentosRestantes--;
        }

        return acertado;
    }

    /**
     * Verifica si el juego ha terminado.
     *
     * @return {@code true} si se han agotado los intentos o si el jugador ganó; {@code false} en caso contrario.
     */
    public boolean juegoTerminado() {
        return intentosRestantes <= 0 || juegoGanado();
    }

    /**
     * Verifica si el jugador ha ganado el juego.
     *
     * @return {@code true} si el progreso coincide con la palabra secreta; {@code false} en caso contrario.
     */
    public boolean juegoGanado() {
        return progreso.toString().equalsIgnoreCase(palabraSecreta);
    }

    /**
     * Devuelve el progreso actual del jugador mostrando las letras adivinadas y los espacios ocultos.
     *
     * @return una cadena que representa el progreso del jugador.
     */
    public String getProgreso() {
        return progreso.toString();
    }

    /**
     * Obtiene la cantidad de intentos que le quedan al jugador.
     *
     * @return el número de intentos restantes.
     */
    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    /**
     * Obtiene el número total de intentos permitidos para el juego.
     *
     * @return el total de intentos iniciales.
     */
    public int getIntentosTotales() {
        return intentosTotales;
    }

    /**
     * Devuelve la palabra secreta que se debe adivinar.
     *
     * @return la palabra secreta.
     */
    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    /**
     * Devuelve una cadena con las letras que el jugador ya ha usado en el juego.
     *
     * @return las letras usadas separadas por espacios.
     */
    public String getLetrasUsadas() {
        return letrasUsadas.toString();
    }

    /**
     * Calcula el número de errores que ha cometido el jugador.
     *
     * @return el número de errores, es decir, la diferencia entre intentos totales e intentos restantes.
     */
    public int getErrores() {
        return intentosTotales - intentosRestantes;
    }
}
