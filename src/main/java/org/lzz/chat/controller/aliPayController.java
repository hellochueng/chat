package org.lzz.chat.controller;

import com.alipay.api.AlipayApiException;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/pay")
@Controller
public class aliPayController {

    @Autowired
    private AliPayService aliPayService;

    /**
     * 支付
     * @param orderId
     * @param request
     * @param resp
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping("/aliPay/{plat}/{orderId}")
    public void aliPay(@PathVariable("plat")PayPlatform plat,@PathVariable("orderId")String orderId, HttpServletRequest request, HttpServletResponse resp) throws IOException, AlipayApiException {
        aliPayService.payInPcWeb(orderId,plat,request,resp);
    }

    @RequestMapping("/notify/{payNumber}")
    public void notify(@PathVariable String payNumber,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
       aliPayService.notifyAndResponse(payNumber,req,resp);
    }

    @RequestMapping("/trunPage/{plat}/{payNumber}")
    public void getResult(@PathVariable("plat")PayPlatform plat,@PathVariable String payNumber,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        aliPayService.notifyAndTrunPage(payNumber, plat,req,resp);
    }

    @RequestMapping("/query/{orderId}/{tradeNo}")
    public void query(@PathVariable("orderId")String orderId,@PathVariable String tradeNo,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        aliPayService.query(orderId, tradeNo,req,resp);
    }

    @RequestMapping("/refund/{orderId}/{tradeNo}/{reason}")
    public void refund(@PathVariable String orderId,@PathVariable String tradeNo,@PathVariable String reason,HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException {
        aliPayService.refund(orderId, tradeNo,reason,req,resp);
    }

    @RequestMapping("/c")
    public void increatmentException() {
        aliPayService.increatmentException();
    }


}