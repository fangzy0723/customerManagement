package cn.com.example.customermanagement.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 描述：字符串工具类 集成了Apache commons<br>
 * 作者：wangxuerui <br>
 * 修改日期：2016-9-13下午1:52:21 <br>
 * E-mail: wangxuerui@sinosoft.com.cn <br>
 */
public class StringUtil extends StringUtils {

    /**
     * 判断数组对象是否为空值 , 如果是 ""," ","null",null 则返回true, 对于Collection.size<1也空 <br>
     * @return
     */
    public static boolean is_Blank(Object object){
        if(object == null || "".equals(object.toString().trim())
                || "null".equals(object.toString().trim())
                || "[null]".equals(object.toString().trim())
                || "[]".equals(object.toString().trim())){
            return true ;
        }
        return false ;
    }

	/**
	 * 判断数组对象是否为空值 , 如果是 ""," ","null",null 则返回true, 对于Collection.size<1也空 <br>
	 * @return
	 */
    public static boolean isBlank(Object...objects){
        Boolean result = false ;
        for (Object object : objects) {
            if(object == null || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())
                    || "[null]".equals(object.toString().trim())
                    || "[]".equals(object.toString().trim())){
                result = true ;
                break ;
            }
        }
        return result ;
    }
	/**
	 * 判断是否不为空, 如果是 ""," ","null",null 则返回false<br>
	 * @return
	 */
    public static boolean isNotBlank(Object...objects){
        return !isBlank(objects);
    }
    /**
     * 判断一个字符串在数组中存在几个<br>
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr, String[] strings){
        if(null == baseStr || baseStr.length() == 0 || null == strings)
            return 0;
        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i ;
    }

    /**
     * 将 Strig  进行 BASE64 编码<br>
     * @param str 要编码的字符串
     * @param bf [true|false,true:去掉结尾补充的'=',false:不做处理]
     * @return
     */
    public static String getBASE64(String str, boolean...bf) {
       if (StringUtils.isBlank(str)) return null; 
       String base64 = new sun.misc.BASE64Encoder().encode(str.getBytes()) ;
       //去掉 '='
       if(isBlank(bf) && bf[0]){
           base64 = base64.replaceAll("=", "");
       }
       return base64;
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码<br>
     * @param s 要解码的字符串
     * @return
     */
    public static String getStrByBASE64(String s) {
       if (isBlank(s)) return ""; 
       BASE64Decoder decoder = new BASE64Decoder();
       try { 
          byte[] b = decoder.decodeBuffer(s); 
          return new String(b);
       } catch (Exception e) {
          return ""; 
       }
    }

    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30<br>
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object,? extends Object> map){
        String result = "" ;
        if(map == null || map.size() ==0){
            return result ;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys ) {
            result += ((String)key + "=" + (String)map.get(key) + "&");
        }
        
        return isBlank(result) ? result : result.substring(0,result.length() - 1);
    }
    
    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}<br>
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args){
        if(isBlank(args)){
            return null ;
        }
        args = args.trim();
        //如果是?开头,把?去掉
        if(args.startsWith("?")){
            args = args.substring(1,args.length());
        }
        String[] argsArray = args.split("&");
        
        Map<String,Object> result = new HashMap<String,Object>();
        for (String ag : argsArray) {
            if(!isBlank(ag) && ag.indexOf("=")>0){
                String[] keyValue = ag.split("=");
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.
                String key = keyValue[0];
                String value = "" ;
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i]  + "=";
                }
                value = value.length() > 0 ? value.substring(0,value.length()-1) : value ;
                result.put(key,value);
            }
        }
        
        return result ;
    }
    
    /**
     * 转换成Unicode<br>
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
            s1 = s1 + "\\u" + as[i];
        }
        return s1;
     }
    /**
     * 描述：double 转 string<br>
     * @param str
     * @return
     */
    public static String getDoubleTOString(Double str){
        String money = str.toString();
        try {   
            Double.parseDouble(money);
        } catch (Exception e) {
            BigDecimal bDecimal = new BigDecimal(str);
            money = bDecimal.toPlainString();
        } 
        return money;
        
    }

    /**
     * 把数组转换成Set 方便判断<br>
     * @param objs
     * @return
     */
    public static TreeSet<String> arrayToSet(String[] objs){
        TreeSet<String> result = new TreeSet<String>();
        if(null ==objs){
            return result ;
        }
        for (String obj:objs) {
            result.add(obj);
        }
        return result;
    }
    
    /**
     * 字符串转urlcode<br>
     * @param value
     * @return
     */
    public static String strToUrlcode(String value){
        try {
            value = java.net.URLEncoder.encode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * urlcode转字符串<br>
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value){
        try {
            value = java.net.URLDecoder.decode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }  
    }
    

    /**
     * 字符串替换<br>
     * @param str
     * @param nowStr
     * @param replaceStr
     * @return
     */
    public static String replaceString(String str, String nowStr, String replaceStr) {
        nowStr = StringUtils.isBlank(nowStr) ? "" : nowStr;
        replaceStr = StringUtils.isBlank(replaceStr) ? "" : replaceStr;
        if (StringUtils.isNotBlank(str)) {
            return str.replaceAll(nowStr, replaceStr);
        }
        return "";
    }
    /**
     * 将对象转换为json字符串<br>
     * @param value
     * @return
     */
    public static String toJson(Object value){
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    	return JSON.toJSONString(value, SerializerFeature.WriteDateUseDateFormat);
    }
    
    
    /**
     * josn字符串转换为对象<br>
     * @param json json格式的字符串
     * @param clazz Class类型  如 Map.class
     * @return
     */
    public static <T> T  fromJson(String json, Class<T> clazz){
    	return JSON.parseObject(json, clazz);
    }

}
