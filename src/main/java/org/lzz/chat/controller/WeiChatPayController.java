package org.lzz.chat.controller;

import org.apache.derby.impl.store.access.heap.Heap;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.payconfig.weichat.QRCodeUtil;
import org.lzz.chat.payconfig.weichat.WeChatPayConfig;
import org.lzz.chat.service.WeiChatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;

@RequestMapping("/pay")
@RestController
public class WeiChatPayController {

    @Autowired
    private WeiChatPayService weiChatPayService;

    //创建微信支付
    @RequestMapping("/weiChat/{seriaNumber}")
    public Map<String, String> gotoPay(@PathVariable String seriaNumber, HttpServletRequest req) throws Exception {
        return weiChatPayService.gotoPay(seriaNumber,req.getRemoteAddr(),WeChatPayConfig.TRADE_TYPE_NATIVE);
    }

    //创建微信支付
    @RequestMapping("/weiChat/app/{seriaNumber}")
    public Map<String, String> gotoAppPay(@PathVariable String seriaNumber, HttpServletRequest req) throws Exception {
        return weiChatPayService.gotoPay(seriaNumber,req.getRemoteAddr(), WeChatPayConfig.TRADE_TYPE_APP);
    }

    //生成二维码
    @RequestMapping("/weiChat/QRcode")
    @ResponseBody
    public void getQrCode(String codeUrl, HttpServletResponse response) throws Exception {
        QRCodeUtil.encode(codeUrl, response.getOutputStream(), false, 0);
    }

    //相应
    @RequestMapping("/weiChat/notify")
    @ResponseBody
    public void notify( HttpServletRequest req) throws Exception {
        Map<String, String[]> parameterMap = req.getParameterMap();
        weiChatPayService.notify(parameterMap);
    }
}
