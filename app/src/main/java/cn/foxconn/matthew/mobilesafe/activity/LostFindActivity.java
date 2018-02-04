package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LostFindActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isConfiged = preferences.getBoolean("configed", false);
        if (isConfiged) {
            setContentView(R.layout.activity_lost_find);
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.reEnter:
                startActivity(new Intent(this,Setup1Activity.class));
                finish();
                break;
        }
    }
}
