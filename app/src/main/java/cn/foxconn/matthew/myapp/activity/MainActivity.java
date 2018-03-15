package cn.foxconn.matthew.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import cn.foxconn.matthew.myapp.mobilesafe.activity.MobileSafeActivity;
import cn.foxconn.matthew.myapp.wanandroid.activity.WanAndroidActivity;

/**
 * @author:Matthew
 * @date:2018/3/2
 * @email:guocheng0816@163.com
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gridView)
    GridView mGridView;

    String[] names = new String[]{"手机卫士", "玩Android"};
    int[] imagIds = new int[]{R.drawable.home_callmsgsafe, R.drawable.ic_launcher_round};

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
                        startActivity(new Intent(MainActivity.this,MobileSafeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, WanAndroidActivity.class));
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
            itemIcon.setImageResource(imagIds[position]);
            return view;
        }
    }
}
