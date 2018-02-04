package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.foxconn.matthew.mobilesafe.R;

public class Setup4Activity extends BaseSetupActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this,LostFindActivity.class));
        finish();
        preference.edit().putBoolean("configed",true).commit();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }
}
