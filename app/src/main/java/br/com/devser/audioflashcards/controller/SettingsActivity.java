package br.com.devser.audioflashcards.controller;

import br.com.devser.audioflashcards.R;

public class SettingsActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_settings;
    }
}