package com.onion.onionadapter;

import android.app.Activity;
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
public class LoadImageDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     ListView listView= getListView();
    setContentView(listView);
        ArrayList<ImageBean> imageBeans=new ArrayList<>();
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        OnionBaseAdapter adapter=new OnionBaseAdapter(this,R.layout.item_loadimage,imageBeans,new String[]{"num"},new String[]{"getImgUrl"});
        HashMap<Integer,String> appends=new HashMap<>();
        appends.put(R.id.money, "$");
        adapter.setAppends(appends);
        listView.setAdapter(adapter);
    }

    public static class ImageBean implements Onion{
     public  String imgUrl;
        public int num=50;

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
