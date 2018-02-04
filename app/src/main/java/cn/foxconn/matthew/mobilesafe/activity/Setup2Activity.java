package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cn.foxconn.matthew.mobilesafe.R;

public class Setup2Activity extends BaseSetupActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

    }

    /**
     * 展示上一页
     */
    @Override
    public void showPreviousPage(){
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
    }

    /**
     * 展示下一页
     */
    @Override
    public void showNextPage(){
        startActivity(new Intent(this,Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }

}
