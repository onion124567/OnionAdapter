package com.onion.onionadapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onion.adapter.OnionBaseAdapter;
import com.onion.uitls.ImageLoaderConfig;
import com.onion.uitls.TextField;

import java.util.ArrayList;


public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoaderConfig.initImageLoader(this, "onion");
        ListView listView = getListView();
        setContentView(listView);
        ArrayList<StringBean> stringDemos = new ArrayList<>();
        stringDemos.add(new StringBean("联网图片Demo"));
        stringDemos.add(new StringBean("本地图片Demo"));
        stringDemos.add(new StringBean("回调按钮点击事件Demo"));
        stringDemos.add(new StringBean("差异性显示Demo"));
        OnionBaseAdapter baseAdapter = new OnionBaseAdapter<StringBean>(this, R.layout.item_index, stringDemos);//其中text为所取属性
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(this);
    }

    public static class StringBean {
        //设置成公有属性，方便反射
        @TextField(R.id.str)
        public String text;

        public StringBean(String text) {
            this.text = text;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                startActivity(new Intent(MainActivity.this, LoadImageDemo.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, LocationImageDemo.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, ClickBtnDemo.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, DiffenceViewDemo.class));
                break;
        }
    }
}
