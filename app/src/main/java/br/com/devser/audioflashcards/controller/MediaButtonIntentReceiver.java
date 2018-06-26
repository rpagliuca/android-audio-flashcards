package br.com.devser.audioflashcards.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import br.com.devser.audioflashcards.business.CommandProcessor;

public class MediaButtonIntentReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "MediaButtonIntentRec";
    private static Long lastTime;
    private static final Long MAX_ELAPSED_TIME = 500l;
    private static Integer clickCount = 0;
    private static Handler handler;

    public MediaButtonIntentReceiver() {
        super();
        log("Construiu MediaButtonIntentReceiver");
        if (handler == null) {
            handler = new android.os.Handler();
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        log("onReceive");
        String intentAction = intent.getAction();
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            final KeyEvent event = (KeyEvent) intent
                    .getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            int action = event.getAction();
            if (action == KeyEvent.ACTION_DOWN) {
                log("onKeyDown");
                Long elapsedTime;
                if (lastTime == null) {
                    lastTime = System.currentTimeMillis();
                }
                elapsedTime = System.currentTimeMillis() - lastTime;
                if (elapsedTime > MAX_ELAPSED_TIME) {
                    lastTime = System.currentTimeMillis();
                    clickCount = 0;
                } else {
                    handler.removeCallbacksAndMessages(null);
                }
                clickCount++;
                log("Key pressed (" + clickCount + "): " + event.getKeyCode());
                handler.postDelayed(
                        new Runnable() {
                            public void run() {
                                CommandProcessor.getInstance().processCommand(context, clickCount);

                            }
                        },
                        MAX_ELAPSED_TIME
                );

            }
        }

        if (isOrderedBroadcast()) {
            abortBroadcast();
        }
        lastTime = System.currentTimeMillis();
    }

    private void log(String msg) {
        Log.d(LOG_TAG, msg);
    }
}