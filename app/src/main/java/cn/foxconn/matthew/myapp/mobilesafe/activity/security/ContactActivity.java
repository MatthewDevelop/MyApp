package cn.foxconn.matthew.myapp.mobilesafe.activity.security;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;
import cn.foxconn.matthew.myapp.mobilesafe.base.MobileSafeBaseActivity;
import cn.foxconn.matthew.myapp.utils.LogUtil;

public class ContactActivity extends MobileSafeBaseActivity {
    private static final String TAG = "ContactActivity";
    @BindView(R.id.contact_list)
    ListView contactList;

    @Override
    protected void init() {
        super.init();
        final List<Map<String, String>> list = readContact();
        LogUtil.e(TAG, list.toString());
        contactList.setAdapter(new SimpleAdapter(this, list, R.layout.contact_list_item
                , new String[]{"name", "phone"}, new int[]{R.id.tv_name, R.id.tv_phone}));
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将数据返回
                String phone = list.get(position).get("phone");
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_contact;
    }

    /**
     * 读取通讯录
     */
    //TODO android.permission.READ_CONTACTS
    private List<Map<String, String>> readContact() {
        Uri rawContentUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        Cursor rawContactCursor = getContentResolver().query(rawContentUri, new String[]{"contact_id"}, null, null, null);
        //LogUtil.e(TAG,(rawContactCursor==null)+"");
        List<Map<String, String>> list = new ArrayList<>();
        if (rawContactCursor != null) {
            while (rawContactCursor.moveToNext()) {
                String contactId = rawContactCursor.getString(0);
                //LogUtil.e(TAG, "readContact: "+contactId );
                //根据contact_id从data表中查询联系人号码和名称，实际上是从data_view表中查询
                Cursor dataCursor = getContentResolver().
                        query(dataUri, new String[]{"mimetype", "data1"},
                                "contact_id=?", new String[]{contactId},
                                null);
                if (dataCursor != null) {
                    Map<String, String> map = new HashMap<>();
                    while (dataCursor.moveToNext()) {
                        String mimeType = dataCursor.getString(0);
                        String data1 = dataCursor.getString(1);
                        //LogUtil.e(TAG,contactId+"-"+mimeType+"-"+data1);
                        if ("vnd.android.cursor.item/name".equals(mimeType)) {
                            map.put("name", data1);
                        } else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
                            map.put("phone", data1);
                        }
                    }
                    list.add(map);
                    dataCursor.close();
                }
            }
            rawContactCursor.close();
        }
        return list;
    }
}
