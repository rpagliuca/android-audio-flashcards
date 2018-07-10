package br.com.devser.audioflashcards;

import android.app.Application;
import android.media.MediaPlayer;

public class App extends Application {

    public MediaPlayer mediaPlayer = null;

    public MediaPlayer getMediaPlayer()
    {
        if (this.mediaPlayer != null) {
            try {
                this.mediaPlayer.reset();
            } catch (IllegalStateException e) {
            }
        } else if (this.mediaPlayer == null) {
            this.mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setOnCompletionListener(null);
        return this.mediaPlayer;
    }

    public void stopMediaPlayer()
    {
        if (this.mediaPlayer != null) {
            try {
                this.mediaPlayer.reset();
            } catch (IllegalStateException e) {
            }
        }
    }
}