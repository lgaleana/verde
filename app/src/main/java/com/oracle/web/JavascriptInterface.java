package com.oracle.web;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lgaleana on 5/1/15.
 */
public class JavascriptInterface {
    Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void  toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
