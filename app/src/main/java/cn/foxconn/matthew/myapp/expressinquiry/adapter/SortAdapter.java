package cn.foxconn.matthew.myapp.expressinquiry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.expressinquiry.bean.SortBean;

/**
 * @author:Matthew
 * @date:2018/4/14
 * @email:guocheng0816@163.com
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {

    private List<SortBean> mList = null;
    private Context mContext;

    public SortAdapter(Context context, List<SortBean> list) {
        mContext = context;
        mList = list;
    }

    public void updateList(List<SortBean> list) {
        mList = list;
        notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final SortBean sortBean = mList.get(position);
        if (holder == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_express_company, null);
            holder.tvLetter = convertView.findViewById(R.id.tv_letter);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //根据位置获取分类的首字母的char ascII码值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母char的位置，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(sortBean.getSortLetters());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }

        holder.tvName.setText(sortBean.getName());
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascII码获取第一次出现该首字母的位置
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for(int i=0;i<getCount();i++){
            String sortStr=mList.get(i).getSortLetters();
            char firstChar=sortStr.toUpperCase().charAt(0);
            if(firstChar==sectionIndex){
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据listView当前位置获取分类首字母的char ascII码值
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetters().charAt(0);
    }

    private class ViewHolder {
        TextView tvLetter;
        TextView tvName;
    }
}
