package com.example.practica1;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(
                "Pregunta 1: ¿Cuál es un pez?",
                0,
                new String[]{"Tiburón", "Cangrejo", "Ballena", "Rana"},
                null,
                0,
                Question.ControlType.RADIO_GROUP));
        questions.add(new Question(
                "Pregunta 2: ¿Cuál es más grande?",
                0,
                new String[]{"Puma", "León", "Tigre", "Leopardo"},
                null,
                2,
                Question.ControlType.RADIO_GROUP));
        questions.add(new Question(
                "Pregunta 3: ¿Cómo se llama este animal?",
                R.drawable.garza,
                new String[]{"Ganso", "Oca", "Cisne", "Garza"},
                null,
                3,
                Question.ControlType.LIST_VIEW));
        questions.add(new Question(
                "Pregunta 4: (imagen con opciones de texto)",
                R.drawable.placeholder,
                new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                null,
                3,
                Question.ControlType.RADIO_GROUP));
        questions.add(new Question(
                "Pregunta 5: ¿Cuál de estos NO es omnívoro?",
                0,
                null,
                new int[]{R.drawable.oso_negro, R.drawable.oso_hormiguero, R.drawable.oso_pardo, R.drawable.panda_rojo},
                1,
                Question.ControlType.IMAGE_BUTTONS));
        return questions;
    }
}