package cn.foxconn.matthew.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.expressinquiry.activity.ExpressQueryActivity;
import cn.foxconn.matthew.myapp.helper.RetrofitServiceManager;
import cn.foxconn.matthew.myapp.mobilesafe.activity.MobileSafeActivity;
import cn.foxconn.matthew.myapp.test.TrackingMoreApi;
import cn.foxconn.matthew.myapp.test.CarrierBean;
import cn.foxconn.matthew.myapp.utils.NetworkUtil;
import cn.foxconn.matthew.myapp.wanandroid.activity.WanAndroidActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.gridView)
    GridView mGridView;

    //    String[] names = new String[]{"手机卫士", "玩Android","WebSocket"};
    String[] names = new String[]{"手机卫士", "玩Android", "快递查询",
            "Test"};
    int[] imageIds = new int[]{R.drawable.home_callmsgsafe, R.drawable.ic_launcher_round, R.drawable.ic_launcher_round,
            R.drawable.ic_launcher_round};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGridView.setAdapter(new HomeAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, MobileSafeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, WanAndroidActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, ExpressQueryActivity.class));
                        break;
                    case 3:
                        //startActivity(new Intent(MainActivity.this, WebSocketActivity.class));
                        /*new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RetrofitServiceManager.getInstance(AppConst.KUAIDI_100_BASE_URL)
                                        .create(ExpressService.class)
                                        .queryExpressInfo("yuantong", "888590141663223471")
                                        .subscribe(new Observer<ExpressResponseData>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ExpressResponseData expressResponseData) {
                                                System.out.println(expressResponseData.toString());
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        }).start();*/
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                String json = "{\n" +
//                                        "    \"tracking_number\": \"888590141663223471\"\n" +
//                                        "}";
//                                try {
////                                    Tracker.orderOnlineByJson(json, " /carriers/detect", "post");
////                                    Tracker.orderOnlineByJson("", "/carriers", "get");
////                                    Tracker.queryCompany(json);
//                                    RetrofitServiceManager.getExpressInstance()
//                                            .create(TrackingMoreApi.class)
//                                            .queryCarriers("8ddcc142-4540-4837-9bb0-0600f8c3ba36")
//                                            .subscribe(new Observer<CarrierBean>() {
//                                                @Override
//                                                public void onSubscribe(Disposable d) {
//
//                                                }
//
//                                                @Override
//                                                public void onNext(CarrierBean carrierBean) {
//                                                    Log.e(TAG, "onNext: "+carrierBean.getData().size() );
//                                                    Log.e(TAG, carrierBean.toString() );
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//
//                                                }
//
//                                                @Override
//                                                public void onComplete() {
//
//                                                }
//                                            });
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * gridView适配器
     */
    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.home_grid_item, parent, false);
            TextView itemName = view.findViewById(R.id.item_name);
            ImageView itemIcon = view.findViewById(R.id.item_icon);
            itemName.setText(names[position]);
            itemIcon.setImageResource(imageIds[position]);
            return view;
        }
    }
}
