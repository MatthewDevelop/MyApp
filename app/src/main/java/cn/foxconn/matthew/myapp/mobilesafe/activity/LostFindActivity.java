package cn.foxconn.matthew.myapp.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.activity.BaseActivity;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;

/**
 * @author:Matthew
 * @date:2018/2/3
 * @email:guocheng0816@163.com
 */

public class LostFindActivity extends BaseActivity {
    @BindView(R.id.tv_protect_num)
    TextView tvProtectNum;
    @BindView(R.id.iv_isProtect)
    ImageView ivProtect;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isConfiged = preferences.getBoolean("configed", false);
        if (isConfiged) {
            setContentView(R.layout.activity_lost_find);
            translucentBar(AppConst.THEME_COLOR);
            ButterKnife.bind(this);
            String phone = preferences.getString("safe_phone", "");
            tvProtectNum.setText(phone);
            boolean isProtected = preferences.getBoolean("protect", false);
            if (isProtected) {
                ivProtect.setImageResource(R.drawable.lock);
            } else {
                ivProtect.setImageResource(R.drawable.unlock);
            }
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reEnter:
                startActivity(new Intent(this, Setup1Activity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
