package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;

/**
 * Created by Matthew on 2018/1/31.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.gridView)
    GridView mGridView;

    String[] names = new String[]{"手机防盗", "手机防盗", "手机防盗", "手机防盗", "手机防盗", "手机防盗", "手机防盗", "手机防盗", "设置中心"};
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
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
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
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

    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button bt_ok = view.findViewById(R.id.bt_ok);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        final EditText et_password = view.findViewById(R.id.et_password);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localPassword=preferences.getString("password",null);
                String password = et_password.getText().toString();
                //判空 null和""
                if (!TextUtils.isEmpty(password)) {
                    if (password.equals(localPassword)) {
                        Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "密码不为空哟！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        dialog.setView(view, 0, 0, 0, 0);
        Button bt_ok = view.findViewById(R.id.bt_ok);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        final EditText et_password = view.findViewById(R.id.et_password);
        final EditText et_password_confirm = view.findViewById(R.id.et_password_confirm);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String passwordConfirm = et_password_confirm.getText().toString();
                //判空 null和""
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        Toast.makeText(MainActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                        preferences.edit().putString("password", password).commit();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "密码不为空哟！", Toast.LENGTH_SHORT).show();
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
            TextView textView = view.findViewById(R.id.item_name);
            textView.setText(names[position]);
            return view;
        }
    }
}
