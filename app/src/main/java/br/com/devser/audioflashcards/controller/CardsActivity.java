package br.com.devser.audioflashcards.controller;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import br.com.devser.audioflashcards.business.CardAdapter;
import br.com.devser.audioflashcards.R;
import br.com.devser.audioflashcards.db.AppDatabase;
import br.com.devser.audioflashcards.db.Card;

public class CardsActivity extends BaseActivity {

    public AppDatabase db;

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
                card.setNote(card.getId());
                card.setDate(Calendar.getInstance().getTime());
                db.cardDao().insertAll(card);
                refreshList();
            }
        });

        refreshList();
    }

    public void refreshList() {
        List<Card> cards = db.cardDao().getAll();
        CardAdapter cardAdapter = new CardAdapter(cards, this);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(cardAdapter);
    }
}
