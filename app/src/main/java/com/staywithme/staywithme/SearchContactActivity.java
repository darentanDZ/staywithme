package com.staywithme.staywithme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchContactActivity extends AppCompatActivity {

    public static String[] nameList = {"Wife",
            "2nd Wife",
            "Ah Boy(HP)",
            "Ah Boy(Office)",
            "Mike Smith",
            "Nazrul",
            "Rakesh",
            "Nicholas",
            "First Love"};
    public static String[] phoneList = {"013-42178269", "016-90748103", "011-91301810", "03-42178269", "017-90748103", "019-91301810", "012-42178269", "013-90748103", "016-91301810"};

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
