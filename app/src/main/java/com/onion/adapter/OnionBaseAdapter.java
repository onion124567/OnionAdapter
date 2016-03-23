package com.onion.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.onion.bean.Onion;
import com.onion.onionadapter.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 显示列表的适配器
 *
 * @author onion
 */
@SuppressWarnings("NullArgumentToVariableArgMethod")
public class OnionBaseAdapter extends BaseAdapter {
    int layoutId;
    protected List<? extends Onion> list;
    protected Context context;
//    protected SingleChildCallBack singleChildCallBack;
    /**
     * 用来指定显示属性顺序
     */
    String[] filedNames;
    String[] getImgMetHode;
    boolean picisNative = false;//是否使用本地图片
    private String packageName;
    public void setPicisNative(boolean picisNative) {
        this.picisNative = picisNative;
        packageName=context.getPackageName();
    }

    DisplayImageOptions displayImageOptions;
CallBack callBack;
    public OnionBaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        list = new ArrayList<Onion>();
    }

    public OnionBaseAdapter(Context context, int layoutId, List<? extends Onion> list) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames, Map<Integer,String> appends) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
        this.appends=appends;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames, String[] getImgMetHode, DisplayImageOptions displayImageOptions) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
        this.getImgMetHode = getImgMetHode;
        this.displayImageOptions = displayImageOptions;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames, String[] getImgMetHode) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
        this.getImgMetHode = getImgMetHode;
    }
    //所加前缀
    Map<Integer,String> appends;
    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames, Map<Integer,String> appends, String[] getImgMetHode) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
        this.getImgMetHode = getImgMetHode;
        this.appends=appends;
    }

    public OnionBaseAdapter(Context context, int layoutId,
                            List<? extends Onion> list, String[] filedNames, String[] getImgMetHode, boolean picisNative) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
        this.filedNames = filedNames;
        this.getImgMetHode = getImgMetHode;
        this.picisNative = picisNative;
        packageName=context.getPackageName();
    }

    public void changeData(List<? extends Onion> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setAppends(Map<Integer, String> appends) {
        this.appends = appends;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Onion getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
//		String str = list.get(index).getId();
//		if (str == null || TextUtils.isEmpty(str))
//			return 0;
//		return Long.parseLong(str);
        return index;
    }

    @Override
    public View getView(int index, View convertview, ViewGroup arg2) {
        ViewHolder vh = null;
        Onion yb = list.get(index);
        Class<? extends Onion> cc = yb.getClass();
        if (convertview == null) {
            convertview = View.inflate(context, layoutId, null);
//            if (singleChildCallBack != null && !singleChildCallBack.initindexs.isEmpty()) {//添加监听事件
//                for(int i=0;i<singleChildCallBack.initindexs.size();i++)
//                singleChildCallBack.initSingleChildView(((LinearLayout) convertview).getChildAt(i),index,i);
//            }
            vh = new ViewHolder((LinearLayout) convertview);
            convertview.setTag(vh);
        } else {
            vh = (ViewHolder) convertview.getTag();
        }
        int n = vh.tvs.size();
        int m = cc.getFields().length;
        Object o = "";
        if (filedNames == null) {// 没有指定顺序
            for (int i = 0; i < n && i < m; i++) {
                Field f = cc.getFields()[i];

                try {
                    o = f.get(yb);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                vh.tvs.get(i).setText(o.toString());
            }
        } else {// 有指定顺序
            for (int i = 0; i < n && i < m && i < filedNames.length; i++) {
                try {
                    Field f = cc.getField(filedNames[i]);
                    o = f.get(yb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(appends!=null&&appends.containsKey(vh.tvs.get(i).getId()))   {
                    int id= vh.tvs.get(i).getId();
                    vh.tvs.get(i).setText(appends.get(id)+o.toString());
                }
          else      vh.tvs.get(i).setText(o+"");
            }
        }
        if (getImgMetHode != null) {//有图片，指定图片摆放顺序
            for (int i = 0; i < vh.ivs.size() && i < getImgMetHode.length; i++) {
                try {
                    Method method = cc.getMethod(getImgMetHode[i],null);// 图片路径通过方法反射获得
                    o = method.invoke(yb,  null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String path="";
                if(o!=null)
                path = o.toString();
                if (picisNative) {//加载本地图片
                    if (o.toString().contains("."))//去掉本地图片后缀
                        path = path.substring(0, o.toString().lastIndexOf("."));
//                    ImageLoader.getInstance().displayImage(
//                            "drawable://" + context.getResources().getIdentifier(path, "drawable",
//                                    packageName), vh.ivs.get(i));
                 int id=   context.getResources().getIdentifier(path, "drawable",
                            packageName);
                    if(id!=0){//本地图片存在
                        vh.ivs.get(i).setImageResource(id);
                    }else {//本地图片不存在,加载默认图片
                        vh.ivs.get(i).setImageResource(R.drawable.icon_default);
                    }
                } else {
                    if (displayImageOptions == null)
                        ImageLoader.getInstance().displayImage( path, vh.ivs.get(i));
                    else ImageLoader.getInstance().displayImage(
                            path, vh.ivs.get(i), displayImageOptions);
                }
            }
        }

        if(callBack!=null)callBack.onGetView(convertview,index);
        return convertview;
    }

    class ViewHolder {
        ArrayList<TextView> tvs;
        ArrayList<ImageView> ivs;

        ViewHolder(LinearLayout l) {
            tvs = new ArrayList<TextView>();
            ivs = new ArrayList<ImageView>();
            addTextViews(l);
        }

        private void addTextViews(LinearLayout l) {
            for (int i = 0; i < l.getChildCount(); i++) {
                View v = l.getChildAt(i);
                if (v instanceof TextView && (TextUtils.isEmpty(((TextView) v).getText().toString()) || "TextView".equals(((TextView) v).getText().toString()))) {
                    tvs.add((TextView) v);
                }
                if (v instanceof ImageView)
                    ivs.add((ImageView) v);
                if (v instanceof LinearLayout) {
                    addTextViews((LinearLayout) v);
                }
            }
        }
    }
   public  interface CallBack{
        void onGetView(View view, int postion);
    }

}
