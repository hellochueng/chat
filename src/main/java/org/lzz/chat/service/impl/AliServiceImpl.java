package org.lzz.chat.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;
import org.lzz.chat.payconfig.AlipayConfig;
import org.lzz.chat.service.AliPayService;
import org.lzz.chat.service.BasePay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AliServiceImpl extends BasePay implements AliPayService {

    static AtomicInteger i = new AtomicInteger(1);

    @Override
    public void payInPcWeb(String payNumber,PayPlatform plat, HttpServletRequest request, HttpServletResponse resp) throws IOException, AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key,
                "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url+"/"+plat+"/"+payNumber);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url+"/"+payNumber);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = payNumber;
        //付款金额，必填
        String total_amount = new String("100.00");
        //订单名称，必填
        String subject = new String("喝就是喝".getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        String body = new String("这是一件好商品，醉生梦死就是喝".getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        resp.getWriter().println(result);
    }

    @Override
    public void notifyAndTrunPage(String payNumber, PayPlatform patform, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(req.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            resp.getWriter().println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
        }else {
            resp.getWriter().println("验签失败");
        }
    }

    @Override
    public void notifyAndResponse(String payNumber, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified) {
            resp.getWriter().println("success");
        }else {
            resp.getWriter().println("fail");
        }
    }

    @Override
    public Result getAppPayInfo(String seriaNumber) {
        return null;
    }

    @Override
    public void refund(String out_trade_no, String trade_no,String reason, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(req.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
//        String trade_no = new String(req.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"),"UTF-8");
        //请二选一设置
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String("100.00".getBytes("ISO-8859-1"),"UTF-8");

        //退款的原因说明
        String refund_reason = new String(reason.getBytes("ISO-8859-1"),"UTF-8");

        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String("1".getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent(
                "{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"trade_no\":\""+ trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"refund_reason\":\""+ refund_reason +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();

        //输出
        resp.getWriter().println(result);
    }

    @Override
    public void query(String out_trade_no,String trade_no, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(req.getParameter("WIDTQout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //支付宝交易号
//        String trade_no = new String(req.getParameter("WIDTQtrade_no").getBytes("ISO-8859-1"),"UTF-8");
        //请二选一设置

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();

        //输出
        resp.getWriter().println(result);
    }

    @Override
    public Integer increatment() {
        return i.incrementAndGet();
    }

    @Override
    public int getResult() {
        return i.get();
    }

    @Override
    @Transactional
    public void increatmentException() {
        i.incrementAndGet();
    }

}
