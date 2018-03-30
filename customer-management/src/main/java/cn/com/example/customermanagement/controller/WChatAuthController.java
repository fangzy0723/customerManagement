package cn.com.example.customermanagement.controller;

import cn.com.example.customermanagement.exception.CustomException;
import cn.com.example.customermanagement.utils.HttpRequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 微信授权
 * Created by fangzy on 2018/3/19 14:31
 */
@RestController
public class WChatAuthController {

    @RequestMapping("/wxLogin")
    public void  wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appid = "";
        //微信回掉的地址（公网可访问的）
        String backUrl = "/getAccessToken";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appid+
                "&redirect_uri=" + URLEncoder.encode(backUrl) +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    /**
     * 根据code获取access_token跟openid
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getAccessToken")
    public void  getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appid = "";
        String appsecret = "";
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appid +
                "&secret=" + appsecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = HttpRequestUtil.getRequest(url);
        String openid = (String)jsonObject.get("openid");
        String access_token = (String)jsonObject.get("access_token");
        //用户刷新access_token时用
        String refresh_token = (String)jsonObject.get("refresh_token");
        // 校验access_token是否有效
        JSONObject checkAccessTokenReturnJsonObject = checkAccessToken(access_token,openid,request,response);
        String errcode = checkAccessTokenReturnJsonObject.getString("errcode");
        if("0".equals(errcode)){
            //access_token 有效
            getUserInfo(access_token,openid,request,response);
        }else{
            throw new CustomException(0,"access_token无效请重试！");
        }
    }

    /**
     * 根据access_token和openid获取用户信息
     * @param access_token
     * @param openid
     * @throws IOException
     */
    public void  getUserInfo(String access_token ,String openid,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInfourl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject userInfojsonObject = HttpRequestUtil.getRequest(userInfourl);
        System.out.println("获取到的用户信息：" + userInfojsonObject);

        redirectToPage(userInfojsonObject, request, response);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * 正确的JSON返回结果：{ "errcode":0,"errmsg":"ok"}
     * 错误时的JSON返回示例：{ "errcode":40003,"errmsg":"invalid openid"}
     * @param access_token
     * @param openid
     * @param request
     * @param response
     * @throws IOException
     */
    public JSONObject  checkAccessToken(String access_token ,String openid,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://api.weixin.qq.com/sns/auth?" +
                "access_token=" + access_token +
                "&openid="+ openid;
        JSONObject jsonObject = HttpRequestUtil.getRequest(url);
        System.out.println("检验授权凭证（access_token）是否有效 返回信息：" + jsonObject);
        return jsonObject;
    }

    /**
     * 刷新access_token（如果需要）
     * @param refresh_token
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public JSONObject  refreshToken(String refresh_token ,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appid = "";
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                "appid=" +appid +
                "&grant_type=refresh_token" +
                "&refresh_token="+refresh_token;
        JSONObject jsonObject = HttpRequestUtil.getRequest(url);
        System.out.println("刷新access_token 返回信息：" + jsonObject);
        return jsonObject;
    }

    /**
     * 重定向到前台页面
     * @param userInfojsonObject
     * @param response
     * @throws IOException
     */
    public void  redirectToPage(JSONObject userInfojsonObject , HttpServletRequest request, HttpServletResponse response) throws IOException {
        //重定向到页面
        String redirect_url = "";
        response.sendRedirect("redirect_url");
    }
}
