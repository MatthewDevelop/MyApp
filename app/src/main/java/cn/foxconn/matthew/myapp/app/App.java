package cn.foxconn.matthew.myapp.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.foxconn.matthew.myapp.expressinquiry.bean.CompanyData;
import cn.foxconn.matthew.myapp.helper.MyDatabaseHelper;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class App extends Application {

    private static final String TAG = "App";

    private static List<Activity> activities;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private SharedPreferences mPreferences;
    private ThreadPoolExecutor fixedThreadPool;

    public static List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        setActivitiesValue(activities);
    }

    /**
     * 退出程序
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        setActivities(new LinkedList<Activity>());
        ThreadFactory initDatabase = new ThreadFactoryBuilder().setNameFormat("init-database-thread").build();
        fixedThreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), initDatabase);
        initDataBase();
        initBugly();
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "9ef3850ba1", true);
    }

    private void initDataBase() {
        mPreferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isDbCreated = mPreferences.getBoolean(AppConst.IS_DATABASE_CREATED, false);
        if (!isDbCreated) {
            SQLiteDatabase database = new MyDatabaseHelper(getContext()).getWritableDatabase();
            initExpressCompanyData(database);
            mPreferences.edit().putBoolean(AppConst.IS_DATABASE_CREATED,true).apply();
        }
    }

    public static void setContextValue(Context contextValue) {
        mContext = contextValue;
    }

    public static void setActivitiesValue(List<Activity> activities) {
        App.activities = activities;
    }

    public static Context getContext() {
        return mContext;
    }

    private void initExpressCompanyData(final SQLiteDatabase database) {
        Log.e(TAG, "initExpressCompanyData: " );
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open("company.json");
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    CompanyData companyData = new Gson().fromJson(br, CompanyData.class);
                    List<CompanyData.CompanyBean> list = companyData.getRows();
                    if (list.size() != 0) {
                        database.beginTransaction();
                        for (CompanyData.CompanyBean companyBean : list) {
                            database.execSQL("insert into company_data(company_name,company_code) values(?,?)"
                                    , new String[]{companyBean.getCompany_name(), companyBean.getCompany_code()});
                        }
                        database.setTransactionSuccessful();
                        database.endTransaction();
                        Log.e(TAG, "数据初始化成功" );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(runnable);
    }

    public void setContext(Context context) {
        setContextValue(context);
    }
}
