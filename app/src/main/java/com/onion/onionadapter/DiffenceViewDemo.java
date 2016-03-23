package com.onion.onionadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.onion.adapter.OnionBaseAdapter;
import com.onion.bean.Onion;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/23.
 */
public class DiffenceViewDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        setContentView(listView);
        final ArrayList<DiffBean> diffBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            diffBeans.add(new DiffBean(i));
        OnionBaseAdapter adapter = new OnionBaseAdapter(this, R.layout.item_diff, diffBeans);
        adapter.setCallBack(new OnionBaseAdapter.CallBack() {
            @Override
            public void onGetView(View view, int postion) {
                if (diffBeans.get(postion).num % 3 > 0)
                    view.findViewById(R.id.iv_icon).setVisibility(View.VISIBLE);
                else
                    view.findViewById(R.id.iv_icon).setVisibility(View.GONE);
            }
        });
        listView.setAdapter(adapter);
    }

    public static class DiffBean implements Onion {
        public int num = 30;

        public DiffBean(int num) {
            this.num = num;
        }
    }
}
