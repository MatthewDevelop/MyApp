package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;

public class Setup4Activity extends BaseSetupActivity {
    @BindView(R.id.cb_protect)
    CheckBox cb_protect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        ButterKnife.bind(this);
        boolean isProtect=preference.getBoolean("protect",false);
        if(isProtect){
            cb_protect.setText("防盗保护已经开启");
        }else {
            cb_protect.setText("防盗保护未开启");
        }
        cb_protect.setChecked(isProtect);
        cb_protect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_protect.setText("防盗保护已经开启");
                    preference.edit().putBoolean("protect",true).commit();
                }else {
                    cb_protect.setText("防盗保护未开启");
                    preference.edit().putBoolean("protect",false).commit();
                }
            }
        });
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
