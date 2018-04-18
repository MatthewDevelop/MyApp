package cn.foxconn.matthew.myapp.expressinquiry.model;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import cn.foxconn.matthew.myapp.app.AppConst;
import cn.foxconn.matthew.myapp.expressinquiry.api.ExpressService;
import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import cn.foxconn.matthew.myapp.helper.RetrofitServiceManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class DataModelImpl implements DataModel {

     ExpressService mExpressService;

    public DataModelImpl(){
        mExpressService= RetrofitServiceManager.getInstance().create(ExpressService.class);
    }

    @Override
    public void getExpressInfo(String companyCode, String postId, LifecycleProvider<ActivityEvent> provider, Observer<ExpressResponseData> observer) {
        String url=AppConst.KUAIDI_100_BASE_URL+"query?type="+companyCode+"&postid="+postId;
        mExpressService.queryExpressInfo_(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.<ExpressResponseData>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(observer);
    }
}
