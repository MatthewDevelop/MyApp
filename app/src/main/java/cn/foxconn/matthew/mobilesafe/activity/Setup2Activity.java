package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.utils.LogUtil;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;
import cn.foxconn.matthew.mobilesafe.view.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {
    private static final String TAG = "Setup2Activity";
    @BindView(R.id.siv_sim)
    SettingItemView sivSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        ButterKnife.bind(this);
        String sim=preference.getString("sim",null);
        if(!TextUtils.isEmpty(sim)){
            sivSim.setChecked(true);
        }else {
            sivSim.setChecked(false);
        }
        sivSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sivSim.isChecked()){
                    sivSim.setChecked(false);
                    preference.edit().remove("sim").commit();
                }else {
                    sivSim.setChecked(true);
                    TelephonyManager tm= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    //TODO 获取android.permission.READ_PHONE_STATE
                    String serialNum=tm.getSimSerialNumber();
//                    LogUtil.e(TAG,serialNum);
                    //将SIM卡序列号保存
                    preference.edit().putString("sim",serialNum).commit();
                }
            }
        });
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
        String sim=preference.getString("sim",null);
        if (TextUtils.isEmpty(sim)){
            ToastUtil.show(this,"必须绑定SIM卡");
            return;
        }
        startActivity(new Intent(this,Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }

}
