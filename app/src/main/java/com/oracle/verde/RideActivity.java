package com.oracle.verde;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;


public class RideActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        WebView webView = (WebView) findViewById(R.id.webview_ride);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");

        TextView destination = (TextView) findViewById(R.id.destination);
        destination.setText("Oracle Parkway");
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText("25 miles");
        TextView totalGas = (TextView) findViewById(R.id.total_gas);
        totalGas.setText("0.9 gallons");
        TextView gasSaved = (TextView) findViewById(R.id.gas_saved);
        gasSaved.setText("0.9 gallons");
        TextView time = (TextView) findViewById(R.id.time);
        time.setText("30 minutes / 45 minutes without traffic");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_ride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
