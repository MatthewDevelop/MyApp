package cn.foxconn.matthew.myapp.mobilesafe.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author:Matthew
 * @date:2018/8/21
 * @email:guocheng0816@163.com
 * @func:adapter基类
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mList;

    public MyBaseAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
