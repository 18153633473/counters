package com.example.asus.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView out = findViewById(R.id.textView2);
        TextView out2 = findViewById(R.id.newtextView2);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button newbtn1 = findViewById(R.id.newbutton1);
        Button newbtn2 = findViewById(R.id.newbutton2);
        Button newbtn3 = findViewById(R.id.newbutton3);
        Button btn4 = findViewById(R.id.buttons);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView out = findViewById(R.id.textView2);
                String str = out.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 1;
                String result =String.valueOf(num);
                out.setText(result);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView out = findViewById(R.id.textView2);
                String str = out.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 2;
                String result =String.valueOf(num);
                out.setText(result);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView out = findViewById(R.id.textView2);
                String str = out.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 3;
                String result =String.valueOf(num);
                out.setText(result);
            }
        });
        newbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView out2 = findViewById(R.id.newtextView2);
                String str = out2.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 1;
                String result =String.valueOf(num);
                out2.setText(result);
            }
        });
        newbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView out2 = findViewById(R.id.newtextView2);
                String str = out2.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 2;
                String result =String.valueOf(num);
                out2.setText(result);
            }
        });
        newbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView out2 = findViewById(R.id.newtextView2);
                String str = out2.getText().toString();
                int num = Integer.parseInt(str);
                num = num + 3;
                String result =String.valueOf(num);
                out2.setText(result);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView out = findViewById(R.id.textView2);
                TextView out2 = findViewById(R.id.newtextView2);
                out.setText("0");
                out2.setText("0");
            }
        });


    }

}