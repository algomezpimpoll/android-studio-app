package com.example.practica1.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insertAll(List<QuestionEntity> questions);

    @Query("SELECT * FROM questions ORDER BY id")
    List<QuestionEntity> getAll();

    @Query("SELECT COUNT(*) FROM questions")
    int count();

    @Query("SELECT * FROM questions WHERE id = :id")
    QuestionEntity findQuestionById(int id);
}