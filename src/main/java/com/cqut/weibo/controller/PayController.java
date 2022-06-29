package com.cqut.weibo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cqut.weibo.config.AlipayConfig;
import com.cqut.weibo.service.UsersService;
import com.cqut.weibo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class PayController {

    @Autowired
    private UsersService usersService;

    /**
     * 支付接口
     *
     * @param total_amount
     * @param body
     * @param response
     * @return java.lang.String
     * @author HL.
     * @Date 14:03 2021/5/13
     */
    @PostMapping("/pay")
    public String payController(String total_amount, String body, HttpServletResponse response) throws IOException {
        //1.获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl, //请求网关
                AlipayConfig.APP_ID,  //收款人ID
                AlipayConfig.APP_PRIVATE_KEY,  //私钥
                "json",  //返回格式
                AlipayConfig.CHARSET,  //字符编码格式
                AlipayConfig.ALIPAY_PUBLIC_KEY, //公钥
                AlipayConfig.sign_type); //加密方式

        //设置请求参数
        //2.创建Request请求
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        //3.封装传入参数
        //设置异步回调地址
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        //设置同步回调地址
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();

        //用雪花算法计算一个订单编号
        model.setOutTradeNo(String.valueOf(new IdWorker(1, 1, 1).nextId()));
        //商品的标题/交易标题/订单标题/订单关键字等
        model.setSubject("会员充值");
        //订单总金额
        model.setTotalAmount(total_amount);
        //该订单的备注、描述、明细等。(这里传入用户id号)
        model.setBody(body);

        alipayRequest.setBizModel(model);

        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //        + "\"total_amount\":\""+ total_amount +"\","
        //        + "\"subject\":\""+ subject +"\","
        //        + "\"body\":\""+ body +"\","
        //        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        //response.getWriter().write(form);//直接将完整的表单html输出到页面
        //response.getWriter().flush();
        //response.getWriter().close();
        return form;
    }

    /**
     * 支付成功的回调
     */
    @PostMapping("/fallback")
    public String fallback(HttpServletRequest request) throws ParseException {
        //得到各种订单支付的信息
        Map map = request.getParameterMap();
        if (((String[]) map.get("trade_status"))[0].equals("TRADE_SUCCESS")) {
            //将订单信息存入数据库
            usersService.addOrderInformation(map);
            return "success";
        }
        return "failed";
    }

    /**
     * 支付成功的提醒
     */
    @PostMapping("/paySuccess")
    public String paySuccess(HttpServletRequest request) {
        return "success";
    }
}
