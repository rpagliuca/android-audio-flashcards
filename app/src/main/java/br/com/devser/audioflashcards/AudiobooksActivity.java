package br.com.devser.audioflashcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AudiobooksActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_audiobooks;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_audiobooks;
    }
}
