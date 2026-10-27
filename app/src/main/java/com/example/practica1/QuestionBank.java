package com.example.practica1;

import java.util.ArrayList;
import java.util.List;

public class BancoPreguntas {

    public static List<Pregunta> obtener() {
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta(
                "Pregunta 1 (texto con opciones de texto)",
                0,
                new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                null,
                0));
        preguntas.add(new Pregunta(
                "Pregunta 2 (texto con opciones de texto)",
                0,
                new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                null,
                2));
        preguntas.add(new Pregunta(
                "Pregunta 3 (imagen con opciones de texto)",
                R.drawable.placeholder,
                new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                null,
                1));
        preguntas.add(new Pregunta(
                "Pregunta 4 (imagen con opciones de texto)",
                R.drawable.placeholder,
                new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                null,
                3));
        preguntas.add(new Pregunta(
                "Pregunta 5 (texto con opciones de imagen)",
                0,
                null,
                new int[]{R.drawable.placeholder, R.drawable.placeholder, R.drawable.placeholder, R.drawable.placeholder},
                1));
        return preguntas;
    }
}