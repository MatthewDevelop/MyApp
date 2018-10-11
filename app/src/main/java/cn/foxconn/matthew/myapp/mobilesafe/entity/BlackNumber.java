package cn.foxconn.matthew.myapp.mobilesafe.entity;

/**
 * @author:Matthew
 * @date:2018/8/20
 * @email:guocheng0816@163.com
 * @func:
 */
public class BlackNumber {
    /**
     * 被拦截号码
     */
    private String number;
    /**
     * 拦截模式
     * 1 电话
     * 2 短信
     * 3 电话加短信
     */
    private String mode;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BlackNumber{" +
                "number='" + number + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

}
