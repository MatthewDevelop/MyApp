package cn.foxconn.matthew.myapp.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.wanandroid.bean.pojo.ArticleBean;
import cn.foxconn.matthew.myapp.wanandroid.helper.RxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.activity.WebViewActivity;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.utils.UIUtil;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public class CollectAtrcicleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    DataModel mDataModel;
    private Context mContext;
    TextView tv_no_collect;

    public CollectAtrcicleAdapter(Context context, @Nullable List<ArticleBean> data, TextView tv_no_collect) {
        super(R.layout.item_article, data);
        mContext = context;
        mDataModel = new DataModelImpl();
        this.tv_no_collect = tv_no_collect;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());
        //判断文章是否被收藏
        final TextView tv_collect = helper.getView(R.id.tv_collect);
        tv_collect.setText(UIUtil.getString(R.string.ic_collect_sel));
        tv_collect.setTextColor(UIUtil.getColor(R.color.main));
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //collectArticle(tv_collect, item);
                unCollectArticler(helper.getAdapterPosition(), item, tv_collect);
            }
        });
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(mContext, item.getLink());
            }
        });
    }


    private void unCollectArticler(final int position, ArticleBean item, final TextView tv_collect) {
        mDataModel.unCollectArticle(item.getId(), item.getOriginId(), new RxObserverHelper<String>() {
            @Override
            protected void _onNext(String s) {
                ToastUtil.showShort(mContext, "取消成功");
                getData().remove(position);
                //防止下标越界
                if (position == 0 && getData().size() == 0) {
                    notifyDataSetChanged();
                    tv_no_collect.setVisibility(View.VISIBLE);
                } else {
                    notifyItemRemoved(position);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(mContext, "取消失败");
            }
        });
    }
}
