package br.com.devser.audioflashcards.business;

import android.content.Context;
import android.widget.Toast;

public class CommandProcessor {

    Context context;

    private static CommandProcessor INSTANCE = null;
    private final CardPlayer cardPlayer;

    // other instance variables can be here

    private CommandProcessor(CardPlayer cardPlayer) {
        this.cardPlayer = cardPlayer;
    };

    public static CommandProcessor getInstance() {
        return INSTANCE;
    }

    public static CommandProcessor getInstance(CardPlayer cardPlayer) {
        if (INSTANCE == null) {
            INSTANCE = new CommandProcessor(cardPlayer);
        }
        return INSTANCE;
    }

    public void processCommand(Context context, Integer clickCount) {
        this.context = context;
        switch (clickCount) {
            case 1:
                click1();
                break;
            case 2:
                click2();
                break;
            case 3:
                click3();
                break;
            default:
                clickDefault(clickCount);
                break;
        }
    }

    private void click1() {
        toast("Ouvindo novamente...");
        this.cardPlayer.playAgain();
    }

    private void click2() {
        toast("Próxima!");
        this.cardPlayer.playNext();
    }

    private void click3() {
        toast("Anterior!");
        this.cardPlayer.playPrevious();
    }

    private void clickDefault(Integer clickCount) {
        Toast.makeText(context, "Click count = " + clickCount, Toast.LENGTH_SHORT).show();
    }

    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
