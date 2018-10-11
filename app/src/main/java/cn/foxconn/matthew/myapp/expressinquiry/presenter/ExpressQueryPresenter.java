package cn.foxconn.matthew.myapp.expressinquiry.presenter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.expressinquiry.bean.ExpressResponseData;
import cn.foxconn.matthew.myapp.expressinquiry.model.DataModel;
import cn.foxconn.matthew.myapp.expressinquiry.model.DataModelImpl;
import cn.foxconn.matthew.myapp.expressinquiry.view.ExpressQueryView;
import cn.foxconn.matthew.myapp.helper.MyDatabaseHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class ExpressQueryPresenter {
    private static final String TAG = "ExpressQueryPresenter";

    DataModel mDataModel;
    private LifecycleProvider<ActivityEvent> mProvider;
    private ExpressQueryView mQueryView;
    private MyDatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;


    public ExpressQueryPresenter(ExpressQueryView expressQueryView, LifecycleProvider<ActivityEvent> provider) {
        mQueryView = expressQueryView;
        mProvider = provider;
        mDataModel = new DataModelImpl();
        mHelper = new MyDatabaseHelper(App.getContext());
    }

    public void getCompanyData() {
        new MyAsyncTask().execute();
    }

    public void getExpressInfo(String companyCode, String postId) {
        mDataModel.getExpressInfo(companyCode, postId, mProvider, new Observer<ExpressResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {
                mQueryView.showLoadingDialog("正在查询,请稍后...");
            }

            @Override
            public void onNext(ExpressResponseData expressResponseData) {
                Log.e(TAG, expressResponseData.toString() );
                String message=expressResponseData.getMessage();
                if("ok".equals(message)){
                    mQueryView.queryExpressInfoSuccess(expressResponseData.getData());
                }else {
                    mQueryView.queryExpressInfoError(message);
                }
            }

            @Override
            public void onError(Throwable e) {
                mQueryView.dismissLoadingDialog();
                mQueryView.queryExpressInfoError("网络异常，点击重试");
            }

            @Override
            public void onComplete() {
                mQueryView.dismissLoadingDialog();
            }
        });
    }

    public String getCurrentCompanyCode(String companyName) {
        Cursor cursor = mDatabase.rawQuery("select company_code from company_data where company_name=?", new String[]{companyName});
        Log.e(TAG, "getCurrentCompanyCode: " + cursor.getCount());
        String currentCompanyCode="";
        if (cursor.moveToNext()) {
            currentCompanyCode=cursor.getString(cursor.getColumnIndex("company_code"));
        }
        cursor.close();
        return currentCompanyCode;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            mDatabase = mHelper.getWritableDatabase();
            List<String> strings = new ArrayList<>();
            Cursor cursor = mDatabase.rawQuery("select company_name from company_data", null);
            while (cursor.moveToNext()) {
                strings.add(cursor.getString(cursor.getColumnIndex("company_name")));
            }
            cursor.close();
            return strings;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            mQueryView.loadCompanyNameSuccess(strings);
        }
    }
}
