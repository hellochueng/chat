package org.lzz.chat.service.impl;

import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;
import org.lzz.chat.payconfig.weichat.MyConfig;
import org.lzz.chat.payconfig.weichat.WXPay;
import org.lzz.chat.payconfig.weichat.WXPayUtil;
import org.lzz.chat.payconfig.weichat.WeChatPayConfig;
import org.lzz.chat.service.BasePay;
import org.lzz.chat.service.WeiChatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WeiChatPayServiceImpl extends BasePay implements WeiChatPayService {

    @Autowired
    private WXPay wxpay;

    @Override
    public Map<String, String> gotoPay(String payNumber,String ip,String type) throws Exception {

        Map<String, String> data = new HashMap<String, String>();

        data.put("body", "这是款好酒，醉生梦死就是喝");
        data.put("out_trade_no", payNumber);
        data.put("device_info", "");
        data.put("fee_type", "HKD");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", type);  // 此处指定为扫码支付
        data.put("product_id", "12");

        return wxpay.unifiedOrder(data);
    }

    @Override
    public void turnPage(String payNumber, PayPlatform patform){

    }

    @Override
    public void notify(Map<String, String[]> parameterMap) throws Exception {

        String notifyData = "...."; // 支付结果通知的xml格式数据

        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map

        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
            // 签名正确
            // 进行处理。
            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
        }
        else {
            // 签名错误，如果数据里没有sign字段，也认为是签名错误
        }
    }

    @Override
    public Result getAppPayInfo(String seriaNumber) {
        return null;
    }

    @Override
    public Map<String, String> refund(String out_trade_no, String trade_no,String reason) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000012");

        Map<String, String> resp = wxpay.refundQuery(data);

        return resp;
    }

    @Override
    public Map<String, String> query(String out_trade_no) throws Exception {

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000012");

        Map<String, String> result = wxpay.orderQuery(data);
        return result;
    }

}
