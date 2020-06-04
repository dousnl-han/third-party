package cn.itsource.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import cn.itsource.alipay.AlipayConfig;

/**
 * @author 阳彦刚
 *	支付宝主页点击付款
 */
@Controller
@RequestMapping("/alipay")
public class ALiPayController {
	
	@RequestMapping("/alipayTradePagePay")
	public String payTradePagePay(HttpServletRequest request){
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no;
		//付款金额，必填
		String total_amount;
		//订单名称，必填
		String subject;
		//商品描述，可空
		String body;
		try {
			out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
			subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			//请求
			String result = alipayClient.pageExecute(alipayRequest).getBody();
			//输出
			request.setAttribute("result",result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "pay";
	}
}
