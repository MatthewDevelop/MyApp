package cn.foxconn.matthew.myapp.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.activity.WebViewActivity;
import cn.foxconn.matthew.myapp.utils.PrefUtil;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.utils.UIUtil;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;
    private DataModel mDataModel;
    private CompositeDisposable mCompositeDisposable;

    public ArticleListAdapter(Context context, @Nullable List<ArticleBean> data, CompositeDisposable compositeDisposable) {
        super(R.layout.item_article, data);
        mContext = context;
        mDataModel=new DataModelImpl();
        mCompositeDisposable=compositeDisposable;
    }


    @Override
    protected void convert(BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());

        //判断文章是否被收藏
        final TextView tv_collect = helper.getView(R.id.tv_collect);
        if (item.isCollect()) {
            tv_collect.setText(UIUtil.getString(R.string.ic_collect_sel));
            tv_collect.setTextColor(UIUtil.getColor(R.color.main));
        } else {
            tv_collect.setText(UIUtil.getString(R.string.ic_collect_nor));
            tv_collect.setTextColor(UIUtil.getColor(R.color.text3));
        }

        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectArticle(item);
            }
        });
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(mContext,item.getLink());
            }
        });
    }

    private void collectArticle(ArticleBean item) {
        if (PrefUtil.getBoolean(mContext, AppConst.IS_LOGIN_KEY, false) == false) {
            ToastUtil.showShort(mContext, "请先登录");
            return;
        }

        //已经收藏，点击取消收藏
        if (item.isCollect()) {
            unCollectArticler(item);
        } else {
            collectArticler(item);
        }
    }

    private void collectArticler(final ArticleBean item) {
        RxObserverHelper<String> rxObserverHelper=new RxObserverHelper<String>() {

            @Override
            protected void _onNext() {
                super._onNext();
                ToastUtil.showShort(mContext,"收藏成功");
                item.setCollect(true);
                notifyDataSetChanged();
            }

            @Override
            protected void _onNext(String s) {
                ToastUtil.showShort(mContext,"收藏成功");
                item.setCollect(true);
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(mContext,"收藏失败");
            }
        };
        mDataModel.collectArticleInHomeList(item.getId(),rxObserverHelper);
        mCompositeDisposable.add(rxObserverHelper);
    }

    private void unCollectArticler(final ArticleBean item) {
        RxObserverHelper<String> rxObserverHelper=new RxObserverHelper<String>() {
            @Override
            protected void _onNext() {
                super._onNext();
                ToastUtil.showShort(mContext,"取消成功");
                item.setCollect(false);
                notifyDataSetChanged();
            }

            @Override
            protected void _onNext(String s) {
                ToastUtil.showShort(mContext,"取消成功");
                item.setCollect(false);
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(mContext,"取消失败");
            }
        };
        mDataModel.unCollectArticleInHomeList(item.getId(),rxObserverHelper );
        mCompositeDisposable.add(rxObserverHelper);
    }
}
