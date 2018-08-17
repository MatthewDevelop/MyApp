package cn.foxconn.matthew.myapp.expressinquiry.utils;


import java.util.Comparator;

import cn.foxconn.matthew.myapp.expressinquiry.bean.SortBean;

/**
 * @author:Matthew
 * @date:2018/4/14
 * @email:guocheng0816@163.com
 */
public class PinyinComparator implements Comparator<SortBean> {

    @Override
    public int compare(SortBean o1, SortBean o2) {
        //将快递公司按拼音排序
        if ("#".equals(o1.getSortLetters())) {
            return 1;
        } else if ("#".equals(o2.getSortLetters())) {
            return -1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
