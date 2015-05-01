package com.oracle.verde;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oracle.data.Data;
import com.oracle.web.JavascriptInterface;


public class RideActivity extends ActionBarActivity {

    TextView gasConsumption;
    RelativeLayout infoContainer;
    Button stateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        final WebView webView = (WebView) findViewById(R.id.webview_ride);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/route.html");

        webView.addJavascriptInterface(new JavascriptInterface(this), "Android");

        gasConsumption = (TextView) findViewById(R.id.gas_consumption);
        gasConsumption.setText("0 gallons saved");

        infoContainer = (RelativeLayout) findViewById(R.id.info_container);

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

        stateButton = (Button) findViewById(R.id.state_btn);

        final Data data = new Data(RideActivity.this);
        switch(data.getRideState()) {
            case Data.RIDE_ACCEPTED:
                invalidateOptionsMenu();
                stateButton.setText("Begin Trip");
                stateButton.setBackgroundColor(getResources().getColor(R.color.verde_blue));
                break;
            case Data.RIDE_ON:
                invalidateOptionsMenu();
                infoContainer.setVisibility(View.GONE);
                stateButton.setVisibility(View.GONE);
                gasConsumption.setVisibility(View.VISIBLE);
                break;
        }

        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(data.getRideState()) {
                    case Data.RIDE_WAITING:
                        data.setRideState(Data.RIDE_ACCEPTED);
                        invalidateOptionsMenu();
                        stateButton.setText("Begin Trip");
                        stateButton.setBackgroundColor(getResources().getColor(R.color.verde_blue));
                        break;
                    case Data.RIDE_ACCEPTED:
                        data.setRideState(Data.RIDE_ON);
                        invalidateOptionsMenu();
                        infoContainer.setVisibility(View.GONE);
                        stateButton.setVisibility(View.GONE);
                        gasConsumption.setVisibility(View.VISIBLE);

                        webView.loadUrl("javascript:map.setCenter(origin)");
                        webView.loadUrl("javascript:map.setZoom(15)");
                        
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ride, menu);

        Data data = new Data(RideActivity.this);

        switch(data.getRideState()) {
            case Data.RIDE_WAITING:
                menu.findItem(R.id.action_finish).setVisible(false);
                break;
            case Data.RIDE_ACCEPTED:
                data.setAppState(Data.APP_SET);
                menu.findItem(R.id.action_finish).setVisible(false);
                break;
            case Data.RIDE_ON:
                menu.findItem(R.id.action_cancel).setVisible(false);
                break;
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Data data = new Data(this);
        if (id == R.id.action_cancel) {
            data.setAppState(Data.APP_NONE);
            data.setRideState(Data.RIDE_WAITING);
            finish();
            return true;
        }
        else if(id == R.id.action_finish) {
            data.setAppState(Data.APP_NONE);
            data.setRideState(Data.RIDE_WAITING);

            finish();
            Intent intent = new Intent(RideActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
