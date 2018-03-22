package cn.foxconn.matthew.myapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:Matthew
 * @date:2018/2/3
 * @email:guocheng0816@163.com
 */

public class MD5Util {
    /**
     * 获取MD5
     * @param pass
     * @return
     */
    public static String encode(String pass){
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(pass.getBytes("UTF-8"));
            StringBuffer sb=new StringBuffer();
            for (byte b : digest) {
                //获取字节低八位有效值
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length()<2) {
                    hexString = "0" + hexString;
                }
                //System.out.println(hexString);
                sb.append(hexString);
            }
            //System.out.println(sb);
            //System.out.println(sb.length());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
