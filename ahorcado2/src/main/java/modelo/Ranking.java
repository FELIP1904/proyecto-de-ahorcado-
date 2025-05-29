package modelo;

/**
 * La clase {@code Ranking} representa el rendimiento de un jugador en el juego del ahorcado.
 * Contiene información sobre su nombre de usuario, puntuación, partidas ganadas y partidas jugadas.
 */
public class Ranking {
    private String username;
    private int puntuacion;
    private int partidasGanadas;
    private int partidasJugadas;

    /**
     * Crea una nueva instancia de {@code Ranking} con los datos del jugador.
     *
     * @param username        el nombre de usuario del jugador.
     * @param puntuacion      la puntuación total obtenida por el jugador.
     * @param partidasGanadas la cantidad de partidas ganadas.
     * @param partidasJugadas la cantidad total de partidas jugadas.
     */
    public Ranking(String username, int puntuacion, int partidasGanadas, int partidasJugadas) {
        this.username = username;
        this.puntuacion = puntuacion;
        this.partidasGanadas = partidasGanadas;
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * Obtiene el nombre de usuario del jugador.
     *
     * @return el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene la puntuación total del jugador.
     *
     * @return la puntuación.
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Obtiene la cantidad de partidas ganadas por el jugador.
     *
     * @return partidas ganadas.
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Obtiene la cantidad total de partidas jugadas por el jugador.
     *
     * @return partidas jugadas.
     */
    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    /**
     * Calcula el porcentaje de partidas ganadas por el jugador.
     *
     * @return el porcentaje de victorias, o 0 si no ha jugado ninguna partida.
     */
    public double getPorcentajeVictorias() {
        if (partidasJugadas == 0) return 0;
        return (double) partidasGanadas / partidasJugadas * 100;
    }
}
