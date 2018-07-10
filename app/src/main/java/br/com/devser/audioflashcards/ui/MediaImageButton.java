package br.com.devser.audioflashcards.ui;

import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.devser.audioflashcards.R;

public class MediaImageButton {

    ImageButton btn;
    ListView listView;
    Integer position;

    public MediaImageButton(ImageButton btn, ListView listView, Integer position) {
        this.listView = listView;
        this.position = position;
        this.btn = btn;
    }

    public void setActive() {
        btn.setColorFilter(listView.getResources().getColor(R.color.colorPrimary));
        listView.smoothScrollToPosition(this.position);
    }

    public void setInactive() {
        btn.setColorFilter(Color.BLACK);
    }

}
