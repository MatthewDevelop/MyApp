package cn.foxconn.matthew.myapp.wanandroid.bean.pojo;

/**
 * @author:Matthew
 * @date:2018/3/15
 * @email:guocheng0816@163.com
 */

public class HotKeyBean {
    /**
     * icon : null
     * id : 6
     * name : 面试
     * link : null
     * visible : 1
     * order : 1
     */
    private String icon;
    private int id;
    private String name;
    private String link;
    private int visible;
    private int order;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
