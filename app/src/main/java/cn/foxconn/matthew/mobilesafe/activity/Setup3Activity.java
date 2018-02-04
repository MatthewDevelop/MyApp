package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.foxconn.matthew.mobilesafe.R;

public class Setup3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                startActivity(new Intent(this,Setup4Activity.class));
                finish();
                overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
                break;
            case R.id.bt_previous:
                startActivity(new Intent(this, Setup2Activity.class));
                finish();
                overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
                break;
        }
    }
}
