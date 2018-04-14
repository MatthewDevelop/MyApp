package cn.foxconn.matthew.myapp.expressinquiry.bean;

/**
 * @author:Matthew
 * @date:2018/4/13
 * @email:guocheng0816@163.com
 */
public class SortBean {
    //显示的数据
    private String name;
    //显示数据拼音的首字母
    private String sortLetters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
