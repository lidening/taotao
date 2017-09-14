package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

//	@Autowired
//	private HttpClientUtil httpClientUtil;
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		//获取订单号
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult taotaoResult = TaotaoResult.format(json);
		if(taotaoResult.getStatus()==200){
			Object orderId= taotaoResult.getData();
			return orderId.toString();
		}
		return "";
	}

}
