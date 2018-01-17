package cn.com.example.customermanagement.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：公共工具类，常用工具等<br>
 */
public class CommonUtils {

	/**
	 * 获取请求方IP
	 * @param request HttpServletRequest
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据身份证号获取性别(15位的是最后一位,18位的是倒数第二位,男单数女双 数)
	 * @param idNO 身份证号
	 * @return M:男性,F:女性
	 * @throws Exception
	 */
	public static String getSexByIDNo(String idNO) throws Exception {
		String sex = "";
		String charToFindSex = "";
		if (idNO == null || "".equals(idNO))
			return "";
		if (idNO.length() == 18) {
			charToFindSex = idNO.charAt(16) + "";
		} else if (idNO.length() == 15) {
			charToFindSex = idNO.charAt(14) + "";
		} else {
			throw new Exception("输入的身份证号不是15位也不是18位,请查证!");
		}
		// 15位的是最后一位,18位的是倒数第二位,男单数女双 数
		int intToFindSex = Integer.parseInt(charToFindSex);
		int sexInt = intToFindSex % 2;
		if (sexInt == 0){
			sex = "M";// 女性
		}else{
			sex = "F"; // 男性
		}
		return sex;
	}
	
	/**
	 * 根据身份证号获取生日
	 * @param IDNo 身份证号
	 * @return 返回生日  yyyy-MM-dd
	 * @throws Exception
	 */
	public static String getBirthdayByIDNo(String IDNo) throws Exception {
		String birthday = "";
		if (IDNo == null || "".equals(IDNo))
			return "";
		if (IDNo.length() == 18) {// 18位身份证号提取出生日期
			String birthday18 = IDNo.substring(6, 14);
			birthday = birthday18.substring(0, 4) + "-"
					+ birthday18.substring(4, 6) + "-"
					+ birthday18.subSequence(6, 8);
		} else if (IDNo.length() == 15) {// 15位身份证号提取出生日期
			String birthday15 = IDNo.substring(6, 12);
			birthday = "19" + birthday15.substring(0, 2) + "-"
					+ birthday15.substring(2, 4) + "-"
					+ birthday15.substring(4, 6);
		} else {
			throw new Exception("输入的身份证号不是15位也不是18位,请查证!");
		}
		return birthday;
	}


	/**
	 * 判断数组对象是否为空值 , 如果是 ""," ","null",null 则返回true, 对于Collection.size<1也空 <br>
	 * @param orignalValue
	 * @return
	 */
	public static boolean isBlank(Object... orignalValue) {
		if (orignalValue == null)
			return true;
		for (Object obj : orignalValue) {
			if (obj == null) {
				return true;
			} else if ("".equals(obj.toString().trim())) {
				return true;
			} else if ("null".equalsIgnoreCase(obj.toString().trim())) {
				return true;
			} else if (obj instanceof Collection) {
				Collection objCollection = (Collection) obj;
				if (objCollection == null || objCollection.size() < 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否不为空, 如果是 ""," ","null",null 则返回false<br>
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object... obj) {
		return !isBlank(obj);
	}


	/**
	 * 转换成保留两位的 金额
	 * @param num 金额 字符串
	 * @return 保留两位的金额
	 */
	public static String formatToMoney(String num) {
		String result = "";
		if (isBlank(num))
			return result;
		Double value = null;
		try {
			value = Double.parseDouble(num);
		} catch (Exception e) {
			value = null;
		}
		return formatToMoney(value);
	}

	/**
	 * 转换成保留两位的 金额<br>
	 * @param num 金额 浮点型
	 * @return 保留两位的金额
	 */
	public static String formatToMoney(Double num) {
		String result = "";
		if (isBlank(num))
			return result;

		DecimalFormat df = new DecimalFormat("##0.00");
		result = df.format(num);

		return result;
	}

	/**
	 * 生成4位随机数<br>
	 * @return
	 */
	public static String getCheckCode() {
		SecureRandom random = new SecureRandom();
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = "";
			int x = random.nextInt(10);
			rand = rand + x;
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * 将数字从一位小数变为String型的两位小数<br>
	 * @param dnumber 一位小数
	 * @return  String 两位小数
	 */
	public static String decimalFormat(double dnumber) {
		DecimalFormat df = new DecimalFormat("########.00 ");
		return df.format(dnumber);
	}


	/** 
     * 手机号验证 
     * @param  str
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
    
	/**
	 * @param obj
	 * @return  对空对象转换空字符串
	 */
	public static String getNull(String obj){
    	if(isBlank(obj)){
    		return "";
		}else{
    		return obj;
		}
	}

	/**
	 * 创建文件方法
	 * @param is
	 * @param uploadedFileLocation
	 * @return
	 */
	public static File writeToFile(InputStream is, String uploadedFileLocation) {
		File file = new File(uploadedFileLocation);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int n=0;
			while (-1 != (n = is.read(buffer))) {
				os.write(buffer, 0, n);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os!=null){
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		if (file.length()<5) {
//			file.delete();
//			return null;
//		}
		return file;
	}

	/**
	 *  创建文件方法
	 * @param bs
	 * @param uploadedFileLocation
	 * @return
	 */
	public static File uploadFile(byte[] bs, String uploadedFileLocation) {
		File file = new File(uploadedFileLocation);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(bs);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os!=null){
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 生成 uuid， 即用来标识一笔单，长度为32位
	 *
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成随机数
	 * @param length
	 * @return
	 */
	public static String generateNumber(int length) {
		String randomString = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			randomString += random.nextInt(10);
		}
		return randomString;
	}

	/**
	 * 交易流水号生成  24位长度
	 * @return
	 */
	public static String generateTransNo() {
		return DateUtils.format("yyyyMMddHHmmss",new Date())+generateNumber(10);
	}
	// 对密钥进行sha1加密
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("GBK"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取请求参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String doPostParamter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String str = "";
		StringBuffer sb = new StringBuffer();

		//按行读取内容
		while(null != (str = br.readLine())){
			sb.append(str);
		}
		String content = sb.toString();
		return content;
	}
	/**
	 * 获取uuid字符串,不带"-"格式
	 * @return
	 */
	public static String getUUID(){
		return java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	/**
	 * 正则检验 "2017-11-13 10:00:00" 格式的字符串
	 * @param dateTime   要检验的字符串
	 * @return
	 */
	public static boolean checkYYYYMMddHH24mmss(String dateTime){
		String regEx = "20\\d{2}-\\d{2}-\\d{2}\\s{1,}\\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(dateTime);
		return matcher.matches();
	}
	/**
	 * 正则检验 "2017-11-13" 格式的字符串
	 * @param date   要检验的字符串
	 * @return
	 */
	public static boolean checkYYYYMMdd(String date){
		String regEx = "20\\d{2}-\\d{2}-\\d{2}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}
	/**
	 * 正则检验 "10:00:00" 格式的字符串
	 * @param time   要检验的字符串
	 * @return
	 */
	public static boolean checkHH24mmss(String time){
		String regEx = "\\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(time);
		return matcher.matches();
	}
}
