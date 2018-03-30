package cn.com.example.customermanagement.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http请求工具类
 * Created by fangzy on 2017/9/20 9:23
 */
public class HttpRequestUtil {
    static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);


    /**
     * 发送http post
     * 请求数据格式为json，响应格式为json
     * @param strUrl   请求url地址
     * @param json     请求数据
     * @return
     * @throws IOException
     */
    public static String postRequestJson(String strUrl, String json) throws IOException {
        logger.info("postRequestJson 方法被调用，请求的url：{}，请求的数据：{}",strUrl,json);
        StringBuffer sb = new StringBuffer("");
        //创建连接
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置http连接属性
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 可以根据需要 提交 GET、POST、DELETE、INPUT等http提供的功能
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        //设置http头 消息
        //设定 请求格式 json，也可以设定xml格式的
        connection.setRequestProperty("Content-Type", "application/json");
        //设定响应的信息的格式为 json，也可以设定xml格式的
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        OutputStream out = connection.getOutputStream();
        out.write(json.getBytes());
        out.flush();
        out.close();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;

        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        reader.close();
        connection.disconnect();
        logger.info("postRequestJson 方法返回的请求响应内容：{}",sb.toString());
        return sb.toString();
    }

    /**
     * 发送http post
     * 请求数据格式为xml，响应格式为json
     * @param strUrl    请求url
     * @param xml       请求数据
     * @return
     * @throws IOException
     */
    public static String postRequestXml(String strUrl, String xml) throws IOException {
        logger.info("postRequestXml 方法被调用，请求的url：{}，请求的数据：{}",strUrl,xml);
        StringBuffer sb = new StringBuffer("");
        //创建连接
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置http连接属性
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 可以根据需要 提交 GET、POST、DELETE、INPUT等http提供的功能
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        //设置http头 消息
        //设定 请求格式 xml
        connection.setRequestProperty("Content-Type", "text/xml");
        //设定响应的信息的格式为 json，也可以设定xml格式的
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        OutputStream out = connection.getOutputStream();
        out.write(xml.getBytes());
        out.flush();
        out.close();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        reader.close();
        connection.disconnect();
        logger.info("postRequestXml 方法返回的请求响应内容：{}",sb.toString());
        return sb.toString();
    }

    /**
     * 发送http get
     * 请求数据格式为xml，响应格式为json
     * @param strUrl    请求url
     * @return
     * @throws IOException
     */
    public static JSONObject getRequest(String strUrl) {
        logger.info("getRequestXml 方法被调用，请求的url：{}",strUrl);
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            StringBuffer sb = new StringBuffer("");
            //创建连接
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置http连接属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 可以根据需要 提交 GET、POST、DELETE、INPUT等http提供的功能
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            //设置http头 消息
            //设定 请求格式 xml
            connection.setRequestProperty("Content-Type", "text/xml");
            //设定响应的信息的格式为 json，也可以设定xml格式的
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            OutputStream out = connection.getOutputStream();
            //out.write(xml.getBytes());
            out.flush();
            out.close();
            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            connection.disconnect();
            logger.info("getRequestXml 方法返回的请求响应内容：{}",sb.toString());
            /**把json字符串转换成json对象**/
            jsonResult = JSONObject.parseObject(sb.toString());
        }catch (Exception e){
            logger.error("get请求提交失败:" + strUrl, e);
        }
        return jsonResult;
    }

}
