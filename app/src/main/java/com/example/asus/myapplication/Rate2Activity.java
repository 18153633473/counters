package com.example.asus.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Rate2Activity extends AppCompatActivity {
    EditText text1;
    EditText text2;
    EditText text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate2);
        Intent intent = getIntent();
        double dollar = intent.getDoubleExtra("dollar-rate",0.0d);
        double europe = intent.getDoubleExtra("europe-rate",0.0d);
        double japan  = intent.getDoubleExtra("japan-rate",0.0d);
        text1 = (EditText)findViewById(R.id.dollarrate);
        text2 = (EditText)findViewById(R.id.europerate);
        text3 = (EditText)findViewById(R.id.japanrate);

        text1.setText(String.valueOf(dollar));
        text2.setText(String.valueOf(europe));
        text3.setText(String.valueOf(japan));
    }
    public void save(View btn){
        Double newdollar = Double.parseDouble(text1.getText().toString());
        Double neweurope = Double.parseDouble(text2.getText().toString());
        Double newjapan = Double.parseDouble(text3.getText().toString());
        Bundle bdl = new Bundle();
        bdl.putDouble("dollarrate",newdollar);
        bdl.putDouble("europerate",neweurope);
        bdl.putDouble("japanrate",newjapan);
        Log.i("aaa","newdollar:"+newdollar);
        Log.i("ccc","newdollar:"+newjapan);
        Intent intent = getIntent();
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }
}
