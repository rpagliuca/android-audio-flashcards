package br.com.devser.audioflashcards;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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