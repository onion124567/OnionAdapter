package com.onion.onionadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.onion.adapter.OnionBaseAdapter;
import com.onion.bean.Onion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/23.
 */
public class LocationImageDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     ListView listView= getListView();
    setContentView(listView);
        ArrayList<ImageBean> imageBeans=new ArrayList<>();
        imageBeans.add(new ImageBean("onion"));
        imageBeans.add(new ImageBean("onion"));
        imageBeans.add(new ImageBean("onion"));
        OnionBaseAdapter adapter=new OnionBaseAdapter(this,R.layout.item_loadimage,imageBeans,new String[]{"num"},new String[]{"getImgUrl"});
        adapter.setPicisNative(true);
        listView.setAdapter(adapter);
    }

    public static class ImageBean implements Onion{
     public  String imgUrl;
        public int num=30;

        public ImageBean(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
