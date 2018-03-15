package cn.foxconn.matthew.myapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.activity.MobileSafeActivity;
import cn.foxconn.matthew.myapp.utils.LogUtil;
import cn.foxconn.matthew.myapp.utils.StreamUtil;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int TYPE_UPDATE = 0;
    private static final int TYPE_ERR_NET = 1;
    private static final int TYPE_ERR_URL = 2;
    private static final int TYPE_ERR_JSON = 3;
    private static final int TYPE_ENTER = 4;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.text_progress)
    TextView tvProgress;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
    private String mVersionName;
    private int mVersionCode;
    private String mDes;
    private String mDownLoadUrl;
    //TODO 待优化
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TYPE_UPDATE:
                    showUpdateDialog();
                    break;
                case TYPE_ERR_NET:
                    Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case TYPE_ERR_URL:
                    Toast.makeText(SplashActivity.this, "Url异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case TYPE_ERR_JSON:
                    Toast.makeText(SplashActivity.this, "解析异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case TYPE_ENTER:
                    enterHome();
                    break;
                default:
                    break;
            }
        }
    };

    private SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvVersion.setText("版本号：" + getVersionName());
        mPref=getSharedPreferences("config",MODE_PRIVATE);
        boolean isAutoUpdate=mPref.getBoolean("auto_update",true);
        if(isAutoUpdate) {
            checkVersion();
        }else {
            mHandler.sendEmptyMessageDelayed(TYPE_ENTER,2000);
        }

        AlphaAnimation anim=new AlphaAnimation(0.3f,1);
        anim.setDuration(2000);
        rl_root.startAnimation(anim);
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

    /**
     * 检查更新
     */
    //TODO android.permission.INTERNET
    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                Message msg = Message.obtain();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://192.168.1.104:8080/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
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
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 跳转主页面
     */
    private void enterHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
                    tvProgress.setText("下载进度："+current*100/total+"%");
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    LogUtil.e(TAG, "成功");
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result),
                            "application/vnd.android.package-archive");
//                    startActivity(intent);
                    //用户取消安装会回掉onActivityResult
                    startActivityForResult(intent,0);
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

    /**
     * activity跳转回调
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
                downloadUpdate();
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
}
