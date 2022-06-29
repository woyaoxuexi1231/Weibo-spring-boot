package com.cqut.weibo.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2016110200786452";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCjbW2e/T5fgbvb/N2Vz0Kt0C6KNGZlOGGleYvkAJ9w0L+o79XSB5nOAN4jPXILN/J+ZQ4OwcEo6WVkUaxTl0Fn9NTbFyyRvl6lBvPVuSmeS1Xtrw8QQMiQs9awVWJUcSkk8QdWNEANKO6yXT/RVijH/LGPIjZ1Kxi7ev9Ohbj25iaa6XXKMDJGjbILfaU7R8aAI/TDJrCXhjrEIYRbpFrz1rTwzEzYA02CLERjB7vDHErrdJj4ebg0igOhaJfQmjSNK2byZj1aaeJpYESRx5Vm0W7dEFnXAiuyHF68/tqkS+k2aKMLzM+qDVWjh0K/v1wRyR7Gmfe/l2LN4Qr+0pyLAgMBAAECggEAXp9IHgqwZ2ndFCUXQtrq12o22Aw40RhQ6Rcz2MJHjnE+1xht65o9Ahpcg3jbJAWzoFzwQ7kJFp/z9Z6YMRndY8xMxcFoK8ZWVCRvX2pC34Ts7p4yEY4A0k9d2WDGeu++C8aGIfJEt5nggmMdRR4mpIv5Xk9HypU2T66KTbN9AW4YYUZ3y+EFCOPSBpz1fbW90rqZE28Qy13mh/aZkgPlx/blQaW6fw39znEzITCNAScJ3iOVUyQch74tS7jsyce65byN+YvMGXe/FBVk42RVH/k3XcnyN99OA+p2PoDhFAoXEf8DTxmWdKykscG8LcVGrhlptjiAGXbJsrA8/RWf0QKBgQDfHops+WFkkmCSdvDZhqcDyVSAXAoO6Nx57OaZ9M9p12tf4QZWLpeDWA5db+NG9dfwLR0Jjzg+P3oqgYLx+IkF4a1n8CXTAaPic8HAYlkyvAY35N+dphL2gu9IB1+KoPwjcwnxMSePR5th+Ke7Fb5UlV7XCLo6fTkTG3XyH5t+6QKBgQC7gu/Lv/FK8sybYmOZqVbeQCXw1kSaipx9eg8BTxuKKlwk6EyLOvSgFT/fy7Xs34laFMbJ0zw60RdKFW58Xiw0Y0suwPyKSkhjSL2vlLWzR8IQZHG+0RohY2RC6djSzZ74Xm7wQmpwBUReh/Ft2tpm4WeMlG2a0ya+w1wIvtBfUwKBgGT3dBrWS01wtATzkBBPz9p1zCh9/gs7MzJ1kvEOiKHT2wI7WVu8n9HPEkkULdxZugs0zxQZXJFGtmjvzKrlFlS0CH2lfehbsKb0TTRbDRRZY9L9Xnf/xEsLe26z73dTXGZKU7By1v0OoXEXTgfqUgQCyDU0/Nkk+6P65EfaJ8qpAoGAN3pP9Fp/y4r2mmqLyWTevLcCArWCaq7kCgPY+KwtG73YsVrBetgglFR1J4Of+ctRWgOXpRMIWOaQG20n2UgLkBxonBahIVFFBYr6BD7dW4O5mRv+w1QehQ5hFaMWcZhAln9qF0+vKiGEzusttTDxOUsrVd58Zzhd2Eb4reW2xN0CgYBF2Ov2qR4N+NfyG9XTJH14Dree35CdFTOYaMlQM9FiMiuTHLDklsvCXZ2Nzlbgi26YXlmHVpk+moLk6vkwTy00zdfFWeWJn34DgJsNzHDR/C7MDRfcfJxRGbMAuaErbkwM32RtYtJ8zWxawTGBjUA3T8M+pcQTynmL5rZBWAxf+Q==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo21tnv0+X4G72/zdlc9CrdAuijRmZThhpXmL5ACfcNC/qO/V0geZzgDeIz1yCzfyfmUODsHBKOllZFGsU5dBZ/TU2xcskb5epQbz1bkpnktV7a8PEEDIkLPWsFViVHEpJPEHVjRADSjusl0/0VYox/yxjyI2dSsYu3r/ToW49uYmmul1yjAyRo2yC32lO0fGgCP0wyawl4Y6xCGEW6Ra89a08MxM2ANNgixEYwe7wxxK63SY+Hm4NIoDoWiX0Jo0jStm8mY9WmniaWBEkceVZtFu3RBZ1wIrshxevP7apEvpNmijC8zPqg1Vo4dCv79cEckexpn3v5dizeEK/tKciwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://uuwa32.natappfree.cc/index/fallback";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8083/homeMain";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
