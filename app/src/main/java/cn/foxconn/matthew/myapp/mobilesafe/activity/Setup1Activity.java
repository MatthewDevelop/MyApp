package cn.foxconn.matthew.myapp.mobilesafe.activity;

import android.content.Intent;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.BaseSetupActivity;

/**
 * @author:Matthew
 * @date:2018/2/3
 * @email:guocheng0816@163.com
 */

public class Setup1Activity extends BaseSetupActivity {

    @Override
    public void showPreviousPage() {
        //doNothing
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        //设置跳转动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_setup1;
    }
}
