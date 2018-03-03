package cn.foxconn.matthew.mobilesafe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.app.AppConst;
import cn.foxconn.matthew.mobilesafe.model.pojo.ArticleBean;
import cn.foxconn.matthew.mobilesafe.utils.PrefUtil;
import cn.foxconn.matthew.mobilesafe.utils.ToastUtil;
import cn.foxconn.matthew.mobilesafe.utils.UIUtil;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;

    public ArticleListAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.item_article, data);
        mContext = context;
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
                //collectArticle(tv_collect, item);
            }
        });
    }

    private void collectArticle(TextView tv_collect, ArticleBean item) {
        if (PrefUtil.getBoolean(mContext, AppConst.IS_LOGIN_KEY, false) == false) {
            ToastUtil.showShort(mContext, "请先登录");
            return;
        }

        //已经收藏，点击取消收藏
        if (item.isCollect()) {
            unCollectArticler(item, tv_collect);
        } else {
            collectArticler(item, tv_collect);
        }
    }

    private void collectArticler(ArticleBean item, TextView tv_collect) {

    }

    private void unCollectArticler(ArticleBean item, TextView tv_collect) {

    }
}
