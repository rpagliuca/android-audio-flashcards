package br.com.devser.audioflashcards.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.method.DateTimeKeyListener;

import java.util.Date;

@Entity
public class Card {

    @PrimaryKey
    @NonNull
    private String id;

    private String textNote;

    private String filePositionId;

    private Date dateCreated;

    private Date dateModified;

    private Integer status;

    @Ignore
    private Boolean playingQuestion = false;

    @Ignore
    private Boolean playingAnswer = false;

    @Ignore
    private Boolean isLastActive = false;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getFilePositionId() {
        return filePositionId;
    }

    public void setFilePositionId(String filePositionId) {
        this.filePositionId = filePositionId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPlayingQuestion(Boolean playingQuestion) { this.playingQuestion = playingQuestion; }

    public void setPlayingAnswer(Boolean playingAnswer) { this.playingAnswer = playingAnswer; }

    public Boolean isPlayingQuestion() { return playingQuestion; }

    public Boolean isPlayingAnswer() { return playingAnswer; }

    public Boolean getLastActive() {
        return isLastActive;
    }

    public void setLastActive(Boolean lastActive) {
        isLastActive = lastActive;
    }
}