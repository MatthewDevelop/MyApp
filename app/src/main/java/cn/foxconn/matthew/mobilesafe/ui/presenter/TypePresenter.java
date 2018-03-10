package cn.foxconn.matthew.mobilesafe.ui.presenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.bean.ResponseData;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.ArticleListVO;
import cn.foxconn.matthew.mobilesafe.bean.pojoVO.TypeTagVO;
import cn.foxconn.matthew.mobilesafe.helper.RxSubscribeHelper;
import cn.foxconn.matthew.mobilesafe.model.DataModel;
import cn.foxconn.matthew.mobilesafe.model.DataModelImpl;
import cn.foxconn.matthew.mobilesafe.ui.adapter.ArticleListAdapter;
import cn.foxconn.matthew.mobilesafe.ui.base.BasePresenter;
import cn.foxconn.matthew.mobilesafe.ui.view.TypeView;
import cn.foxconn.matthew.mobilesafe.utils.UIUtil;
import cn.foxconn.matthew.mobilesafe.widget.AutoLinefeedLayout;
import rx.Subscriber;

/**
 * @author:Matthew
 * @date:2018/3/8
 * @email:guocheng0816@163.com
 */

public class TypePresenter extends BasePresenter<TypeView> {
    private static final String TAG = "TypePresenter";
    private FragmentActivity mActivity;
    DataModel mDataModel;

    private TypeView mTypeView;
    private List<TypeTagVO> mDatas;
    private AutoLinefeedLayout llTag;
    private List<TextView> tagTvs;
    private int mId;//选中的tagId
    private int mTabSelect; //标记选中的Tab标签
    private int mTagSelect; //标记选中的Tag标签，用户设置背景色

    private int mCurrentPage;
    private ArticleListAdapter mAdapter;


    public TypePresenter(FragmentActivity activity){
        mDataModel=new DataModelImpl();
        mActivity=activity;
    }

    public void getTagData() {
        mTypeView=getView();
        mDataModel.getTagData(new RxSubscribeHelper<List<TypeTagVO>>() {
            @Override
            protected void _onNext(List<TypeTagVO> typeTagVOS) {
                mDatas=typeTagVOS;
                setTabUi();
                mTabSelect=0;
                mTagSelect=0;
                getServerData(mDatas.get(0).getChildren().get(0).getId());
            }

            @Override
            protected void _onError(String message) {
                System.out.println(message);
            }
        });
    }

    /**
     * 设置tab
     */
    public void setTabUi(){
        TabLayout tabLayout=mTypeView.getTabLayout();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (TypeTagVO bean:mDatas){
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTagUi(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (llTag != null && llTag.getVisibility() == View.VISIBLE) {
                    llTag.setVisibility(View.GONE);
                } else {
                    setTagUi(tab.getPosition());
                }
            }
        });
    }

    /**
     * 设置tag
     * @param position
     */
    private void setTagUi(final int position) {
        llTag=getView().getTagLayout();
        llTag.setVisibility(View.VISIBLE);
        llTag.removeAllViews();
        if(tagTvs==null){
            tagTvs=new ArrayList<>();
        }else {
            tagTvs.clear();
        }

        for(int i=0;i<mDatas.get(position).getChildren().size();i++){
            View view= LinearLayout.inflate(mActivity, R.layout.item_tag_layout,null);
            final TextView textView=view.findViewById(R.id.tv_item);
            textView.setText(mDatas.get(position).getChildren().get(i).getName());
            llTag.addView(view);
            tagTvs.add(textView);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTabSelect=position;
                    mTagSelect= finalI;
                    textView.setTextColor(UIUtil.getColor(R.color.white));
                    textView.setBackgroundResource(R.drawable.shape_tag_sel);
                    mId=mDatas.get(position).getChildren().get(finalI).getId();
                    getServerData(mId);
                }
            });
        }

        //设置选中的tag背景
        for (int j = 0; j < tagTvs.size(); j++) {
            if (position == mTabSelect && j == mTagSelect) {
                tagTvs.get(j).setTextColor(UIUtil.getColor(R.color.white));
                tagTvs.get(j).setBackgroundResource(R.drawable.shape_tag_sel);
            } else {
                tagTvs.get(j).setTextColor(UIUtil.getColor(R.color.text0));
                tagTvs.get(j).setBackgroundResource(R.drawable.shape_tag_nor);
            }
        }
    }

    /**
     * 根据选择的标签从服务器抓取数据
     * @param id
     */
    private void getServerData(int id) {
        mCurrentPage=0;
        mAdapter=getView().getAdapter();
        mDataModel.getTypeDataById(mCurrentPage, id, new RxSubscribeHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                if (articleListVO.getDatas()!=null){
                    getView().getRefreshDataSuccess(articleListVO.getDatas());
                    mTypeView.getTagLayout().setVisibility(View.GONE);
                }
            }

            @Override
            protected void _onError(String message) {
                getView().getDataError(message);
            }
        });
    }

    /**
     * 加载下一页
     */
    public void getMoreData() {
        mCurrentPage=mCurrentPage+1;
        mDataModel.getTypeDataById(mCurrentPage, mId, new RxSubscribeHelper<ArticleListVO>() {
            @Override
            protected void _onNext(ArticleListVO articleListVO) {
                getView().getMoreDataSuccess(articleListVO.getDatas());
            }

            @Override
            protected void _onError(String message) {
                getView().getDataError(message);
            }
        });
    }
}
