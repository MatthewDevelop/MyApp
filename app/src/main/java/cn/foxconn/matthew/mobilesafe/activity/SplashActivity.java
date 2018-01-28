package cn.foxconn.matthew.mobilesafe.activity;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;
import cn.foxconn.matthew.mobilesafe.utils.LogUtil;
import cn.foxconn.matthew.mobilesafe.utils.StreamUtil;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int TYPE_UPDATE = 0;
    private static final int TYPE_ERR_NET = 1;
    private static final int TYPE_ERR_URL = 2;
    private static final int TYPE_ERR_JSON = 3;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private String mVersionName;
    private int mVersionCode;
    private String mDes;
    private String mDownLoadUrl;
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
                    break;
                case TYPE_ERR_URL:
                    Toast.makeText(SplashActivity.this, "Url异常", Toast.LENGTH_SHORT).show();
                    break;
                case TYPE_ERR_JSON:
                    Toast.makeText(SplashActivity.this, "解析异常", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvVersion.setText("版本号：" + getVersionName());
        checkVersion();
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
    private void checkVersion() {
        new Thread(new Runnable() {


            @Override
            public void run() {
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
                        mVersionName = jsonObject.getString("VersionName");
                        mVersionCode = jsonObject.getInt("VersionCode");
                        mDes = jsonObject.getString("Des");
                        mDownLoadUrl = jsonObject.getString("DownLoadUrl");
//                        LogUtil.e(TAG,mVersionName);
                        if (mVersionCode > getVersionCode()) {
                            //showUpdateDialog();
                            msg.what = TYPE_UPDATE;
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
                    mHandler.sendMessage(msg);
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本：" + mVersionName);
        builder.setMessage(mDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("以后再说", null);
        builder.show();
    }
}
