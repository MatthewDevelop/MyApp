package cn.foxconn.matthew.myapp.mobilesafe.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import cn.foxconn.matthew.myapp.helper.MyDatabaseHelper;
import cn.foxconn.matthew.myapp.mobilesafe.entity.BlackNumber;

/**
 * @author:Matthew
 * @date:2018/8/20
 * @email:guocheng0816@163.com
 * @func:
 */
public class BlackNumDao {

    private static final String TABLE_NAME = "black_num";
    private MyDatabaseHelper mHelper;

    public BlackNumDao(Context context) {
        mHelper = new MyDatabaseHelper(context);
    }

    /**
     * 插入数据
     *
     * @param number 拦截号码
     * @param mode   拦截模式
     * @return 插入结果
     */
    public boolean add(String number, String mode) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("mode", mode);
        long num = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return num != -1;
    }


    /**
     * 从黑名单中移除指定号码
     *
     * @param number 删除号码
     * @return 删除结果
     */
    public boolean delete(String number) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int num = db.delete(TABLE_NAME, "number=?", new String[]{number});
        System.out.println(num);
        db.close();
        return num != 0;
    }

    /**
     * 更新名单
     *
     * @param number 被拦截号码
     * @param mode   拦截模式
     * @return 修改结果
     */
    public boolean modify(String number, String mode) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mode", mode);
        int num = db.update(TABLE_NAME, contentValues, "number=?", new String[]{number});
        db.close();
        return num != 0;
    }


    /**
     * 查询指定号码的拦截模式
     *
     * @param number 被拦截的号码
     * @return 拦截模式
     */
    public String query(String number) {
        String mode = "";
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"mode"},
                "number=?", new String[]{number},
                null, null, null);
        if (cursor.moveToNext()) {
            mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }

    /**
     * 查询所有的黑名单号码
     *
     * @return
     */
    public List<BlackNumber> queryAll() {
        List<BlackNumber> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"number", "mode"}, null,
                null, null,
                null, null);
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setNumber(cursor.getString(0));
            blackNumber.setMode(cursor.getString(1));
            list.add(blackNumber);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 查询表中数据条数
     *
     * @return 统计结果
     */
    public int queryTotalNumCount() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"number", "mode"}, null,
                null, null,
                null, null);
        int result = cursor.getCount();
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 分页查询黑名单
     *
     * @param pageNum  页数
     * @param pageSize 单页条数
     * @return 黑名单
     */
    public List<BlackNumber> queryByPage(int pageNum, int pageSize) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from black_num limit ? offset ?"
                , new String[]{String.valueOf(pageSize), String.valueOf(pageNum * pageSize)});
        List<BlackNumber> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setNumber(cursor.getString(0));
            blackNumber.setMode(cursor.getString(1));
            result.add(blackNumber);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 上拉加载更多
     *
     * @param startIndex 开始位置
     * @param maxCount 单页最大
     * @return
     */
    public List<BlackNumber> queryByPage2(int startIndex, int maxCount) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from black_num limit ? offset ?"
                , new String[]{String.valueOf(maxCount), String.valueOf(startIndex)});
        List<BlackNumber> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            BlackNumber blackNumber = new BlackNumber();
            blackNumber.setNumber(cursor.getString(0));
            blackNumber.setMode(cursor.getString(1));
            result.add(blackNumber);
        }
        cursor.close();
        db.close();
        return result;
    }
}
