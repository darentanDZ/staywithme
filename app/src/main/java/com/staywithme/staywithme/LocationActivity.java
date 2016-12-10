package com.staywithme.staywithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class LocationActivity extends AppCompatActivity {

    private ToggleSwitch switchLocation;
    private TextView addressText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        switchLocation = (ToggleSwitch) findViewById(R.id.locationSwitch);
        addressText = (TextView) findViewById(R.id.locationText);

        switchLocation.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener(){

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                // Write your code ...
                switch(position) {
                    case 0:
                        addressText.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        addressText.setVisibility(View.GONE);
                        addressText.setText("");
                        break;
                }
            }
        });
        Button next_btn = (Button) this.findViewById(R.id.nextButton);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(getBaseContext(), ConfirmationActivity.class);
                startActivity(l);
            }
        });

    }
}
