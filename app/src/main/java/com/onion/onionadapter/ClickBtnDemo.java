package com.onion.onionadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.onion.adapter.OnionBaseAdapter;
import com.onion.uitls.TextField;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ClickBtnDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        setContentView(listView);
        final ArrayList<ButtonBean> buttonBeans = new ArrayList<>();
        buttonBeans.add(new ButtonBean("点击按钮", 0));
        buttonBeans.add(new ButtonBean("点击按钮", 1));
        buttonBeans.add(new ButtonBean("点击按钮", 2));
        buttonBeans.add(new ButtonBean("点击按钮", 3));
        buttonBeans.add(new ButtonBean("点击按钮", 4));
        buttonBeans.add(new ButtonBean("点击按钮", 5));
        buttonBeans.add(new ButtonBean("点击按钮", 6));
        buttonBeans.add(new ButtonBean("点击按钮", 7));
        buttonBeans.add(new ButtonBean("点击按钮", 8));
        OnionBaseAdapter adapter = new OnionBaseAdapter<ButtonBean>(this, R.layout.item_btn, buttonBeans);
        adapter.setCallBack(new OnionBaseAdapter.CallBack() {
            @Override
            public void onGetView(View view, final int postion) {
                view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ClickBtnDemo.this, "点击了按钮" + buttonBeans.get(postion).num, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ClickBtnDemo.this, "点击了item" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ButtonBean {
        @TextField(R.id.test)
        public String text;
        private int num;

        public ButtonBean(String text, int num) {
            this.text = text;
            this.num = num;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
