package br.com.devser.audioflashcards.business;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

import br.com.devser.audioflashcards.App;
import br.com.devser.audioflashcards.ui.MediaImageButton;

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

    private void onPlay(boolean start, MediaImageButton btn) {
        if (start) {
            startPlaying(btn);
        } else {
            stopPlaying();
        }
    }

    private void startPlaying(final MediaImageButton btn) {
        mPlayer = ((App) act.getApplication()).getMediaPlayer();
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
        mPlayer.reset();
        mPlayer = null;
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
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

    public void toggleRecording(MediaImageButton btn) {
        if (mStartRecording) {
            btn.setActive();
        } else {
            btn.setInactive();
        }
        onRecord(mStartRecording);
        if (mStartRecording) {
            log("Stop recording " + mFileName);
        } else {
            log("Start recording " + mFileName);
        }
        mStartRecording = !mStartRecording;
    }

    public void togglePlay(MediaImageButton btn) {
        if (mStartPlaying) {
            btn.setActive();
        } else {
            btn.setInactive();
        }
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