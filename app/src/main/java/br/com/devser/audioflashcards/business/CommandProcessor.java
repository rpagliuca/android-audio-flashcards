package br.com.devser.audioflashcards.business;

import android.content.Context;
import android.widget.Toast;

public class CommandProcessor {

    Context context;

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
        toast("Sim");
    }

    private void click2() {
        toast("Não");
    }

    private void click3() {
        toast("Talvez");
    }

    private void clickDefault(Integer clickCount) {
        Toast.makeText(context, "Click count = " + clickCount, Toast.LENGTH_SHORT).show();
    }

    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
