package br.com.devser.audioflashcards.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

public class AudioRecord {

    private static final String LOG_TAG = "AudioRecord";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    private Activity act;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;

    public AudioRecord(Activity act) {
        this.act = act;
        // Record to the external cache directory for visibility
        mFileName = act.getExternalCacheDir().getAbsolutePath();
        Log.d(LOG_TAG, "Filename: " + mFileName);
        mFileName += "/audiorecordtest.3gp";
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
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

    public void toggleRecording() {
        onRecord(mStartRecording);
        if (mStartRecording) {
            Log.d(LOG_TAG, "Stop recording");
        } else {
            Log.d(LOG_TAG, "Start recording");
        }
        mStartRecording = !mStartRecording;
    }

    public void togglePlay() {
        onPlay(mStartPlaying);
        if (mStartPlaying) {
            Log.d(LOG_TAG, "Stop playing");
        } else {
            Log.d(LOG_TAG, "Start playing");
        }
        mStartPlaying = !mStartPlaying;
    }
}