package com.hitech.apolczynski.toi_toy;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    private static final long DOUBLE_CLICK_DEFENDER = 600;
    private static long LAST_CLICK_TIME = 0;
    private ImageButton b_start;
    private Spinner spinner;
    private TextView tv_descr;
    private int orientation;

    @Override
    protected void onResume() {
        super.onResume();
        b_start.setSelected(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.toilet_flush);
        mp.setVolume(40, 40);
        mp.start();
        orientation = getResources().getConfiguration().orientation;
        tv_descr = (TextView) findViewById(R.id.tv_descr);
        spinner = (Spinner) findViewById(R.id.spinner);
        b_start = (ImageButton) findViewById(R.id.b_start);
        b_start.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < DOUBLE_CLICK_DEFENDER) {
                return;
            }
            LAST_CLICK_TIME = SystemClock.elapsedRealtime();

            b_start.setSelected(true);
            Utils.getQuote(getApplicationContext(), new Utils.VolleyCallback() {
                @Override
                public void onSuccess(JSONArray response) {
                    if (response.length() > 0) { // czy instrukcja naprawde konieczna?
                        Dialog dialog = new QuoteDialog(MainActivity.this, response);
                        dialog.setOnDismissListener(onDismiss);
                        dialog.show();
                        if (orientation == 1) {
                            tv_descr.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onError() {
                    Dialog dialog = new QuoteDialog(MainActivity.this, "\"Something went wrong, is it reasonable to be mad at beta software?\" \n\n~autor unknown");
                    dialog.setOnDismissListener(onDismiss);
                    dialog.show();
                    if (orientation == 1) {
                        tv_descr.setVisibility(View.INVISIBLE);
                    }
                }
            }, spinner.getSelectedItemPosition());
        }
        DialogInterface.OnDismissListener onDismiss = new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tv_descr.setVisibility(View.VISIBLE);
                b_start.setSelected(false);
            }
        };
    };
}
