package cn.foxconn.matthew.myapp.expressinquiry.model;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import io.reactivex.Observer;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public interface DataModel {

    /**
     * 查询物流信息
     *
     * @param type
     * @param postId
     * @param provider
     * @param observer
     */
    void getExpressInfo(String type, String postId
            , LifecycleProvider<ActivityEvent> provider, Observer<ExpressResponseData> observer);
}
