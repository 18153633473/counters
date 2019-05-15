package com.example.asus.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.login.LoginException;

public class RateActivity extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView show;
    private final String TAG="日志";
    String update ="";
    double dollorrate;
    double europerate;
    double japanrate;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.show);
        //保存修改的利率在data.xml文件
        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollorrate = sharedPreferences.getFloat("dollarrate",0.0f);
        europerate = sharedPreferences.getFloat("europerate",0.0f);
        japanrate  = sharedPreferences.getFloat("japanrate",0.0f);
        update = sharedPreferences.getString("update_date","");

        //获取当前时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todaystr =sdf.format(today);
        Log.i(TAG,"update="+update);

        //判断时间
        if (!todaystr.equals(update)){
            Log.i(TAG,"需要更新");
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        }else {
            Log.i(TAG,"不更新");
        }


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==5){
                    Bundle bdl=(Bundle) msg.obj;
                    dollorrate=bdl.getFloat("dollar2rate");
                    japanrate=bdl.getFloat("japan2rate");
                    europerate=bdl.getFloat("europe2rate");
                    Log.i("tag","网页美元"+dollorrate);
                    Log.i("tag","网页日元"+japanrate);
                    Log.i("tag","网页欧元"+europerate);
                    //保存更新日期
                    SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("update_date",todaystr);
                    editor.putFloat("japanrate", (float) japanrate);
                    editor.putFloat("dollarrate", (float) dollorrate);
                    editor.putFloat("europerate", (float) europerate);
                    editor.apply();

                    Toast.makeText(RateActivity.this,"汇率更新完毕",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };



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
            //将新汇率写入sp
            SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("japanrate", (float) japanrate);
            editor.putFloat("dollarrate", (float) dollorrate);
            editor.putFloat("europerate", (float) europerate);
            Log.i("tag","japan"+japanrate);
            editor.commit();
            Log.i("TAG","已保存");
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


            Intent nextpage = new Intent(this,Ratelist.class);
            startActivityForResult(nextpage,1);
//            Intent nextpage = new Intent(this,Rate2Activity.class);
//            nextpage.putExtra("dollar-rate",dollorrate);
//            nextpage.putExtra("europe-rate",europerate);
//            nextpage.putExtra("japan-rate",japanrate);
//            startActivityForResult(nextpage,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        //用于保存获取的网页汇率
        Bundle bundle = new Bundle();

       /*获取网络数据
        URL url = null;
        try {
            url = new URL("http://www.boc.cn/sourcedb/whpj/");
            try {
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                InputStream in = http.getInputStream();
                String html = inputStreamtostring(in);
                String dollor = "<td>美元</td>";
                int location = html.indexOf(dollor);

                Log.i("tag","run_html"+html);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("", "rundoc:" + doc.title());
            Elements tables=doc.getElementsByTag("table");
            /*
            for (Element table:tables) {
                Log.i("", "run:table["+i+"]="+table);
                i++;
            }*/
            Element table=tables.get(0);
            //Log.i("tag", "run:table6="+table6);
            Elements tds=table.getElementsByTag("td");
            for (int i=0;i<tds.size();i+=6){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i("tag","run:"+td1.text()+"==>"+td2.text());
                if ("美元".equals(td1.text())){
                    bundle.putFloat("dollar2rate", 100f/Float.parseFloat(td2.text()));
                    Log.i("tag","td2"+td2.text());
                }else if("欧元".equals(td1.text())) {
                    bundle.putFloat("europe2rate", 100f / Float.parseFloat(td2.text()));
                }else if("日元".equals(td1.text())) {
                    bundle.putFloat("japan2rate", 100f / Float.parseFloat(td2.text()));
                }
            }

        }
            catch(IOException e){
                e.printStackTrace();
            }
            //bundle中保存获取的汇率
        Message msg = handler.obtainMessage(5);
       msg.obj=bundle;
        handler.sendMessage(msg);
        }


    private String inputStreamtostring (InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
