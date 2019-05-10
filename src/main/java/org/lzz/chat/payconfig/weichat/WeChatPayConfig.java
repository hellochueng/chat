package org.lzz.chat.payconfig.weichat;

public class WeChatPayConfig {
    public WeChatPayConfig() {
    }

    public static final String APPID = "wx426b3015555a46be";
    public static final String APPSECRET = "01c6d59a3f9024db6336662ac95c8e74";
    public static final String MCH_ID = "1225312702";
    public static final String KEY = "e10adc3949ba59abbe56e057f20f883e";
    public static final String TRADE_TYPE_NATIVE = "NATIVE";//扫码支付
    public static final String TRADE_TYPE_APP = "APP";//APP支付
    public static final String TRADE_TYPE_JSAPI = "JSAPI";//公众号支付

    public static final String INPUT_CHARSET = "utf-8";

    //支付完成之后回调地址
    public static final String PC_NOTIFY_URL = "/web_api/wechatpay/notify/";          //PC回调地址
    public static final String APP_VUE_NOTIFY_URL="/app_vue_api/wechatpay/notify/";   //App嵌入Vue回调地址
    public static final String APP_NOTIFY_URL="/app_vue_api/wechatpay/notifyInApp/";            //App回调地址
    public static final String M_VUE_NOTIFY_URL="/mobile_vue_api/wechatpay/notify/";   //手机回调地址
}