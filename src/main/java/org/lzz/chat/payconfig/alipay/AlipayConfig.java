package org.lzz.chat.payconfig.alipay;

public class AlipayConfig {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092700605286";

	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJtc7aO6IyL9B4Q6z2oKpBKttTRaVJ7yS6aZqlGdEE7HG9Ln7Dnm5QPBGU890+ezt9zVBSOAnWp0STyBxQXuJuXqKSHRJYgr1cTE7NCrduhbQIWXaV4VpUzLKA1tWjbEn3anNcvcsC1+qQsQVQdDw+O6Gtlwg3jMIjjjaCZSE2ORAgMBAAECgYEAhbA/ZSXk2lS1RRl+JZeme9QyGyynqGaCpoeQQc5PnBpFXQTpByIia0qcRs3DLJw/WK2NUHF6A4KxCnn4WhQGL3S7qTrmQ19TZHkrnfZjV9nJTR5gvWa5aXeV/0haJFvr/Bj1wC+rbt8DDvSP/o2gLDx32dm9SVm8sKY9gIk4U2ECQQDbTkUFe2bZyDHp98CpeqH1ieg80Nht+vRcq2LmL+jPpJB8Qi+5JEL9HQGyDT+UvXcxJhiX98TsHPCE1ALI5Y6tAkEAtVu9ICNAaKf9Z1YHrFi5pAkJ4q5nR3OnHVUYkp3QKtpqFxQSNq/P1syIqEUPche/Th1jreMzbhtC2rJb9Vw49QJBALUqLFSCA43nFmy8MRkQaFjJSuNqd1ArXtBq+HFFpKJbxMwRdRERxpEH9E2dKif6eEtOuERZ9wBCS+K+rWo+UKUCQDYk82va2mnkbYUibBwKSTIt+KOSigcNAkGBnFp5fyKsHqxRM7bb+mMYoVwLcjcP2++IWrAZestOnEEKquWv6NkCQQCPjiRjUUYVAo+4iV/6nWjuv8B+HQH43nRB76sIStW6/ALcG6YMkTBXWcQI3eSLeWirI0TIrlnEmVBv2gXQA6rp";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "D:\\";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8888/pay/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8888/pay/trunPage";
}