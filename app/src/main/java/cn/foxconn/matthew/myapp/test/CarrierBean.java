package cn.foxconn.matthew.myapp.test;

import java.util.List;

/**
 * @author:Matthew
 * @date:2018/4/16
 * @email:guocheng0816@163.com
 */
public class CarrierBean {

    /**
     * meta : {"code":200}
     * data : [{"name":"DHL","code":"dhl","phone":"1 800 225 5345","homepage":"//www.dhl.com/","type":"express","picture":"//cdn.trackingmore.com/images/icons/express/dhl.png"},{"name":"China post","code":"china-post","phone":"86 20 11185","homepage":"//intmail.183.com.cn/","type":"globalpost","picture":"//cdn.trackingmore.com/images/icons/express/companylogo/3010.jpg"},{"name":"Singapore Post","code":"singapore-post","phone":"0065 / 6841 2000","homepage":"//www.singpost.com/","type":"globalpost","picture":"//cdn.trackingmore.com/images/icons/express/companylogo/19131.jpg"}]
     */

    private MetaBean meta;
    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * code : 200
         */

        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "MetaBean{" +
                    "code=" + code +
                    '}';
        }
    }

    public static class DataBean {
        /**
         * name : DHL
         * code : dhl
         * phone : 1 800 225 5345
         * homepage : //www.dhl.com/
         * type : express
         * picture : //cdn.trackingmore.com/images/icons/express/dhl.png
         */

        private String name;
        private String code;
        private String phone;
        private String homepage;
        private String type;
        private String picture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", phone='" + phone + '\'' +
                    ", homepage='" + homepage + '\'' +
                    ", type='" + type + '\'' +
                    ", picture='" + picture + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarrierBean{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
