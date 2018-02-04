package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;

public class Setup3Activity extends BaseSetupActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        ButterKnife.bind(this);
        String phone=preference.getString("safe_phone","");
        et_phone.setText(phone);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
    }

    @Override
    public void showNextPage() {
        String phone=et_phone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
//            Toast.makeText(this, "安全号码不为空", Toast.LENGTH_SHORT).show();
            ToastUtil.show(this,"安全号码不为空");
            return;
        }else {
            preference.edit().putString("safe_phone",phone).commit();
            startActivity(new Intent(this, Setup4Activity.class));
            finish();
            overridePendingTransition(R.anim.trans_in, R.anim.trans_out);
        }
    }

    public void selectContact(View view){
        Intent intent=new Intent(this,ContactActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            String phone = data.getStringExtra("phone");
            phone = phone.replaceAll("-", "").replaceAll(" ", "");
            et_phone.setText(phone);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
