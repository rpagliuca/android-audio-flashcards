package br.com.devser.audioflashcards.controller;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import br.com.devser.audioflashcards.business.CardAdapter;
import br.com.devser.audioflashcards.R;
import br.com.devser.audioflashcards.business.CardPlayer;
import br.com.devser.audioflashcards.business.CommandProcessor;
import br.com.devser.audioflashcards.db.AppDatabase;
import br.com.devser.audioflashcards.db.Card;

public class CardsActivity extends BaseActivity {

    private static final String LOG_TAG = "CardsActivity";
    public AppDatabase db;
    public MediaButtonIntentReceiver mMediaButtonReceiver;

    @Override
    public int getContentViewId() {
        return R.layout.activity_cards;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_cards;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize database */
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        /* Insert button */
        findViewById(R.id.fab).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();
                card.setId(UUID.randomUUID().toString());
                card.setTextNote(card.getId());
                card.setDateCreated(Calendar.getInstance().getTime());
                db.cardDao().insertAll(card);
                refreshList();
            }
        });

        /* Play button */
        findViewById(R.id.fab2).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaButtonReceiver.triggerClick(v.getContext());
            }
        });

        /* Initialize singleton player */
        CardPlayer cardPlayer = CardPlayer.getInstance(db, this);
        CommandProcessor commandProcessor = CommandProcessor.getInstance(cardPlayer);

        /* Bluetooth media controls */
        Log.d(LOG_TAG, "Registering bluetooth media controls");
        /* Intent filter */
        IntentFilter mediaFilter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
        mediaFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        /* Intent receiver */
        mMediaButtonReceiver = new MediaButtonIntentReceiver();
        registerReceiver(mMediaButtonReceiver, mediaFilter);
        /* Second attempt */
        AudioManager mAudioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        ComponentName mediaButtonReceiver = new ComponentName(getPackageName(), MediaButtonIntentReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mediaButtonReceiver);

        /* Refresh cards list */
        refreshList();
    }

    public void refreshList() {
        List<Card> cards = db.cardDao().getAll();
        CardAdapter cardAdapter = new CardAdapter(cards, this);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(cardAdapter);
        cardAdapter.setListView(listView);
        CardPlayer.getInstance(db, this).refreshDb();
    }

}