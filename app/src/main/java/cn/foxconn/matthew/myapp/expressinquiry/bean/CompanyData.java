package cn.foxconn.matthew.myapp.expressinquiry.bean;

import java.util.List;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class CompanyData {
    List<CompanyBean> rows;

    public List<CompanyBean> getRows() {
        return rows;
    }

    public void setRows(List<CompanyBean> rows) {
        this.rows = rows;
    }

    public class CompanyBean {
        String company_name;
        String company_code;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        @Override
        public String toString() {
            return "CompanyBean{" +
                    "company_name='" + company_name + '\'' +
                    ", company_code='" + company_code + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CompanyData{" +
                "rows=" + rows +
                '}';
    }
}
