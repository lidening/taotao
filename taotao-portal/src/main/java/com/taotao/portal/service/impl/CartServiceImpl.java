package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Override
	public TaotaoResult addCartItem(long itemId, int num,HttpServletRequest request,HttpServletResponse response) {
		//cookie中获取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		CartItem item=null;
		//若购物车中有此商品，直接增加数量
		for (CartItem cartItem : cartItemList) {
			if(cartItem.getId()==itemId){
				cartItem.setNum(cartItem.getNum()+num);
				item=cartItem;
				break;
			}
		}
		//若购物车中无此商品，添加商品
		if(null==item){
			item=new CartItem();
			String itemjson = HttpClientUtil.doGet(REST_BASE_URL+ ITEM_INFO_URL +itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(itemjson, TbItem.class);
			if(taotaoResult.getStatus()==200){
				TbItem tbItem=(TbItem) taotaoResult.getData();
				item.setId(itemId);
				item.setNum(num);
				item.setPrice(tbItem.getPrice());
				item.setTitle(tbItem.getTitle());
				//图片是由逗号分割的图片地址字符串组成，取第一张图片
				item.setImage(tbItem.getImage()==null?"":tbItem.getImage().split(",")[0]);
			}
			cartItemList.add(item);
		}
		//购物车列表写回cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList),true);
		return TaotaoResult.ok();
	}
	private List<CartItem> getCartItemList(HttpServletRequest request){
		//cookie中获取商品列表
		String cartjson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(cartjson==null){
			return new ArrayList<>();
		}
		
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartjson, CartItem.class);
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return new ArrayList<>();
	}
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> list = getCartItemList(request);
		return list;
	}
	@Override
	public List<CartItem> updateCartItemList(long itemId,int num,HttpServletRequest request, HttpServletResponse response) {
		//cookie中获取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if(cartItem.getId()==itemId){
				cartItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList),true);
		return cartItemList;
	}
	@Override
	public List<CartItem> deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//cookie中获取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		if(null!=cartItemList && cartItemList.size()>0){
			for (CartItem cartItem : cartItemList) {
				if(cartItem.getId()==itemId){
					cartItemList.remove(cartItem);
					break;
				}
			}
			CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList),true);
			return cartItemList;
			
		}else{
			return new ArrayList<>();
		}
	}

}
