package cn.foxconn.matthew.myapp.mobilesafe.db.dao;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cn.foxconn.matthew.myapp.app.App;
import cn.foxconn.matthew.myapp.mobilesafe.entity.BlackNumber;


/**
 * @author:Matthew
 * @date:2018/8/20
 * @email:guocheng0816@163.com
 * @func:
 */
public class BlackNumDaoTest {


    @Test
    public void add() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            blackNumDao.add(String.valueOf(15623912075l + i), String.valueOf(random.nextInt(3) + 1));
        }
    }

    @Test
    public void delete() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        boolean result = blackNumDao.delete("15623912076");
        Assert.assertEquals(true, result);
    }

    @Test
    public void modify() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        boolean result = blackNumDao.modify("15623912075", "3");
        Assert.assertEquals(true, result);
    }

    @Test
    public void query() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        String result = blackNumDao.query("15623912075");
        Assert.assertEquals("3", result);
    }

    @Test
    public void queryAll() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        List<BlackNumber> result = blackNumDao.queryAll();
        Assert.assertEquals(199, result.size());
    }

    @Test
    public void queryTotalNumCount() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        int result = blackNumDao.queryTotalNumCount();
        Assert.assertEquals(199, result);
    }

    @Test
    public void queryByPage() {
        BlackNumDao blackNumDao = new BlackNumDao(App.getContext());
        List<BlackNumber> result = blackNumDao.queryByPage(3, 10);
        for (BlackNumber b : result) {
            System.out.println(b.toString());
        }
    }
}