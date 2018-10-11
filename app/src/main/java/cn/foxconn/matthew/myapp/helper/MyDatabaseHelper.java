package cn.foxconn.matthew.myapp.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.foxconn.matthew.myapp.app.AppConst;

/**
 * @author:Matthew
 * @date:2018/4/11
 * @email:guocheng0816@163.com
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_COMPANY_DATA_TABLE = "create table company_data(" +
            "id integer primary key autoincrement," +
            "company_name text," +
            "company_code text)";

    private static final String CREATE_BALCK_NUMBER_TABLE = "create table black_num" +
            "(id integer primary key autoincrement," +
            "number varchar(20)," +
            "mode varchar(2))";


    public MyDatabaseHelper(Context context) {
        super(context, AppConst.DB_NAME, null, AppConst.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表格
        db.execSQL(CREATE_COMPANY_DATA_TABLE);
        db.execSQL(CREATE_BALCK_NUMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除表格
        db.execSQL("drop table company_data");
    }
}
