package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {

	TaotaoResult addCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	
	List<CartItem> getCartItemList(HttpServletRequest request,HttpServletResponse response);
	
	List<CartItem> updateCartItemList(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	
	List<CartItem> deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);
	
}
