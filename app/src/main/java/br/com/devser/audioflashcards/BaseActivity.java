package br.com.devser.audioflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_cards:
                    intent = new Intent(getApplicationContext(), CardsActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_cards);
                    return true;
                case R.id.navigation_audiobooks:
                    intent = new Intent(getApplicationContext(), AudiobooksActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_audiobooks);
                    return true;
                case R.id.navigation_settings:
                    intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getContentViewId());

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void selectBottomNavigationBarItem(int itemId) {
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

}
