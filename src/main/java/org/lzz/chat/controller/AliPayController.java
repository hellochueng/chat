package org.lzz.chat.controller;

import com.alipay.api.AlipayApiException;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;
import org.lzz.chat.service.AliPayService;
import org.lzz.chat.service.impl.AliServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/pay")
@RestController
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;

    //创建阿里支付
    @RequestMapping("/aliPay/{plat}/{orderId}")
    public String aliPay(@PathVariable("plat")PayPlatform plat,@PathVariable("orderId")String orderId, HttpServletResponse resp) throws IOException, AlipayApiException {
        return aliPayService.payInWeb(orderId,plat);
    }

    //支付相应
    @RequestMapping("/aliPay/notify/{payNumber}")
    public void notify(@PathVariable String payNumber,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {

        Map<String,String> params = AliServiceImpl.getResponseParam(req.getParameterMap());

        if(aliPayService.rsaCheckV1(payNumber,params)){
            //商户订单号
            String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
           resp.getWriter().println("success");
        }else {
           resp.getWriter().println("fail");
        }
    }

    //支付完成返回页面
    @RequestMapping("/aliPay/turnPage/{plat}/{payNumber}")
    public void turnPage(@PathVariable("plat")PayPlatform plat,@PathVariable String payNumber,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {

        //获取支付宝GET过来反馈信息
        Map<String,String> params = AliServiceImpl.getResponseParam(req.getParameterMap());

        if(aliPayService.rsaCheckV1(payNumber,params)) {
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

    //查询阿里订单支付情况
    @RequestMapping("/aliPay/query/{orderId}/{tradeNo}")
    public Result query(@PathVariable("orderId")String orderId, @PathVariable String tradeNo, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        return aliPayService.query(orderId, tradeNo);
    }

    //退款
    @RequestMapping("/aliPay/refund/{orderId}/{tradeNo}/{reason}")
    public Result refund(@PathVariable String orderId,@PathVariable String tradeNo,@PathVariable String reason,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        return aliPayService.refund(orderId, tradeNo,reason);
    }

}