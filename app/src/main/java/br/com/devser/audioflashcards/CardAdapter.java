package br.com.devser.audioflashcards;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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
        Card card = (Card) getItem(position);
        View view = act.getLayoutInflater()
                .inflate(R.layout.cardview_card, parent, false);
        if (card.getDate() != null) {
            ((TextView) view.findViewById(R.id.date)).setText(card.getDate().toString());
        }
        ((TextView) view.findViewById(R.id.note)).setText(card.getNote());
        return view;
    }
}