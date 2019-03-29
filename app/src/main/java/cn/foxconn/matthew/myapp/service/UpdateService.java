package cn.foxconn.matthew.myapp.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;

import cn.foxconn.matthew.myapp.BuildConfig;
import cn.foxconn.matthew.myapp.utils.LogUtil;

/**
 * Author:Matthew
 * <p>
 * Date:2019/3/27 15:17
 * <p>
 * Email:guocheng0816@163.com
 * <p>
 * Desc:
 */
public class UpdateService extends Service {
    private static final String TAG = "UpdateService";
    private String mDownLoadUrl = "";
    private String mVersionName = "";
    private DownloadManager mDownloadManager = null;
    private long mTaskId = 0;
    private String mDownloadPath = "";
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownLoadStatus();
        }
    };

    private void checkDownLoadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    LogUtil.i(TAG, ">>>下载暂停");
                    break;
                case DownloadManager.STATUS_PENDING:
                    LogUtil.i(TAG, ">>>下载延迟");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    LogUtil.i(TAG, ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    LogUtil.i(TAG, ">>>下载完成");
                    //下载完成安装APK
                    installAPK(new File(mDownloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    LogUtil.i(TAG, ">>>下载失败");
                    break;
                default:
                    break;
            }
        }
    }

    //下载到本地后执行安装
    protected void installAPK(File file) {
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            //在服务中开启activity必须设置flag,后面解释
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        startActivity(intent);
        stopSelf();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        mDownloadManager = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        mDownLoadUrl = intent.getStringExtra("URL");
        mDownLoadUrl = "http://192.168.137.37:8080/app-release.apk";
        mVersionName = intent.getStringExtra("VersionName");
        mDownloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + mVersionName;
        downloadApk();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //使用系统下载器
    private void downloadApk() {
        Log.e(TAG, "downloadApk: ");
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDownLoadUrl));
        //漫游网络是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(mDownLoadUrl));
        request.setMimeType(mimeString);
        //设置在通知栏中显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        //设置下载路径
        request.setDestinationInExternalPublicDir("/download/", mVersionName);
//        request.setDestinationInExternalFilesDir(, , )也可以自定义路径
        //将请求加入下载列队
        mTaskId = mDownloadManager.enqueue(request);
    }
}
