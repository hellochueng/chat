package org.lzz.chat.service;

import com.alipay.api.AlipayApiException;
import org.lzz.chat.entity.PayPlatform;
import org.lzz.chat.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface WeiChatPayService extends Pay {

    Map<String, String> gotoPay(String payNumber, String ip,String type) throws Exception;

    void notify(Map<String, String[]> parameterMap) throws Exception;

    Result getAppPayInfo(String seriaNumber);

    Map<String, String> refund(String out_trade_no, String trade_no,String reason) throws Exception;

    Map<String, String> query(String out_trade_no) throws Exception;
}
