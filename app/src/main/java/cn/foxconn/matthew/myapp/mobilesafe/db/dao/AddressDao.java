package cn.foxconn.matthew.myapp.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author:Matthew
 * @date:2018/8/11
 * @email:guocheng0816@163.com
 * @func:归属地查询工具
 */
public class AddressDao {

    private static final String PATH = "data/data/cn.foxconn.matthew.myapp/files/address.db";

    /**
     * 根据号码查询归属地
     *
     * @param number
     * @return
     */
    public static String getAddress(String number) {
        String result = "未知号码";
        //获取数据库对象
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(PATH,
                null, SQLiteDatabase.OPEN_READONLY);
        //正则表达式 1开头第二位3-8后面9位数字
        //^1[3-8]\d{9}&
        if (number.matches("^1[3-8]\\d{9}$")) {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT location FROM data2 WHERE id=(SELECT outkey FROM data1 WHERE id=?)",
                    new String[]{number.substring(0, 7)});
            if (cursor.moveToNext()) {
                result = cursor.getString(0);
            }
            if (cursor != null) {
                cursor.close();
            }
        } else if (number.matches("^\\d+$")) {
            switch (number.length()) {
                case 3:
                    result = "报警电话";
                    break;
                case 4:
                    result = "模拟器";
                    break;
                case 5:
                    result = "客服电话";
                    break;
                case 7:
                case 8:
                    result = "本地电话";
                    break;
                default:
                    if(number.startsWith("0")&&number.length()>10){
                        Cursor cursor=sqLiteDatabase.rawQuery("SELECT location FROM data2 WHERE area=?"
                                , new String[]{number.substring(1, 4)});
                        if (cursor.moveToNext()){
                            result=cursor.getString(0);
                            result=result.substring(0,result.length()-2);
                        }else {
                            if(cursor!=null){
                                cursor.close();
                            }
                            cursor=sqLiteDatabase.rawQuery("SELECT location FROM data2 WHERE area=?"
                                    , new String[]{number.substring(1, 3)});
                            if(cursor.moveToNext()){
                                result=cursor.getString(0);
                                result=result.substring(0,result.length()-2);
                            }

                            if (cursor!=null){
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
        if (sqLiteDatabase!=null){
            sqLiteDatabase.close();
        }
        return result;
    }
}
