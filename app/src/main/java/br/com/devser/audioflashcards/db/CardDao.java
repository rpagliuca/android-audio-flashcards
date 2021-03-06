package br.com.devser.audioflashcards.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card ORDER BY dateCreated DESC")
    List<Card> getAll();

    @Insert
    void insertAll(Card... cards);

    @Delete
    void delete(Card card);
}