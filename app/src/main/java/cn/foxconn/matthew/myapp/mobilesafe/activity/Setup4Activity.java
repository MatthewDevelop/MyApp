package cn.foxconn.matthew.myapp.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;

public class Setup4Activity extends BaseSetupActivity {
    @BindView(R.id.cb_protect)
    CheckBox mCbProtect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        ButterKnife.bind(this);
        boolean isProtect=preference.getBoolean("protect",false);
        if(isProtect){
            mCbProtect.setText("防盗保护已经开启");
        }else {
            mCbProtect.setText("防盗保护未开启");
        }
        mCbProtect.setChecked(isProtect);
        mCbProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mCbProtect.setText("防盗保护已经开启");
                    preference.edit().putBoolean("protect",true).commit();
                }else {
                    mCbProtect.setText("防盗保护未开启");
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
