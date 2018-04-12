package cn.foxconn.matthew.myapp.expressinquiry.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;

/**
 * @author:Matthew
 * @date:2018/4/12
 * @email:guocheng0816@163.com
 */
public class ExpressInfoAdapter extends BaseQuickAdapter<ExpressResponseData.DataBean, BaseViewHolder> {

    public ExpressInfoAdapter(@Nullable List<ExpressResponseData.DataBean> data) {
        super(R.layout.item_express_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressResponseData.DataBean item) {
        helper.setText(R.id.tv_time,item.getTime())
                .setText(R.id.tv_express_info,item.getContext());
    }
}
