package cn.foxconn.matthew.myapp.expressinquiry.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class ExpressResponseData {

    /**
     * message : ok
     * nu : 888590141663223471
     * ischeck : 1
     * condition : F00
     * com : yuantong
     * status : 200
     * state : 3
     * data : [{"time":"2018-03-13 20:53:34","ftime":"2018-03-13 20:53:34","context":"客户 签收人: 爱家代理点 已签收 感谢使用圆通速递，期待再次为您服务","location":null},{"time":"2018-03-13 15:34:34","ftime":"2018-03-13 15:34:34","context":"【湖北省武汉市武昌区黄家湖公司】 派件人: 罗凤 派件中 派件员电话18571510189","location":null},{"time":"2018-03-13 14:17:25","ftime":"2018-03-13 14:17:25","context":"【湖北省武汉市洪山区白沙洲公司】 已发出 下一站 【湖北省武汉市武昌区黄家湖公司】","location":null},{"time":"2018-03-13 11:26:40","ftime":"2018-03-13 11:26:40","context":"【武昌转运中心】 已发出 下一站 【湖北省武汉市洪山区白沙洲公司】","location":null},{"time":"2018-03-12 03:55:15","ftime":"2018-03-12 03:55:15","context":"【武昌转运中心】 已收入","location":null},{"time":"2018-03-11 22:49:28","ftime":"2018-03-11 22:49:28","context":"【武汉转运中心】 已发出 下一站 【武昌转运中心】","location":null},{"time":"2018-03-11 22:43:55","ftime":"2018-03-11 22:43:55","context":"【武汉转运中心】 已收入","location":null},{"time":"2018-03-11 18:29:43","ftime":"2018-03-11 18:29:43","context":"【湖北省武汉市武昌区庙山公司】 已发出 下一站 【武汉转运中心】","location":null},{"time":"2018-03-11 18:02:46","ftime":"2018-03-11 18:02:46","context":"【湖北省武汉市武昌区庙山公司】 已打包","location":null},{"time":"2018-03-11 12:56:20","ftime":"2018-03-11 12:56:20","context":"【湖北省武汉市武昌区庙山公司】 已收件","location":null}]
     */

    private String message;
    @SerializedName("nu")
    private String number;
    @SerializedName("ischeck")
    private String isCheck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-03-13 20:53:34
         * ftime : 2018-03-13 20:53:34
         * context : 客户 签收人: 爱家代理点 已签收 感谢使用圆通速递，期待再次为您服务
         * location : null
         */

        private String time;
        private String ftime;
        private String context;
        private Object location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    ", ftime='" + ftime + '\'' +
                    ", context='" + context + '\'' +
                    ", location=" + location +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExpressResponseData{" +
                "message='" + message + '\'' +
                ", number='" + number + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", condition='" + condition + '\'' +
                ", com='" + com + '\'' +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", data=" + data +
                '}';
    }
}
