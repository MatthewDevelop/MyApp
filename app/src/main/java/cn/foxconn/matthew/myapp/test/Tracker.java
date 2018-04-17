package cn.foxconn.matthew.myapp.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:Matthew
 * @date:2018/4/16
 * @email:guocheng0816@163.com
 */
public class Tracker {
    private static final String TAG = "Tracker";

    /**
     * Apikey
     */
    private static String Apikey = "8ddcc142-4540-4837-9bb0-0600f8c3ba36";

    /**
     * Json
     */
    public static String orderOnlineByJson(String requestData, String urlStr, String type) throws Exception {
        //---headerParams
        Map<String, String> headerparams = new HashMap();
        headerparams.put("Trackingmore-Api-Key", Apikey);
        headerparams.put("Content-Type", "application/json");
        //---bodyParams
        List<String> bodyParams = new ArrayList();
        String result = null;
        if (type.equals("post")) {
            String ReqURL = "http://api.trackingmore.com/v2/trackings/post";
            bodyParams.add(requestData);
            result = sendPost(ReqURL, headerparams, bodyParams, "POST");

        } else if (type.equals("get")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings/get";
            String RelUrl = ReqURL + urlStr;
            result = sendPost(RelUrl, headerparams, bodyParams, "GET");

        } else if (type.equals("batch")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings/batch";
            bodyParams.add(requestData);
            result = sendPost(ReqURL, headerparams, bodyParams, "POST");

        } else if (type.equals("codeNumberGet")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings";
            String RelUrl = ReqURL + urlStr;
            result = sendGet(RelUrl, headerparams, "GET");

        } else if (type.equals("codeNumberPut")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings";
            bodyParams.add(requestData);
            String RelUrl = ReqURL + urlStr;
            result = sendPost(RelUrl, headerparams, bodyParams, "PUT");

        } else if (type.equals("codeNumberDelete")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings";
            String RelUrl = ReqURL + urlStr;
            result = sendGet(RelUrl, headerparams, "DELETE");

        } else if (type.equals("realtime")) {

            String ReqURL = "http://api.trackingmore.com/v2/trackings/realtime";
            bodyParams.add(requestData);
            result = sendPost(ReqURL, headerparams, bodyParams, "POST");

        }

        Log.e(TAG, "orderOnlineByJson: " + result);
        return result;


    }

    private static String sendPost(String url, Map<String, String> headerParams, List<String> bodyParams, String mothod) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod(mothod);

            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.connect();

            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            StringBuffer sbBody = new StringBuffer();
            for (String str : bodyParams) {
                sbBody.append(str);
            }
            out.write(sbBody.toString());

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendGet(String url, Map<String, String> headerParams, String mothod) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setRequestMethod(mothod);

            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void queryCompany(String body) {
        //---headerParams
        Map<String, String> headerparams = new HashMap();
        headerparams.put("Trackingmore-Api-Key", Apikey);
        headerparams.put("Content-Type", "application/json");

        List<String> bodyParams = new ArrayList();
        bodyParams.add(body);

        String url="https://api.trackingmore.com/v2/carriers/detect";

        String result=sendPost(url,headerparams,bodyParams,"POST");
        Log.e(TAG, "queryCompany: "+result );
    }

}
