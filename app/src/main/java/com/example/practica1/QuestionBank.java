package com.example.practica1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.practica1.data.AppDatabase;
import com.example.practica1.data.QuestionDao;
import com.example.practica1.data.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private static final String SEPARATOR = "\\|";

    public interface Callback {
        void onLoaded(List<Question> questions);
    }

    public static void load(Context context, Callback callback) {
        Context appContext = context.getApplicationContext();
        AppDatabase.DATABASE_EXECUTOR.execute(() -> {
            QuestionDao dao = AppDatabase.getInstance(appContext).questionDao();
            if (dao.count() == 0) {
                dao.insertAll(getSeed());
            }
            List<Question> questions = new ArrayList<>();
            for (QuestionEntity entity : dao.getAll()) {
                questions.add(toQuestion(appContext, entity));
            }
            new Handler(Looper.getMainLooper()).post(() -> callback.onLoaded(questions));
        });
    }

    private static List<QuestionEntity> getSeed() {
        List<QuestionEntity> seed = new ArrayList<>();
        seed.add(new QuestionEntity(
                "Pregunta 1: ¿Cuál es un pez?",
                null,
                "Tiburón|Cangrejo|Ballena|Rana",
                null,
                0,
                Question.ControlType.RADIO_GROUP.name()));
        seed.add(new QuestionEntity(
                "Pregunta 2: ¿Cuál es más grande?",
                null,
                "Puma|León|Tigre|Leopardo",
                null,
                2,
                Question.ControlType.RADIO_GROUP.name()));
        seed.add(new QuestionEntity(
                "Pregunta 3: ¿Cómo se llama este animal?",
                "garza",
                "Ganso|Oca|Cisne|Garza",
                null,
                3,
                Question.ControlType.LIST_VIEW.name()));
        seed.add(new QuestionEntity(
                "Pregunta 4: ¿Cuál es el animal más pequeño?",
                null,
                "Gato|Ratón|Cerdo|Lombriz",
                null,
                3,
                Question.ControlType.SPINNER.name()));
        seed.add(new QuestionEntity(
                "Pregunta 5: ¿Cuál de estos NO es omnívoro?",
                null,
                null,
                "oso_negro|oso_hormiguero|oso_pardo|panda_rojo",
                1,
                Question.ControlType.IMAGE_BUTTONS.name()));
        return seed;
    }

    private static Question toQuestion(Context context, QuestionEntity entity) {
        Question.ControlType controlType = Question.ControlType.valueOf(entity.getControlType());
        String[] textOptions = entity.getTextOptions() == null ? null : entity.getTextOptions().split(SEPARATOR);
        return new Question(
                getLayout(controlType),
                entity.getText(),
                getDrawableId(context, entity.getImageName()),
                textOptions,
                toDrawableIds(context, entity.getImageOptions()),
                entity.getCorrectIndex(),
                controlType);
    }

    private static int getLayout(Question.ControlType controlType) {
        switch (controlType) {
            case LIST_VIEW:
                return R.layout.activity_main_image_list;
            case SPINNER:
                return R.layout.activity_main_spinner;
            case IMAGE_BUTTONS:
                return R.layout.activity_main_text_images;
            default:
                return R.layout.activity_main;
        }
    }

    private static int[] toDrawableIds(Context context, String names) {
        if (names == null) {
            return null;
        }
        String[] parts = names.split(SEPARATOR);
        int[] ids = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            ids[i] = getDrawableId(context, parts[i]);
        }
        return ids;
    }

    @SuppressLint("DiscouragedApi")
    private static int getDrawableId(Context context, String name) {
        if (name == null) {
            return 0;
        }
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}