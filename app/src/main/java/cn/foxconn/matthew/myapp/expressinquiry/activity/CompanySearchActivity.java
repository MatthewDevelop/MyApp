package cn.foxconn.matthew.myapp.expressinquiry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.activity.BaseActivity;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.expressinquiry.adapter.SortAdapter;
import cn.foxconn.matthew.myapp.expressinquiry.bean.SortBean;
import cn.foxconn.matthew.myapp.expressinquiry.utils.PinyinComparator;
import cn.foxconn.matthew.myapp.expressinquiry.widget.SideBar;
import cn.foxconn.matthew.myapp.utils.CharacterParser;

/**
 * @author:Matthew
 * @date:2018/4/14
 * @email:guocheng0816@163.com
 */
public class CompanySearchActivity extends BaseActivity {
    @BindView(R.id.lv_company)
    ListView mListView;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.et_name)
    EditText mEditText;
    @BindView(R.id.tv_dialog)
    TextView mTvDialog;
    List<String> mCompanyNames;
    private SortAdapter mSortAdapter;
    /**
     * 汉字转化为拼音
     */
    private CharacterParser mParser;
    private List<SortBean> mSortBeans;
    /**
     * 拼音排序
     */
    private PinyinComparator mComparator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        translucentBar(AppConst.THEME_COLOR);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mCompanyNames = intent.getStringArrayListExtra(AppConst.COMPANY_NAMES);
        init();
    }

    private void init() {
        mParser = new CharacterParser();
        mComparator = new PinyinComparator();
        mSortBeans = sortData(mCompanyNames);
        Collections.sort(mSortBeans, mComparator);
        mSortAdapter = new SortAdapter(this, mSortBeans);
        mListView.setAdapter(mSortAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CompanySearchActivity.this,
//                        ((SortBean) mSortAdapter.getItem(position)).getName()
//                        , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(AppConst.SELECTED_COMPANY, ((SortBean) mSortAdapter.getItem(position)).getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mSideBar.setTextViewDialog(mTvDialog);
        //滑动条触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchLetterChanged(String s) {
                int position = mSortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });

        //根据输入框输入的值过滤结果
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入框为空时显示原列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 解析首字母，整理数据
     *
     * @param names
     * @return
     */
    private List<SortBean> sortData(List<String> names) {
        List<SortBean> sortBeans = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            SortBean sortBean = new SortBean();
            sortBean.setName(names.get(i));
            //汉字转换成拼音
            String pinyin = mParser.getSpelling(names.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortBean.setSortLetters(sortString);
            } else {
                sortBean.setSortLetters("#");
            }
            sortBeans.add(sortBean);
        }
        return sortBeans;
    }

    /**
     * 根据输入框中的值过滤出有效的数据并更新listView
     *
     * @param s
     */
    private void filterData(String s) {
        List<SortBean> filterData = new ArrayList<>();
        if (TextUtils.isEmpty(s)) {
            filterData = mSortBeans;
        } else {
            filterData.clear();
            for (SortBean sortBean : mSortBeans) {
                String name = sortBean.getName();
                if (name.toUpperCase().indexOf(s.toString().toUpperCase()) != -1
                        || mParser.getSpelling(name).toUpperCase().startsWith(s.toString().toUpperCase())) {
                    filterData.add(sortBean);
                }
            }
        }
        Collections.sort(filterData, mComparator);
        mSortAdapter.updateList(filterData);
    }

    @OnClick({R.id.ft_return, R.id.ft_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ft_return:
                finish();
                break;
            case R.id.ft_clear:
                mEditText.setText("");
                break;
            default:
                break;
        }
    }

}
