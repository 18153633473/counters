package com.example.asus.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;
    double dollorrate = 0.1489;
    double europerate = 0.1321;
    double japanrate = 16.5512;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.show);
        Button dollor = findViewById(R.id.dollor);
        Button europe = findViewById(R.id.europe);
        Button japan = findViewById(R.id.japan);
        dollor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rmb = findViewById(R.id.rmb);
                show = findViewById(R.id.show);
                if (rmb!=null) {

                    String str = rmb.getText().toString();
                Double str1= Double.parseDouble(str);
                Double str2 = str1 * dollorrate;
                String result =String.valueOf(str2);
                show.setText(str+"人民币是"+result+"美元");}
            }
        });
        europe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rmb = findViewById(R.id.rmb);
                show = findViewById(R.id.show);
                if (rmb!=null) {
                    String str = rmb.getText().toString();
                    Double str1 = Double.parseDouble(str);
                    Double str2 = str1 * europerate;
                    String result =String.valueOf(str2);
                    show.setText(str+"人民币是"+result+"欧元");
                }
            }
        });
        japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rmb = findViewById(R.id.rmb);
                show = findViewById(R.id.show);
                if (rmb!=null) {
                    String str = rmb.getText().toString();
                    Double str1 = Double.parseDouble(str);
                    Double str2 = str1 * japanrate;
                    String result =String.valueOf(str2);
                    show.setText(str+"人民币是"+result+"日元");
                }

            }


        });

    }
    public void openOne(View btn){
        Log.i("open","运行否");

        Intent nextpage = new Intent(this,Rate2Activity.class);
        nextpage.putExtra("dollar-rate",dollorrate);
        nextpage.putExtra("europe-rate",europerate);
        nextpage.putExtra("japan-rate",japanrate);
        startActivityForResult(nextpage,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollorrate = bundle.getDouble("dollarrate");
            Log.i("bbb","dollar=" + dollorrate);
            europerate = bundle.getDouble("europerate");
            japanrate = bundle.getDouble("japanrate");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.rate,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu){
            Log.i("open","运行否");

            Intent nextpage = new Intent(this,Rate2Activity.class);
            nextpage.putExtra("dollar-rate",dollorrate);
            nextpage.putExtra("europe-rate",europerate);
            nextpage.putExtra("japan-rate",japanrate);
            startActivityForResult(nextpage,1);
        }
        return super.onOptionsItemSelected(item);
    }
}
