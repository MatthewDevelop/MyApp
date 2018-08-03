package cn.foxconn.matthew.myapp.mobilesafe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import cn.foxconn.matthew.myapp.mobilesafe.receiver.SmsReceiver;

/**
 * @author:Matthew
 * @date:2018/8/3
 * @email:guocheng0816@163.com
 * @func:获取经纬度坐标的Service
 */
public class LocationService extends Service {

    private LocationManager mLocationManager;
    private MyLocationListener mListener;
    private SharedPreferences mPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mPreferences = getSharedPreferences("config", MODE_PRIVATE);
        //获取定位服务
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mListener=new MyLocationListener();
//        List<String> locationProviders = mLocationManager.getAllProviders();
//        System.out.println(locationProviders);
        //设置定位标准
        Criteria criteria=new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider=mLocationManager.getBestProvider(criteria, true);
        //TODO android.permission.ACCESS_COARSE_LOCATION android.permission.ACCESS_FINE_LOCATION
        mLocationManager.requestLocationUpdates(bestProvider, 0, 0,mListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //移除监听服务
        mLocationManager.removeUpdates(mListener);
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String longitude = "经度：" + location.getLongitude();
            String latitude = "纬度：" + location.getLatitude();
            String accuracy = "精确度" + location.getAccuracy();
            String altitude = "海拔" + location.getAltitude();
            System.out.println(longitude);
            System.out.println(latitude);
            System.out.println(accuracy);
            System.out.println(altitude);
            //将获取的经纬度保存到SP中
            mPreferences.edit().putString("Location", longitude+","+latitude).apply();
            //拿到经纬度后停止service
            stopSelf();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
