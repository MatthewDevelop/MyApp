package cn.foxconn.matthew.myapp.mobilesafe.activity.advancetools;

import android.content.Intent;
import android.view.View;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;

/**
 * @author:Matthew
 * @date:2018/8/4
 * @email:guocheng0816@163.com
 * @func:高级工具
 */
public class AdvanceToolsActivity extends MobileSafeBaseActivity {

    @Override
    protected int getContentResId() {
        return R.layout.activity_advance_tools;
    }

    /**
     * 电话归属地查询
     * @param view
     */
    public void numberAddressQuery(View view){
        startActivity(new Intent(this,AddressQueryActivity.class));
    }
}
