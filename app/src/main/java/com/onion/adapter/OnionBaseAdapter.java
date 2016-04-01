package com.onion.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.onion.onionadapter.R;
import com.onion.uitls.ImageField;
import com.onion.uitls.TextField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 显示列表的适配器
 *
 * @author onion
 */
@SuppressWarnings("NullArgumentToVariableArgMethod")

public class OnionBaseAdapter<T> extends BaseAdapter {
    int layoutId;
    protected List<T> list;
    protected Context context;
    boolean picisNative = false;//是否使用本地图片
    private String packageName;
    DisplayImageOptions displayImageOptions;
    CallBack callBack;

    public void setPicisNative(boolean picisNative) {
        this.picisNative = picisNative;
        packageName = context.getPackageName();
    }

    public OnionBaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        list = new ArrayList<T>();
    }

    public OnionBaseAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }


    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<T> list,  DisplayImageOptions displayImageOptions) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.displayImageOptions = displayImageOptions;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertview, ViewGroup arg2) {
        ViewHolder vh = null;
        Object o = list.get(index);
        if (convertview == null) {
            convertview = View.inflate(context, layoutId, null);
            vh = new ViewHolder(convertview);
            convertview.setTag(vh);
        } else {
            vh = (ViewHolder) convertview.getTag();
        }

        //设置文字
        for (String fieldName : vh.findTexts.keySet()) {
            Field f = null;
            Object text = "";
            try {
                f = o.getClass().getField(fieldName);
                text = f.get(o);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            vh.findTexts.get(fieldName).setText(text + "");
        }
        //设置图片
        for (String fieldName : vh.findImgs.keySet()) {
            Field f = null;
            String text = "";
            try {
                f = o.getClass().getField(fieldName);
                text = (String) f.get(o);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (picisNative) {//加载本地图片
                if (text.contains("."))//去掉本地图片后缀
                    text = text.substring(0, text.lastIndexOf("."));
//                    ImageLoader.getInstance().displayImage(
//                            "drawable://" + context.getResources().getIdentifier(path, "drawable",
//                                    packageName), vh.ivs.get(i));
                int id = context.getResources().getIdentifier(text, "drawable",
                        packageName);
                if (id != 0) {//本地图片存在
                    vh.findImgs.get(fieldName).setImageResource(id);
                } else {//本地图片不存在,加载默认图片
                    vh.findImgs.get(fieldName).setImageResource(R.drawable.icon_default);
                }
            } else {
                if (displayImageOptions == null)
                    ImageLoader.getInstance().displayImage(text, vh.findImgs.get(fieldName));
                else
                    ImageLoader.getInstance().displayImage(text, vh.findImgs.get(fieldName), displayImageOptions);
            }
        }


        if (callBack != null) callBack.onGetView(convertview, index);
        return convertview;
    }

    class ViewHolder {
        HashMap<String, TextView> findTexts = new HashMap<>();
        HashMap<String, ImageView> findImgs = new HashMap<>();

        ViewHolder(View convertView) {
            if (list.size() > 0) {
                Field[] fields = list.get(0).getClass().getDeclaredFields();
                for (Field f : fields) {
                    //认为是文本
                    TextField textField = f.getAnnotation(TextField.class);
                    if (textField != null && convertView.findViewById(textField.value()) != null) {
                        int vId = textField.value();
                        findTexts.put(f.getName(), (TextView) convertView.findViewById(vId));
                    } else {
                        //
                        ImageField imageField = f.getAnnotation(ImageField.class);
                        if (imageField != null && convertView.findViewById(imageField.value()) != null) {
                            int vId = imageField.value();
                            findImgs.put(f.getName(), (ImageView) convertView.findViewById(vId));
                        }
                    }
                }
            }

        }

    }

    public interface CallBack {
        void onGetView(View view, int postion);
    }

}
