package br.com.devser.audioflashcards.controller;

import br.com.devser.audioflashcards.R;

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
