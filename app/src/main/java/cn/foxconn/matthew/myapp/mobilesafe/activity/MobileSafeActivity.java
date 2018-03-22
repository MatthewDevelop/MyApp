package cn.foxconn.matthew.myapp.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.wanandroid.activity.WanAndroidActivity;
import cn.foxconn.matthew.myapp.utils.MD5Util;

/**
 * Created by Matthew on 2018/1/31.
 */

public class MobileSafeActivity extends AppCompatActivity {
    private static final String TAG = "MobileSafeActivity";
    @BindView(R.id.gridView)
    GridView mGridView;

    String[] names = new String[]{"手机防盗", "通讯卫士"
            , "软件管理", "进程管理"
            , "流量统计", "手机杀毒"
            , "缓存清理", "高级工具"
            , "设置中心"};
    int[] imagIds = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe
            , R.drawable.home_apps, R.drawable.home_taskmanager
            , R.drawable.home_netmanager, R.drawable.home_trojan
            , R.drawable.home_sysoptimize, R.drawable.home_tools
            , R.drawable.home_settings};
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        mGridView.setAdapter(new HomeAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        //手机防盗
                        showPasswordDialog();
                        break;
                    case 8:
                        //设置中心
                        startActivity(new Intent(MobileSafeActivity.this, SettingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 密码对话框
     */
    private void showPasswordDialog() {
        //判断是否设置密码
        String password = preferences.getString("password", null);
        //Log.e(TAG, "showPasswordDialog: " +password);
        if (password != null) {
            showPasswordInputDialog();
        } else {
            showPasswordSetDialog();
        }
    }

    /**
     * 密码验证对话框
     */
    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button btOk = view.findViewById(R.id.bt_ok);
        Button btCancel = view.findViewById(R.id.bt_cancel);
        final EditText etPassword = view.findViewById(R.id.et_password);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localPassword = preferences.getString("password", null);
                String password = etPassword.getText().toString();
                //判空 null和""
                if (!TextUtils.isEmpty(password)) {
                    if (MD5Util.encode(password).equals(localPassword)) {
                        Toast.makeText(MobileSafeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(MobileSafeActivity.this, LostFindActivity.class));
                    } else {
                        Toast.makeText(MobileSafeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MobileSafeActivity.this, "密码不为空哟！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    /**
     * 密码设置对话框
     */
    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button btOk = view.findViewById(R.id.bt_ok);
        Button btCancel = view.findViewById(R.id.bt_cancel);
        final EditText etPassword = view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = view.findViewById(R.id.et_password_confirm);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                //判空 null和""
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        Toast.makeText(MobileSafeActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                        preferences.edit().putString("password", MD5Util.encode(password)).commit();
                        dialog.dismiss();
                        startActivity(new Intent(MobileSafeActivity.this, LostFindActivity.class));
                    } else {
                        Toast.makeText(MobileSafeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MobileSafeActivity.this, "密码不为空哟！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    /**
     * gridView适配器
     */
    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.home_grid_item, parent, false);
            TextView itemName = view.findViewById(R.id.item_name);
            ImageView itemIcon = view.findViewById(R.id.item_icon);
            itemName.setText(names[position]);
            itemIcon.setImageResource(imagIds[position]);
            return view;
        }
    }
}
