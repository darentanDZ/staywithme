package com.staywithme.staywithme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchContactActivity extends AppCompatActivity {

    public static String [] nameList={"Ampang","Cheras Balakong", "Cheras Leisure Mall", "Damansara Uptown", "Kajang","Kepong","Klang Bukit Raja AEON","Klang Bukit Tinggi","Mahkota Cheras"};
    public static String [] phoneList={"03-42178269","03-90748103","03-91301810","03-42178269","03-90748103","03-91301810", "03-42178269","03-90748103","03-91301810"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcontacts);

        Spinner spinner = (Spinner) this.findViewById(R.id.contact_spinner);

        CustomAdapter customAdapter = new CustomAdapter(this,nameList,phoneList);
        spinner.setAdapter(customAdapter);
    }
}
