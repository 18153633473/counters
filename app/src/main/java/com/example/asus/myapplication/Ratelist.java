package com.example.asus.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Ratelist extends ListActivity implements Runnable, AdapterView.OnItemClickListener ,AdapterView.OnItemLongClickListener {
    Handler handler;
    private String logDate="";
    private final String DATE_SP_KEY="lastRateDateStr";

    private List<HashMap<String, String>> listitems;
    private SimpleAdapter listItemAdapter;
    private final String TAG = "日志";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ratelist);
//        MyAdapter myAdapter=new MyAdapter(this,R.layout.activity_ratelist,listitems);
        SharedPreferences sp=getSharedPreferences("myrate",Context.MODE_PRIVATE);
        logDate=sp.getString(DATE_SP_KEY,"");
        Log.i("list","datestr="+logDate);
        initListView();
        this.setListAdapter(listItemAdapter);

        Thread t = new Thread(this);
        t.start();

               handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    listitems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(Ratelist.this, listitems,
                            R.layout.activity_ratelist,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg); }};
               getListView().setOnItemClickListener(this);
               getListView().setOnItemLongClickListener(this);
    }

    private void initListView() {
        listitems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "rate:" + i);
            map.put("ItemDetail", "detail:" + i);
            listitems.add(map);

        }
        //生产适配器的item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listitems,
                R.layout.activity_ratelist,
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
    }

    @Override
    public void run() {
        List<String> retlist = new ArrayList<String>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("curDateStr", curDateStr + "logDate" + logDate);
        curDateStr = "1111";
        if (curDateStr.equals(logDate)) {
            Log.i("run", "日期相等，获取数据库数据");
            DBManager manager = new DBManager(this);
            for (RateItem item : manager.listAll()) {
                retlist.add(item.getCurName() + "-->" + item.getCurRate());
            }
        } else {
            //获取网络数据
            Log.i("run", "日期不等，获取网络数据");

            Document doc = null;
            try {
                Thread.sleep(3000);
                try {
                    doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "rundoc:" + doc.title());
                Elements tables = doc.getElementsByTag("table");
                Element table = tables.get(0);
                Elements tds = table.getElementsByTag("td");
                List<RateItem> rateList = new ArrayList<RateItem>();
                for (int i = 0; i < tds.size(); i += 6) {
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i + 5);
                    String str1 = td1.text();
                    String val = td2.text();
                    Log.i(TAG, "数据:" + str1 + "==" + val);
                  /*  HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", str1);
                    map.put("ItemDetail", val);*/
                    retlist.add(str1 + "==>" + val);
                    rateList.add(new RateItem(str1, val));
                }
                //数据写入数据库
                DBManager manager = new DBManager(this);
                manager.deleteAll();
                manager.addAll(rateList);
                //更新记录日期
                SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(DATE_SP_KEY, curDateStr);
                edit.commit();
                Log.i("run", "更新日期结束" + curDateStr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //bundle中保存获取的汇率
            Message msg = handler.obtainMessage(7);
            msg.obj = retlist;
            handler.sendMessage(msg);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i("onItemClick:","hello");
        //打开新页面传参
        Intent rateCalc = new Intent(this, RateCalActivity.class);
        rateCalc.putExtra("title", titleStr);
        rateCalc.putExtra("rate", Float.parseFloat(detailStr));
        startActivity(rateCalc);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
       //长按删除操作
        Log.i(TAG,"长按"+position);

       //构造对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("warning").setMessage("确认是否删除").setNegativeButton("是",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"对话框");
                listitems.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        }).setPositiveButton("否",null);
        builder.create().show();
       Log.i(TAG,"长度"+listitems.size());
        return true;
    }
}
