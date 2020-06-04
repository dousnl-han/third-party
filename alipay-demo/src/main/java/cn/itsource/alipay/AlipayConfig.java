package cn.itsource.alipay;

import java.io.FileWriter;
import java.io.IOException;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/4 11:21
 */
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092000552215";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDC96ZyYcsRpb3fOlESpb8hcG7L/bFiSCQriAsOjpX+UIZibEZhfdXfy6vgvUruryHWX0KoLyC0QAv91JFJwbcDVIGGkrStbRltuRctEtNcHNR4v7fbVDOFG9/lDnVVJrA0tMPwXBnCErexN0KGq2+83/wp0ikB1OsaCPLA+FidtgruNwmDO0BmtcEUTc4FQqrveXJxbZjKlVCW7nl2iEYFlhWLMqB8XBRzRAie5tH+vFMvA5YgVTJ6IXcFH/hGE5eHfMrWnQzCDYZOFiDwhz5m4hz+irpXzR/uc7gpS1RYPwtRdCRova0hNJb9OMUFJxR7wSSOjtTKcV3UX4QFjqnFAgMBAAECggEAbaQFR3e1UhRJ5ZoO85SgGnb25+Kf+WwmYxCbXzFSKnUwTXMkVTkFqFgKLVVmKTn1pm9ltdzi4FrQI4uBtN+qQsYoHPytfIXI8gpPfTqD/936onj2NPJyEp3tWAu8cglSuCEbuzSgC6KpCJDR6ZfJO4cecIpOI0iQedCka159vxogRqVSiiCs6TGTg8y3OzILSxaRRmUhMMyl0s7zSiXn+5KcnKZmNmyehU+STZCFFoGcy1i47K0b10vIx9/UlMGLr6Bx40TIhub2Wr8+E+/nrnGo1Jyz07rSOqlqAvs/wtUnRlyQu37OOS3USJw6B8DVa3zPJ8RPPMr7K7PqalJ3AQKBgQDlsC3PD4/yD8FWJu0TXO9cDpJcOjU+YG9gaZn4eh5hJc/XmnlTYQMCfOalLXvXCGuhAgKUQ8tRtgZbEXj2hwWbXRpUxvULZXPqRs7o/rrkVYpgglYWpCU07nl4l5dUnBwzAuiVIDitexGJw+Qxn5v9Jts5z/QiPSg1QfGz2XoYQQKBgQDZTUA6ARA0k8ywEs8aY8zCC2vE+VH8wN1sULjlTvQJuFZGnT67BhZDN8oz0cFQ+d3lIgoR8isrtIOwkuGmYTDIOJwBoccOVRS20lu9GAXh5af0kQFvuIcR6Kls4EarIyXhMb2ZWtGhYgzsy9sMcLMkMZNNU73b2loH6datoJwQhQKBgFZ4wnY+1KF7/vyRQRXWGiMdNUrVezAVnDi+6QGb8A4A70OrW1vhnQF1xluPSR1GhUajfBcXgtcrYS8t381oPdsk7QHXuW9l7YLYz5L2O6Q09BirWP/DImLQGRkmcQmgkBlAUhG6hLwDJjIodqOcBF0FIOmd+bpeLeIHBpkAGjBBAoGBAJ1IuRBC7MPhpupa8uSbD/3z3lF4VMupnZtk4i+38hOHBYufDWS5hyuoGgdf75j5Ao9zD+7lZjIAOsF3zzRqbLC/qMvVmQrasl+R6yz3Umqrm+H9gxima72eteNEgmIk0qopIp75iXpSSiCrfSWH/WJZL9Fsp6OWbEwGCYkrE0sxAoGBAJHkyiG1e4N6RI2/vxUhGM1CGkZVayLVJoCEN08iu+8YvSvrqc12rs6h9dtaThbh8+3poMAVpbetebMolLTpEM52WyxxXrEx/lWemvSfobalpdnyC+9A4gZm22DPFFJQtkngVNVqONDnrfNT/+m9ZBR0vvRryJ7XygX0hyu+2Zhd";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsJMbCYykTyAAy/8jNxX9kzdBgBfgUhWl/TplIL7Adyz12ljTwiERt4mSEENF3yJP0bWxelFr0YwPTkKTdCvMs7gbM+XfDbX/D9/YIwAOmbvaykPgZiTHOM2TXroOwg8OZznoPmmzEHT4Dz5gWQQwmKYQnTNCjqSYSF8H9SOJCudBXWCvIs0DLXfebhMyyBiDzTBxKFdTAFbrampJNmfzfZ6ifgLIHhZhDWENlUQBFZKOHDQxZalYuNUM4PhOuCdw/RA0eKVHNnk44ezLxBF9Ys6o+96YZoiZ3n96QPmgrinbyLn/yYOIJDLOi6QwKAt8EBShRnGDYmfD+q5PUMH9gQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/payResult/return_url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关 //
    //public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";// 正式
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";// 测试

    // 支付宝网关 日志
    public static String log_path = "C:\\";


    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
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
