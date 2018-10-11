package cn.foxconn.matthew.myapp.mobilesafe.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;
import cn.foxconn.matthew.myapp.mobilesafe.service.AddressService;
import cn.foxconn.matthew.myapp.mobilesafe.service.CallSafeService;
import cn.foxconn.matthew.myapp.mobilesafe.service.RocketService;
import cn.foxconn.matthew.myapp.mobilesafe.widget.SettingItemCheckView;
import cn.foxconn.matthew.myapp.mobilesafe.widget.SettingItemClickView;
import cn.foxconn.matthew.myapp.utils.AdminManager;
import cn.foxconn.matthew.myapp.utils.ServiceUtils;

/**
 * @author:Matthew
 * @date:2018/2/3
 * @email:guocheng0816@163.com
 * @func:设置界面
 */
public class SettingActivity extends MobileSafeBaseActivity {
    @BindView(R.id.item_update)
    SettingItemCheckView updateItem;
    @BindView(R.id.item_device_admin)
    SettingItemCheckView deviceAdminItem;
    @BindView(R.id.item_address)
    SettingItemCheckView addressShownItem;
    @BindView(R.id.item_address_shown_theme)
    SettingItemClickView addressShownThemeItem;
    @BindView(R.id.item_address_shown_location)
    SettingItemClickView addressShownLocationItem;
    @BindView(R.id.item_accelerate_rocket)
    SettingItemCheckView accelerateRocketItem;
    @BindView(R.id.item_black_list_block)
    SettingItemCheckView blaclListBlockItem;
    private SharedPreferences preferences;

    @Override
    protected void init() {
        super.init();
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isAutoUpdate = preferences.getBoolean("auto_update", true);
        boolean isDeviceAdminOn = preferences.getBoolean("device_admin_on", false);
        boolean isBlackListBlockOn = preferences.getBoolean("black_list_block_on", false);
        boolean isAddressServiceRunning = ServiceUtils.isServiceRunning(this,
                "cn.foxconn.matthew.myapp.mobilesafe.service.AddressService");
        boolean isRocketServiceRunning = ServiceUtils.isServiceRunning(this,
                "cn.foxconn.matthew.myapp.mobilesafe.service.RocketService");
        int toastThemeNum = preferences.getInt("toast_theme", 0);
        updateItem.setChecked(isAutoUpdate);
        deviceAdminItem.setChecked(isDeviceAdminOn);
        addressShownItem.setChecked(isAddressServiceRunning);
        accelerateRocketItem.setChecked(isRocketServiceRunning);
        blaclListBlockItem.setChecked(isBlackListBlockOn);
        addressShownThemeItem.setTitle("归属地提示框主题");
        addressShownThemeItem.setDes(AppConst.TOAST_THEME_DES[toastThemeNum]);
        addressShownLocationItem.setTitle("归属地提示框位置");
        addressShownLocationItem.setDes("设置归属地提示框的位置");
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.item_device_admin, R.id.item_update,
            R.id.item_address, R.id.item_address_shown_theme, R.id.item_address_shown_location
            , R.id.item_accelerate_rocket,R.id.item_black_list_block})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_update:
                if (updateItem.isChecked()) {
                    updateItem.setChecked(false);
                    preferences.edit().putBoolean("auto_update", false).apply();
                } else {
                    updateItem.setChecked(true);
                    preferences.edit().putBoolean("auto_update", true).apply();
                }
                break;
            case R.id.item_device_admin:
                if (deviceAdminItem.isChecked()) {
                    deviceAdminItem.setChecked(false);
                    AdminManager.getInstance().removeActive();
                    preferences.edit().putBoolean("device_admin_on", false).apply();
                } else {
                    AdminManager.getInstance().activeAdmin(this);
                }
                break;
            case R.id.item_address:
                if (addressShownItem.isChecked()) {
                    addressShownItem.setChecked(false);
                    stopService(new Intent(this, AddressService.class));
                } else {
                    addressShownItem.setChecked(true);
                    startService(new Intent(this, AddressService.class));
                }
                break;
            case R.id.item_address_shown_theme:
                showThemeSelectDialog();
                break;
            case R.id.item_address_shown_location:
                startActivity(new Intent(this, DragViewActivity.class));
                break;
            case R.id.item_accelerate_rocket:
                if (accelerateRocketItem.isChecked()) {
                    accelerateRocketItem.setChecked(false);
                    stopService(new Intent(this, RocketService.class));
                } else {
                    accelerateRocketItem.setChecked(true);
                    startService(new Intent(this, RocketService.class));
                }
                break;
            case R.id.item_black_list_block:
                if (blaclListBlockItem.isChecked()) {
                    blaclListBlockItem.setChecked(false);
                    preferences.edit().putBoolean("black_list_block_on", false).apply();
                    stopService(new Intent(this, CallSafeService.class));
                } else {
                    blaclListBlockItem.setChecked(true);
                    preferences.edit().putBoolean("black_list_block_on", true).apply();
                    startService(new Intent(this, CallSafeService.class));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置归属地提示框主题
     */
    private void showThemeSelectDialog() {
        int currentTheme = preferences.getInt("toast_theme", 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示风格");
        builder.setSingleChoiceItems(AppConst.TOAST_THEME_DES, currentTheme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences.edit().putInt("toast_theme", which).apply();
                addressShownThemeItem.setDes(AppConst.TOAST_THEME_DES[which]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (AdminManager.getInstance().isAdminActived()) {
                    deviceAdminItem.setChecked(true);
                    preferences.edit().putBoolean("device_admin_on", true).apply();
                } else {
                    deviceAdminItem.setChecked(false);
                    preferences.edit().putBoolean("device_admin_on", false).apply();
                }
                break;
            default:
                break;
        }
    }
}
