package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.foxconn.matthew.mobilesafe.R;

public class Setup2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                startActivity(new Intent(this,Setup3Activity.class));
                finish();
                overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
                break;
            case R.id.bt_previous:
                startActivity(new Intent(this, Setup1Activity.class));
                overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
                finish();
                break;
        }
    }
}
