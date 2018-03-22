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
import cn.foxconn.matthew.myapp.wanandroid.helper.BaseRxObserverHelper;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModel;
import cn.foxconn.matthew.myapp.wanandroid.model.DataModelImpl;
import cn.foxconn.matthew.myapp.wanandroid.activity.WebViewActivity;
import cn.foxconn.matthew.myapp.utils.ToastUtil;
import cn.foxconn.matthew.myapp.utils.UIUtil;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */

public class CollectAtrcicleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    DataModel mDataModel;
    private Context mContext;
    TextView tvNoCollect;
    CompositeDisposable mCompositeDisposable;

    public CollectAtrcicleAdapter(Context context, @Nullable List<ArticleBean> data, TextView tvNoCollect, CompositeDisposable compositeDisposable) {
        super(R.layout.item_article, data);
        mContext = context;
        mDataModel = new DataModelImpl();
        this.tvNoCollect = tvNoCollect;
        mCompositeDisposable=compositeDisposable;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());
        //判断文章是否被收藏
        final TextView tvCollect = helper.getView(R.id.tv_collect);
        tvCollect.setText(UIUtil.getString(R.string.ic_collect_sel));
        tvCollect.setTextColor(UIUtil.getColor(R.color.main));
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //collectArticle(tv_collect, item);
                unCollectArticler(helper.getAdapterPosition(), item, tvCollect);
            }
        });
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(mContext, item.getLink());
            }
        });
    }


    private void unCollectArticler(final int position, ArticleBean item, final TextView tvCollect) {
        BaseRxObserverHelper<String> disposableObserver=new BaseRxObserverHelper<String>() {
            @Override
            protected void next() {
                super.next();
                ToastUtil.showShort(mContext, "取消成功");
                getData().remove(position);
                //防止下标越界
                if (position == 0 && getData().size() == 0) {
                    notifyDataSetChanged();
                    tvNoCollect.setVisibility(View.VISIBLE);
                } else {
                    notifyItemRemoved(position);
                }
            }

            @Override
            protected void next(String s) {
                ToastUtil.showShort(mContext, "取消成功");
                getData().remove(position);
                //防止下标越界
                if (position == 0 && getData().size() == 0) {
                    notifyDataSetChanged();
                    tvNoCollect.setVisibility(View.VISIBLE);
                } else {
                    notifyItemRemoved(position);
                }
            }

            @Override
            protected void error(String message) {
                ToastUtil.showShort(mContext, "取消失败");
            }
        };
        mDataModel.unCollectArticle(item.getId(), item.getOriginId(),disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }
}
