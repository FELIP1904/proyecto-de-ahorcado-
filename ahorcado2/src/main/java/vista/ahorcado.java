package vista;

public class ahorcado {
    private String palabraSecreta;
    private StringBuilder progreso;
    private int intentosRestantes;
    private int intentosTotales;
    private StringBuilder letrasUsadas;

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

    public boolean juegoTerminado() {
        return intentosRestantes <= 0 || juegoGanado();
    }

    public boolean juegoGanado() {
        return progreso.toString().equalsIgnoreCase(palabraSecreta);
    }

    public String getProgreso() {
        return progreso.toString();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public int getIntentosTotales() {
        return intentosTotales;
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public String getLetrasUsadas() {
        return letrasUsadas.toString();
    }

    public int getErrores() {
        return intentosTotales - intentosRestantes;
    }
}