package br.com.devser.audioflashcards.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.IOException;

public class AudioRecord {

    private static final String LOG_TAG = "AudioRecord";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    private Activity act;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;

    public AudioRecord(Activity act, String cardId) {
        this.act = act;
        // Record to the external cache directory for visibility
        mFileName = act.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        mFileName += "/" + cardId + ".3gp";
        log("Filename: " + mFileName);
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start, ImageButton btn) {
        if (start) {
            startPlaying(btn);
        } else {
            stopPlaying();
        }
    }

    private void startPlaying(final ImageButton btn) {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Log.i(LOG_TAG, "Playing complete");
                togglePlay(btn);
            }
        });
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void toggleRecording(ImageButton btn) {
        int color;
        if (mStartRecording) {
            color = Color.RED;
        } else {
            color = Color.BLACK;
        }
        btn.setColorFilter(color);
        onRecord(mStartRecording);
        if (mStartRecording) {
            log("Stop recording " + mFileName);
        } else {
            log("Start recording " + mFileName);
        }
        mStartRecording = !mStartRecording;
    }

    public void togglePlay(ImageButton btn) {
        int color;
        if (mStartPlaying) {
            color = Color.RED;
        } else {
            color = Color.BLACK;
        }
        btn.setColorFilter(color);
        onPlay(mStartPlaying, btn);
        if (mStartPlaying) {
            log("Stop playing " + mFileName);
        } else {
            log("Start playing" + mFileName);
        }
        mStartPlaying = !mStartPlaying;
    }

    private void log(String msg) {
        //Log.d(LOG_TAG, msg);
    }
}