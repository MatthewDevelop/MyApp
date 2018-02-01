package cn.foxconn.matthew.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.mobilesafe.R;

/**
 * Created by Matthew on 2018/1/31.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gridView)
    GridView mGridView;

    String[] names=new String[]{"手机防盗","手机防盗","手机防盗","手机防盗","手机防盗","手机防盗","手机防盗","手机防盗","设置中心"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGridView.setAdapter(new HomeAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 8:
                        //设置中心
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                }
            }
        });
    }

    class HomeAdapter extends BaseAdapter{

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
            View view=getLayoutInflater().inflate(R.layout.home_grid_item,parent,false);
            TextView textView=view.findViewById(R.id.item_name);
            textView.setText(names[position]);
            return view;
        }
    }
}
