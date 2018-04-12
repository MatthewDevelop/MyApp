package cn.foxconn.matthew.myapp.expressinquiry.view;

import java.util.List;

import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public interface ExpressQueryView {
    /**
     * 成功加载company_name
     *
     * @param company_names
     */
    void loadCompanyNameSuccess(List<String> company_names);

    /**
     * 查询物流信息成功
     *
     * @param dataBeans
     */
    void queryExpressInfoSuccess(List<ExpressResponseData.DataBean> dataBeans);

    /**
     * 查询出错
     *
     * @param message
     */
    void queryExpressInfoError(String message);

    /**
     * 正在加载
     *
     * @param message
     */
    void showLoadingDialog(String message);

    /**
     * 取消加载
     */
    void dismissLoadingDialog();


}
