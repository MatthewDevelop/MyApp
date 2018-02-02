package cn.foxconn.matthew.mobilesafe.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.view.SettingItemView;


public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.item_update)
    SettingItemView updateItem;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        //updateItem.setTitle("自动更新设置");
        boolean isAutoUpdate=preferences.getBoolean("auto_update",true);
        updateItem.setChecked(isAutoUpdate);
        //updateItem.setDes(isAutoUpdate ? "已开启":"已关闭");
        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateItem.isChecked()){
                    updateItem.setChecked(false);
                    //updateItem.setDes("已关闭");
                    preferences.edit().putBoolean("auto_update",false).commit();
                }else {
                    updateItem.setChecked(true);
                    //updateItem.setDes("已开启");
                    preferences.edit().putBoolean("auto_update",true).commit();
                }
            }
        });
    }
}
