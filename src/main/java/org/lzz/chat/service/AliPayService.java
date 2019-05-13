package org.lzz.chat.service;

import com.alipay.api.AlipayApiException;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface AliPayService extends Pay {

    String payInWeb(String payNumber, PayPlatform plat) throws IOException, AlipayApiException;

    boolean rsaCheckV1(String payNumber, Map<String,String> params) throws IOException, AlipayApiException;

    Result getAppPayInfo(String seriaNumber);

    Result refund(String out_trade_no,String trade_no,String reason) throws IOException, AlipayApiException;

    Result query(String out_trade_no,String trade_no) throws IOException, AlipayApiException;
}
