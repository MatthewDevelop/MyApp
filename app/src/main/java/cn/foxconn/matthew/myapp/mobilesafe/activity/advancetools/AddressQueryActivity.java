package cn.foxconn.matthew.myapp.mobilesafe.activity.advancetools;

import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnTextChanged;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;
import cn.foxconn.matthew.myapp.mobilesafe.db.dao.AddressDao;

/**
 * @author:Matthew
 * @date:2018/8/11
 * @email:guocheng0816@163.com
 * @func:
 */
public class AddressQueryActivity extends MobileSafeBaseActivity {

    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void init() {
        super.init();
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btQuery.performClick();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_address_query;
    }

    /**
     * 查询归属地
     *
     * @param view
     */
    public void queryAddress(View view) {
        String number = etNumber.getText().toString();
        if (!TextUtils.isEmpty(number)) {
            String result = AddressDao.getAddress(number);
            tvResult.setText(result);
        } else {
            /**
             * 输入框抖动
             */
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etNumber.startAnimation(shake);
            vibrate();
        }
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(2000L);  //震动两秒
//        vibrator.vibrate(new long[]{1000L, 2000L, 1000L, 2000L}, -1);//规律震动停一秒震动两秒再停一秒震动两秒，参二-1不循环，参二0表示从头开始播放，参二其他数字表示从第几个位置开始循环
        //取消震动
//        vibrator.cancel();
    }
}
