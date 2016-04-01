package com.onion.onionadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.onion.adapter.OnionBaseAdapter;
import com.onion.uitls.ImageField;
import com.onion.uitls.TextField;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/23.
 */
public class LoadImageDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        setContentView(listView);
        ArrayList<ImageBean> imageBeans = new ArrayList<>();
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        imageBeans.add(new ImageBean("http://dimg04.c-ctrip.com/images/vacations/images2/1/480/480_34_s31563_C_500_280.jpg"));
        OnionBaseAdapter adapter = new OnionBaseAdapter<ImageBean>(this, R.layout.item_loadimage, imageBeans);
      /*  HashMap<Integer,String> appends=new HashMap<>();
        appends.put(R.id.money, "$");
        adapter.setAppends(appends);*/
        listView.setAdapter(adapter);
    }

    public static class ImageBean {
        @ImageField(R.id.pic)
        public String imgUrl;
        @TextField(R.id.money)
        public int num = 50;

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
