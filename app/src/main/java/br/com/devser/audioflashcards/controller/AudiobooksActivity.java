package br.com.devser.audioflashcards.controller;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.UUID;

import br.com.devser.audioflashcards.App;
import br.com.devser.audioflashcards.R;
import br.com.devser.audioflashcards.db.Card;

public class AudiobooksActivity extends BaseActivity {

    final String LOG_TAG = "AudiobooksActivity";
    final int ACTIVITY_CHOOSE_FILE = 1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_audiobooks;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_audiobooks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Insert button */
        findViewById(R.id.fab).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowse(v);
            }
        });

        /* Insert button */
        findViewById(R.id.fab2).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App app = (App) getApplication();
                app.stopMediaPlayer();
            }
        });
    }

    public void onBrowse(View view) {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Escolha um arquivo");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == ACTIVITY_CHOOSE_FILE)
        {
            Uri uri = data.getData();
            Log.d(LOG_TAG, uri.toString());

            App app = (App) getApplication();
            MediaPlayer mediaPlayer = app.getMediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        }
    }
}
