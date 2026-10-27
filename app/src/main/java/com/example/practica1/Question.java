package com.example.practica1;

public class Pregunta {

    private final String texto;
    private final int imagen;
    private final String[] opcionesTexto;
    private final int[] opcionesImagen;
    private final int correcta;

    public Pregunta(String texto, int imagen, String[] opcionesTexto, int[] opcionesImagen, int correcta) {
        this.texto = texto;
        this.imagen = imagen;
        this.opcionesTexto = opcionesTexto;
        this.opcionesImagen = opcionesImagen;
        this.correcta = correcta;
    }

    public String getTexto() {
        return texto;
    }

    public int getImagen() {
        return imagen;
    }

    public String[] getOpcionesTexto() {
        return opcionesTexto;
    }

    public int[] getOpcionesImagen() {
        return opcionesImagen;
    }

    public int getCorrecta() {
        return correcta;
    }

    public boolean tieneImagen() {
        return imagen != 0;
    }

    public boolean opcionesSonImagenes() {
        return opcionesImagen != null;
    }
}