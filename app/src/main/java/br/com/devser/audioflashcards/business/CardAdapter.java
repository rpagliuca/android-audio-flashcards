package br.com.devser.audioflashcards.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.devser.audioflashcards.R;
import br.com.devser.audioflashcards.controller.CardsActivity;
import br.com.devser.audioflashcards.db.Card;

public class CardAdapter extends BaseAdapter {

    private static final String LOG_TAG = "CardAdapter";
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

        log("Adapter getView " + card.getId());
        final AudioRecord audioRecordQuestion = new AudioRecord(act, card.getId() + "_question");
        final AudioRecord audioRecordAnswer = new AudioRecord(act, card.getId() + "_answer");

        /* Print values */
        if (card.getDate() != null) {
            ((TextView) view.findViewById(R.id.date)).setText(card.getDate().toString());
        }
        ((TextView) view.findViewById(R.id.note)).setText(card.getNote());

        /* Delete button */
        (view.findViewById(R.id.btn_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmation(new MyDeletableCallbackInterface() {
                    @Override
                    public void onConfirmDelete() {
                        act.db.cardDao().delete(card);
                        act.refreshList();
                    }
                });
            }
        });

        /* Record question button */
        (view.findViewById(R.id.btn_record_question)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordQuestion.toggleRecording((ImageButton) v);
            }
        });

        /* Record answer button */
        (view.findViewById(R.id.btn_record_answer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordAnswer.toggleRecording((ImageButton) v);
            }
        });

        /* Play question button */
        (view.findViewById(R.id.btn_play_question)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordQuestion.togglePlay((ImageButton) v);
            }
        });

        /* Play answer button */
        (view.findViewById(R.id.btn_play_answer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordAnswer.togglePlay((ImageButton) v);
            }
        });

        return view;
    }

    private void log(String msg) {
        //Log.d(LOG_TAG, msg);
    }

    private void deleteConfirmation(final MyDeletableCallbackInterface deletable)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(act)
                //set message, title, and icon
                .setTitle("Excluir")
                .setMessage("Deseja mesmo excluir?")
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deletable.onConfirmDelete();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        myQuittingDialogBox.show();
    }

    interface MyDeletableCallbackInterface {
        void onConfirmDelete();
    }
}