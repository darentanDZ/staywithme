package com.staywithme.staywithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchContactActivity extends AppCompatActivity {

    public static String[] nameList = {"Ampang", "Cheras Balakong", "Cheras Leisure Mall", "Damansara Uptown", "Kajang", "Kepong", "Klang Bukit Raja AEON", "Klang Bukit Tinggi", "Mahkota Cheras"};
    public static String[] phoneList = {"03-42178269", "03-90748103", "03-91301810", "03-42178269", "03-90748103", "03-91301810", "03-42178269", "03-90748103", "03-91301810"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcontacts);

        // remove Status bar aka Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Spinner spinner = (Spinner) this.findViewById(R.id.contact_spinner);
        Button next_btn = (Button) this.findViewById(R.id.next_btn);

        CustomAdapter customAdapter = new CustomAdapter(this, nameList, phoneList);
        spinner.setAdapter(customAdapter);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getBaseContext(), DateTimeActivity.class);
                startActivity(j);
            }
        });
    }

}
