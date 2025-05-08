package vista;
public class ahorcado {
    private String palabraSecreta;
    private StringBuilder progreso;
    private int intentosRestantes;

    public ahorcado(String palabraSecreta, int intentos) {
        this.palabraSecreta = palabraSecreta.toUpperCase();
        this.intentosRestantes = intentos;
        this.progreso = new StringBuilder();

        for (int i = 0; i < palabraSecreta.length(); i++) {
            progreso.append("_");
        }
    }

    public boolean adivinarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        boolean acertado = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                progreso.setCharAt(i, letra);
                acertado = true;
            }
        }

        if (!acertado) {
            intentosRestantes--;
        }

        return acertado;
    }

    public boolean juegoTerminado() {
        return intentosRestantes <= 0 || progreso.toString().equals(palabraSecreta);
    }

    public boolean juegoGanado() {
        return progreso.toString().equals(palabraSecreta);
    }

    public String getProgreso() {
        return progreso.toString();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }
}
