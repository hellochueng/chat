package org.lzz.chat.service;

import com.alipay.api.AlipayApiException;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface AliPayService extends Pay {

    Integer increatment();

    int getResult();

    void increatmentException();

    void payInPcWeb(String payNumber, PayPlatform plat, HttpServletRequest request, HttpServletResponse resp) throws IOException, AlipayApiException;

    void notifyAndTrunPage(String payNumber, PayPlatform patform, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException;

    void notifyAndResponse(String payNumber, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException;

    Result getAppPayInfo(String seriaNumber);

    void refund(String out_trade_no,String trade_no,String reason, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException;

    void query(String out_trade_no,String trade_no, HttpServletRequest req, HttpServletResponse resp) throws IOException, AlipayApiException;
}
