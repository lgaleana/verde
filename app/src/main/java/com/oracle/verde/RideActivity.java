package com.oracle.verde;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class RideActivity extends ActionBarActivity {

    static int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        WebView webView = (WebView) findViewById(R.id.webview_ride);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");

        final TextView gasConsumption = (TextView) findViewById(R.id.gas_consumption);
        gasConsumption.setText("0 gallons saved");

        final RelativeLayout infoContainer = (RelativeLayout) findViewById(R.id.info_container);

        TextView destination = (TextView) findViewById(R.id.destination);
        destination.setText("Oracle Parkway");
        TextView distance = (TextView) findViewById(R.id.distance);
        distance.setText("25 miles");
        TextView totalGas = (TextView) findViewById(R.id.total_gas);
        totalGas.setText("0.9 gallons");
        TextView gasSaved = (TextView) findViewById(R.id.gas_saved);
        gasSaved.setText("0.4 gallons saved");
        TextView time = (TextView) findViewById(R.id.time);
        time.setText("30 minutes / 45 minutes without traffic");

        final Button stateButton = (Button) findViewById(R.id.state_btn);
        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(state) {
                    case 0:
                        state++;
                        invalidateOptionsMenu();
                        stateButton.setText("Begin Trip");
                        stateButton.setBackgroundColor(getResources().getColor(R.color.verde_blue));
                        break;
                    case 1:
                        state++;
                        invalidateOptionsMenu();
                        infoContainer.setVisibility(View.GONE);
                        stateButton.setVisibility(View.GONE);
                        gasConsumption.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ride, menu);

        switch(state) {
            case 0:
                menu.findItem(R.id.action_finish).setVisible(false);
                break;
            case 1:
                menu.findItem(R.id.action_finish).setVisible(false);
                menu.findItem(R.id.action_cancel).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.action_cancel).setVisible(false);
                break;
        }


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
