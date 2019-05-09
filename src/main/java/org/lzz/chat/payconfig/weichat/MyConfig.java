package org.lzz.chat.payconfig.weichat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyConfig extends WXPayConfig{

    private byte[] certData;

    public MyConfig() throws Exception {
//        String certPath = "/path/to/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public String getAppID() {
        return "wx426b3015555a46be";
    }

    public String getMchID() {
        return "1225312702";
    }

    public String getKey() {
        return "e10adc3949ba59abbe56e057f20f883e";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    IWXPayDomain getWXPayDomain() {

        return new IWXPayDomain(){

            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}