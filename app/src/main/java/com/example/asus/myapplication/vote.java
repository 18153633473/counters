package com.example.asus.myapplication;

import android.app.AppComponentFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class vote extends AppCompatActivity {
    final String tag="tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
    }
    public void onClick(View btn){
        if (btn.getId()==R.id.btn1){
            new VoteTask().execute("赞成");
            switch (btn.getId()){
                case R.id.btn1:

            }
        }else if (btn.getId()==R.id.btn2){
            new VoteTask().execute("反对");
        }else {
            new VoteTask().execute("弃权");
        }
    }
    private String doVote(String votestr) {
        String retstr = "";
        Log.i(tag, "dovote" + votestr);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append("r=").append(URLEncoder.encode(votestr, "utf-8"));
            byte[] data = stringBuffer.toString().getBytes();
            String urlPath = "http://10.240.12.64:8080/vote/Getvote";
            try {
                URL url = new URL(urlPath);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setUseCaches(false);
                //设置请求体的类型是文本类型？？
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //设置请求体长度
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                //获得输出流，向服务器写入数据
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data);
                int response = httpURLConnection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    retstr = inputStreamToString(inputStream);
                    Log.i("vote", "retstr" + retstr);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retstr;
    }

    public static String inputStreamToString(InputStream inputStream){
        //储存处理结果
        String resultdata=null;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int len=0;
        try {
            while ((len=inputStream.read(data))!=-1){
                byteArrayOutputStream.write(data,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
            resultdata=new String(byteArrayOutputStream.toByteArray());
        return resultdata;
    }

    private class VoteTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            for (String p:params){
                Log.i(tag,"doInBackground"+p);
            }
            String ret=doVote(params[0]);
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(vote.this,s,Toast.LENGTH_SHORT).show();
        }
    }
}
