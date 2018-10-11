package cn.foxconn.matthew.myapp.mobilesafe.activity.callguard;


import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;
import cn.foxconn.matthew.myapp.mobilesafe.base.MyBaseAdapter;
import cn.foxconn.matthew.myapp.mobilesafe.db.dao.BlackNumDao;
import cn.foxconn.matthew.myapp.mobilesafe.entity.BlackNumber;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.wanandroid.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/8/20
 * @email:guocheng0816@163.com
 * @func:通信卫士
 */
public class CallGuardActivity extends MobileSafeBaseActivity {
    private static final String TAG = "CallGuardActivity";
    @BindView(R.id.lv_black_list)
    ListView lvBlackList;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.et_page_num)
    EditText etPageNum;
    @BindView(R.id.tv_page_info)
    TextView tvPageInfo;
    private List<BlackNumber> mList;
    private int currentPageNum = 1;
    private int pageSize = 20;
    private int totalPageCount;
    private CallGuardAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    rlProgress.setVisibility(View.INVISIBLE);
                    tvPageInfo.setText(currentPageNum + "/" + totalPageCount);
                    mAdapter = new CallGuardAdapter(mList);
                    lvBlackList.setAdapter(mAdapter);
                    break;
                case 1:
                    rlProgress.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };
    private BlackNumDao mDao;

    @OnClick({R.id.bt_previous, R.id.bt_next, R.id.bt_jump, R.id.ft_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_previous:
                if (currentPageNum <= 1) {
                    ToastUtil.show("已经是第一页了!");
                    return;
                }
                currentPageNum--;
                initData();
                break;
            case R.id.bt_next:
                if (currentPageNum >= totalPageCount) {
                    ToastUtil.show("没有更多了!");
                    return;
                }
                currentPageNum++;
                initData();
                break;
            case R.id.bt_jump:
                try {
                    int destNum = Integer.parseInt(etPageNum.getText().toString());
                    if (destNum < 1 || destNum > totalPageCount) {
                        ToastUtil.show("请输入正确的页数!");
                        return;
                    }
                    currentPageNum = destNum;
                    initData();
                } catch (NumberFormatException e) {
                    ToastUtil.show("请输入正确的页数!");
                }
                break;
            case R.id.ft_add:
                final AlertDialog dialog = new AlertDialog.Builder(CallGuardActivity.this).create();
                View dailogContent = View.inflate(CallGuardActivity.this, R.layout.dailog_add_black_num, null);
                final CheckBox cbText = dailogContent.findViewById(R.id.cb_text);
                final CheckBox cbPhone = dailogContent.findViewById(R.id.cb_phone);
                final EditText etBlackNum = dailogContent.findViewById(R.id.et_black_num);
                Button btConfirm = dailogContent.findViewById(R.id.bt_confirm);
                Button btCancel = dailogContent.findViewById(R.id.bt_cancel);
                btConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String num = etBlackNum.getText().toString();
                        if (TextUtils.isEmpty(num)) {
                            ToastUtil.show("请输入正确的号码！");
                            return;
                        }
                        BlackNumber blackNumber = new BlackNumber();
                        blackNumber.setNumber(num);
                        String mode;
                        if (cbText.isChecked() && !cbPhone.isChecked()) {
                            mode = "2";
                        } else if (!cbText.isChecked() && cbPhone.isChecked()) {
                            mode = "1";
                        } else if (cbText.isChecked() && cbPhone.isChecked()) {
                            mode = "3";
                        } else {
                            ToastUtil.show("请设置拦截模式！");
                            return;
                        }
                        ToastUtil.show("添加成功");
                        blackNumber.setMode(mode);
                        mDao.add(num, mode);
                        mList.add(0, blackNumber);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(dailogContent);
                dialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        new Thread() {
            @Override
            public void run() {
                super.run();
                mHandler.sendEmptyMessage(1);
                int totalCount = mDao.queryTotalNumCount();
                Log.e(TAG, "run: " + totalCount);
                totalPageCount = totalCount % pageSize > 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
                mList = mDao.queryByPage(currentPageNum - 1, pageSize);
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    protected void init() {
        super.init();
        mDao = new BlackNumDao(CallGuardActivity.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_call_guard;
    }

    static class ViewHolder {
        TextView tvBlackNum;
        TextView tvGuardMode;
        FontTextView ftDelete;
    }

    class CallGuardAdapter extends MyBaseAdapter<BlackNumber> {

        public CallGuardAdapter(List<BlackNumber> list) {
            super(list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(CallGuardActivity.this, R.layout.item_black_num, null);
                viewHolder = new ViewHolder();
                viewHolder.tvBlackNum = convertView.findViewById(R.id.tv_black_num);
                viewHolder.tvGuardMode = convertView.findViewById(R.id.tv_guard_mode);
                viewHolder.ftDelete = convertView.findViewById(R.id.ft_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvBlackNum.setText(mList.get(position).getNumber());
            switch (mList.get(position).getMode()) {
                case "1":
                    viewHolder.tvGuardMode.setText("电话");
                    break;
                case "2":
                    viewHolder.tvGuardMode.setText("短信");
                    break;
                case "3":
                    viewHolder.tvGuardMode.setText("电话+短信");
                    break;
                default:
                    break;
            }
            viewHolder.ftDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlackNumDao dao = new BlackNumDao(CallGuardActivity.this);
                    boolean result = dao.delete(mList.get(position).getNumber());
                    if (result) {
                        ToastUtil.show("删除成功!");
                        mList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        ToastUtil.show("删除失败!");
                    }
                }
            });
            return convertView;
        }
    }


}
