package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.foxconn.matthew.mobilesafe.R;

/**
 * @author:Matthew
 * @date:2018/2/3
 * @email:guocheng0816@163.com
 */

public class Setup1Activity extends BaseSetupActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showPreviousPage() {
        //doNothing
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
        //设置跳转动画
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }
}
