package cn.foxconn.matthew.myapp.activity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.service.UpdateService;
import cn.foxconn.matthew.myapp.utils.LogUtil;
import cn.foxconn.matthew.myapp.utils.StreamUtil;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_UPDATE = 5;
    private static final int TYPE_ERR_NET = 1;
    private static final int TYPE_ERR_URL = 2;
    private static final int TYPE_ERR_JSON = 3;
    private static final int TYPE_ENTER = 4;
    private static String mVersionName;
    private static String mDes;
    private static String mDownLoadUrl;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.text_progress)
    TextView tvProgress;
    @BindView(R.id.rl_root)
    RelativeLayout mRlRoot;
    private int mVersionCode;
    private ExecutorService fixedThreadPool;

    private SharedPreferences mPref;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //全屏不现实状态栏
        //方式一
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //方式二
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //方式三 style.xml中配置
        //<style name="fullScreen" parent="Theme.AppCompat.DayNight.NoActionBar">
        //        <item name="android:windowFullscreen">true</item>
        //</style>
        ButterKnife.bind(this);
        mHandler = new MyHandler(this);
        tvVersion.setText("版本号：" + getVersionName());
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        copyDb("address.db");
        boolean isAutoUpdate = mPref.getBoolean("auto_update", true);
        ThreadFactory checkUpdateFactory = new ThreadFactoryBuilder().setNameFormat("checkUpdate-thread").build();
        fixedThreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), checkUpdateFactory);
        if (isAutoUpdate) {
            Log.e(TAG, "onCreate: 检查版本" );
            checkVersion();
        } else {
            mHandler.sendEmptyMessageDelayed(TYPE_ENTER, 2000);
        }

        AlphaAnimation anim = new AlphaAnimation(0.3f, 1);
        anim.setDuration(2000);
        mRlRoot.startAnimation(anim);
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            //获取包的信息
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            LogUtil.e(TAG, versionName);
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 拷贝数据库到data/data/...
     */
    private void copyDb(String dbName) {
        File destFile = new File(getFilesDir(), dbName);
        if (destFile.exists()){
            Log.e(TAG, "copyDb: 数据库已经存在" );
            return;
        }
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        try {
            inputStream = getAssets().open(dbName);
            fileOutputStream = new FileOutputStream(destFile);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查更新
     */
    //TODO android.permission.INTERNET
    private void checkVersion() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                Message msg = Message.obtain();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("https://matthewdev.tech/files/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    Log.e(TAG, "run: " + responseCode);
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String result = StreamUtil.readFromStream(inputStream);
                        //LogUtil.e(TAG,result);
                        JSONObject jsonObject = new JSONObject(result);
                        //TODO 待优化
                        mVersionName = jsonObject.getString("VersionName");
                        mVersionCode = jsonObject.getInt("VersionCode");
                        mDes = jsonObject.getString("Des");
                        mDownLoadUrl = jsonObject.getString("DownLoadUrl");
//                        LogUtil.e(TAG,mVersionName);
                        if (mVersionCode > getVersionCode()) {
                            //showUpdateDialog();
                            msg.what = TYPE_UPDATE;
                        } else {
                            //enterHome();
                            msg.what = TYPE_ENTER;
                        }
                    }
                } catch (MalformedURLException e) {
                    msg.what = TYPE_ERR_URL;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = TYPE_ERR_NET;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = TYPE_ERR_JSON;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;
                    if (timeUsed < 2000) {
                        try {
                            Thread.sleep(2000 - timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                    Log.e(TAG, "run: " + msg.what);
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        };
        fixedThreadPool.execute(runnable);
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            //获取包的信息
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            LogUtil.e(TAG, "" + versionCode);
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fixedThreadPool != null) {
            fixedThreadPool.shutdown();
            fixedThreadPool = null;
        }
    }

    /**
     * activity跳转回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跳转主页面
     */
    private void enterHome() {
        /**
         * 页面跳转前退出全屏界面
         * 全屏页面跳转到非全屏页面，会先显示页面内容，然后状态栏出现，整个页面下移一个状态栏的高度，导致页面会卡一下
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 升级对话框
     */
    private void showUpdateDialog() {
        //使用getApplicationContext会出错
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本：" + mVersionName);
        builder.setMessage(mDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                downloadUpdate();
                Intent intent=new Intent(SplashActivity.this,UpdateService.class);
                intent.putExtra("URL", mDownLoadUrl);
                intent.putExtra("VersionName", mVersionName);
                SplashActivity.this.startService(intent);
                enterHome();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 下载更新并安装
     */
    //TODO android.permission.WRITE_EXTERNAL_STORAGE
    private void downloadUpdate() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tvProgress.setVisibility(View.VISIBLE);
            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(mDownLoadUrl, target, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    LogUtil.e(TAG, "current:" + current + ", total:" + total);
                    tvProgress.setText("下载进度：" + current * 100 / total + "%");
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    LogUtil.e(TAG, "成功");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result),
                            "application/vnd.android.package-archive");
//                    startActivity(intent);
                    //用户取消安装会回掉onActivityResult
                    startActivityForResult(intent, 0);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    LogUtil.e(TAG, "失败");
                }
            });
        } else {
            Toast.makeText(this, "设备没有Sdcard", Toast.LENGTH_SHORT).show();
        }
    }

    private static class MyHandler extends Handler {

        private final WeakReference<SplashActivity> mActivityWeakReference;

        private MyHandler(SplashActivity splashActivity) {
            mActivityWeakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivityWeakReference == null) {
                return;
            }
            switch (msg.what) {
                case TYPE_UPDATE:
                    mActivityWeakReference.get().showUpdateDialog();
                    break;
                case TYPE_DEFAULT:
                case TYPE_ERR_NET:
                    Toast.makeText(App.getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                    mActivityWeakReference.get().enterHome();
                    break;
                case TYPE_ERR_URL:
                    Toast.makeText(App.getContext(), "Url异常", Toast.LENGTH_SHORT).show();
                    mActivityWeakReference.get().enterHome();
                    break;
                case TYPE_ERR_JSON:
                    Toast.makeText(App.getContext(), "解析异常", Toast.LENGTH_SHORT).show();
                    mActivityWeakReference.get().enterHome();
                    break;
                case TYPE_ENTER:
                    mActivityWeakReference.get().enterHome();
                    break;
                default:
                    break;
            }
        }
    }
}
