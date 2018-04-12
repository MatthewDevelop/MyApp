package cn.foxconn.matthew.myapp.expressinquiry.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.expressinquiry.adapter.ExpressInfoAdapter;
import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import cn.foxconn.matthew.myapp.expressinquiry.presenter.ExpressQueryPresenter;
import cn.foxconn.matthew.myapp.expressinquiry.view.ExpressQueryView;
import cn.foxconn.matthew.myapp.wanandroid.widget.FontTextView;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class ExpressQueryActivity extends RxAppCompatActivity implements ExpressQueryView {
    private static final String TAG = "ExpressQueryActivity";
    @BindView(R.id.ft_return)
    FontTextView mFtReturm;
    @BindView(R.id.sp_company)
    Spinner mSpCompany;
    @BindView(R.id.et_post_id)
    EditText mEtPostId;
    @BindView(R.id.bt_query)
    Button mBtQuery;
    @BindView(R.id.rv_express_info)
    RecyclerView mRvExpressInfo;
    @BindView(R.id.load_error)
    RelativeLayout mRlLoadError;
    @BindView(R.id.ft_refresh)
    FontTextView mFtRefresh;
    @BindView(R.id.tv_error_message)
    TextView mTvErrorMessage;

    private ExpressQueryPresenter mPresenter;
    private List<String> mCompanyNames;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String mCurrentCompanyCode;
    private String mCurrentPostId;
    private ProgressDialog mProgressDialog;
    private List<ExpressResponseData.DataBean> mExpressInfo;
    private ExpressInfoAdapter mExpressInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_query);
        ButterKnife.bind(this);
        init();
        initView();
        initListener();
        initData();
    }

    private void init() {
        mPresenter = new ExpressQueryPresenter(this, this);
        mCompanyNames = new ArrayList<>();
        mSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCompanyNames);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpCompany.setAdapter(mSpinnerAdapter);
        mExpressInfo=new ArrayList<>();
        mExpressInfoAdapter=new ExpressInfoAdapter(mExpressInfo);
        mRvExpressInfo.setLayoutManager(new LinearLayoutManager(this));
        mRvExpressInfo.setAdapter(mExpressInfoAdapter);
    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private void initListener() {
        mSpCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentCompanyCode = mPresenter.getCurrentCompanyCode(mCompanyNames.get(position));
                Log.e(TAG, "onItemSelected: " + position+" "+mCurrentCompanyCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    private void initData() {
        mPresenter.getCompanyData();
    }

    @OnClick({R.id.ft_return, R.id.bt_query,R.id.ft_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ft_return:
                finish();
                break;
            case R.id.bt_query:
                mCurrentPostId = mEtPostId.getText().toString();
                if (mCurrentPostId == null || "".equals(mCurrentPostId)) {
                    Toast.makeText(this, "快递单号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.getExpressInfo(mCurrentCompanyCode, mCurrentPostId);
                break;
            case R.id.ft_refresh:
                mPresenter.getExpressInfo(mCurrentCompanyCode,mCurrentPostId);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadCompanyNameSuccess(List<String> company_names) {
        mCompanyNames.clear();
        mCompanyNames.addAll(company_names);
        mSpinnerAdapter.notifyDataSetChanged();
        //mSpCompany.setSelection(316);
    }

    @Override
    public void queryExpressInfoSuccess(List<ExpressResponseData.DataBean> dataBeans) {
        Log.e(TAG, "queryExpressInfoSuccess: " );
        mRvExpressInfo.setVisibility(View.VISIBLE);
        mRlLoadError.setVisibility(View.GONE);
        Log.e(TAG, dataBeans.toString());
        mExpressInfo.clear();
        mExpressInfo.addAll(dataBeans);
        mExpressInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryExpressInfoError(String message) {
        mRvExpressInfo.setVisibility(View.GONE);
        mRlLoadError.setVisibility(View.VISIBLE);
        mTvErrorMessage.setText(message);
    }

    @Override
    public void showLoadingDialog(String message) {
        mExpressInfo.clear();
        mExpressInfoAdapter.notifyDataSetChanged();
        mRvExpressInfo.setVisibility(View.VISIBLE);
        mRlLoadError.setVisibility(View.GONE);
        mProgressDialog.setTitle(message);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
