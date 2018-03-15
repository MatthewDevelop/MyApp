package cn.foxconn.matthew.myapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Matthew on 2018/1/28.
 */

public class StreamUtil {
    /**
     * 从输入流中读取数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();

        int len;
        byte[] buffer=new byte[1024];
        while((len=inputStream.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        String result=out.toString();
        inputStream.close();
        out.close();
        return result;
    }

}
