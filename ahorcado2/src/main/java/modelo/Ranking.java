package modelo;

public class Ranking {
    private String username;
    private int puntuacion;
    private int partidasGanadas;
    private int partidasJugadas;

    public Ranking(String username, int puntuacion, int partidasGanadas, int partidasJugadas) {
        this.username = username;
        this.puntuacion = puntuacion;
        this.partidasGanadas = partidasGanadas;
        this.partidasJugadas = partidasJugadas;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public double getPorcentajeVictorias() {
        if (partidasJugadas == 0) return 0;
        return (double) partidasGanadas / partidasJugadas * 100;
    }
}