package br.com.devser.audioflashcards.business;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.devser.audioflashcards.db.AppDatabase;
import br.com.devser.audioflashcards.db.Card;

public class CardPlayer  {

    private static CardPlayer INSTANCE = null;
    private String LOG_TAG = "CardPlayer";
    private AppDatabase db;
    private String currentCardId = null;
    private String currentAudioType = null;
    private Integer currentListPos = null;
    private List<Card> cards;
    private Activity act;

    private CardPlayer(AppDatabase db, Activity act) {
        this.db = db;
        this.cards = db.cardDao().getAll();
        this.act = act;
    };

    public static CardPlayer getInstance(AppDatabase db, Activity act) {
        if (INSTANCE == null) {
            INSTANCE = new CardPlayer(db, act);
        }
        return(INSTANCE);
    }

    public String getFilename(String cardId, String audioType) {
        String mFileName = act.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        mFileName += "/" + cardId + "_" + audioType + ".3gp";
        return mFileName;
    }

    public void playNext() {
        refreshDb();
        if (cards.size() == 0) {
            return;
        }
        if (currentCardId == null | currentAudioType == null) {
            currentListPos = 0;
            currentAudioType = "question";
        } else {
            if (currentAudioType == "answer") {
                currentListPos++;
                currentListPos = currentListPos % cards.size();
                currentAudioType = "question";
            } else {
                currentAudioType = "answer";
            }
        }
        this.currentCardId = cards.get(currentListPos).getId();
        this.playFile(this.getFilename(this.currentCardId, this.currentAudioType));
    }

    public void playPrevious() {
        refreshDb();
        if (cards.size() == 0) {
            return;
        }
        if (currentCardId == null | currentAudioType == null) {
            currentListPos = cards.size() - 1;
            currentAudioType = "question";
        }
        if (this.currentAudioType == "answer") {
            this.currentAudioType = "question";
        } else {
            currentListPos--;
            currentListPos = (cards.size() + currentListPos) % cards.size();
            currentAudioType = "answer";
        }
        this.currentCardId = cards.get(currentListPos).getId();
        this.playFile(this.getFilename(this.currentCardId, this.currentAudioType));
    }

    public void playAgain() {
        if (currentCardId == null | currentAudioType == null) {
            return;
        }
        if (cards.size() == 0) {
            return;
        }
        this.playFile(this.getFilename(this.currentCardId, this.currentAudioType));
    }

    private void refreshDb() {
        this.cards = db.cardDao().getAll();
        if (this.cards.size() == 0) {
            this.currentListPos = null;
        }
        if (this.currentListPos != null) {
            this.currentListPos = this.currentListPos % cards.size();
        }
    }

    public void playFile(String fileName) {
        MediaPlayer mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Log.i(LOG_TAG, "Playing complete");
            }
        });
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
}