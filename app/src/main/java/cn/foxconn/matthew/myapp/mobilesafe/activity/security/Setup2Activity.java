package cn.foxconn.matthew.myapp.mobilesafe.activity.security;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.BaseSetupActivity;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.mobilesafe.widget.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {
    private static final String TAG = "Setup2Activity";
    @BindView(R.id.siv_sim)
    SettingItemView sivSim;

    @Override
    protected void init() {
        super.init();
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
                    preference.edit().remove("sim").apply();
                }else {
                    sivSim.setChecked(true);
                    TelephonyManager tm= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    //TODO 获取android.permission.READ_PHONE_STATE
                    String serialNum= null;
                    if (tm != null) {
                        serialNum = tm.getSimSerialNumber();
                    }
//                    LogUtil.e(TAG,serialNum);
                    //将SIM卡序列号保存
                    preference.edit().putString("sim",serialNum).apply();
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

    @Override
    protected int getContentResId() {
        return R.layout.activity_setup2;
    }

}
