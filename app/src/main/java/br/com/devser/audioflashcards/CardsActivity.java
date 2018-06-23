package br.com.devser.audioflashcards;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CardsActivity extends BaseActivity {

    AppDatabase db;

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
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        FloatingActionButton clickButton = (FloatingActionButton) findViewById(R.id.fab);
        clickButton.setOnClickListener( new View.OnClickListener() {
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

    protected void refreshList() {
        List<Card> cards = db.cardDao().getAll();
        CardAdapter cardAdapter = new CardAdapter(cards, this);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(cardAdapter);
    }
}
