package com.hitech.apolczynski.toi_toy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Admin on 2017-02-13.
 */

public class QuoteDialog extends Dialog {

    TextView tv_quote;
    JSONArray mQuote;
    String mStringQuote = "string";

    public QuoteDialog(Context context, JSONArray quote) {
        super(context);
        mQuote = quote;
    }
    public QuoteDialog(Context context, String quote) {
        super(context);
        mStringQuote = quote;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quote_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;

        tv_quote = (TextView) findViewById(R.id.tv_quote);



        // ponizej instrukcja w zaleznosci od uzytego konstruktora

        if (mStringQuote == "string") {
            try {
                tv_quote.setText(mQuote.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            tv_quote.setText(mStringQuote);
        }


    }
}
