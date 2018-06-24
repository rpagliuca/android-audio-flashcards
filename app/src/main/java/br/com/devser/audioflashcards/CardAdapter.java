package br.com.devser.audioflashcards;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.devser.audioflashcards.controller.CardsActivity;
import br.com.devser.audioflashcards.db.Card;

public class CardAdapter extends BaseAdapter {

    private final List<Card> cards;
    private final CardsActivity act;

    public CardAdapter(List<Card> cards, CardsActivity act) {
        this.cards = cards;
        this.act = act;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Card card = (Card) getItem(position);
        View view = act.getLayoutInflater()
                .inflate(R.layout.cardview_card, parent, false);
        if (card.getDate() != null) {
            ((TextView) view.findViewById(R.id.date)).setText(card.getDate().toString());
        }
        ((TextView) view.findViewById(R.id.note)).setText(card.getNote());
        (view.findViewById(R.id.btn_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.db.cardDao().delete(card);
                act.refreshList();
            }
        });
        return view;
    }
}