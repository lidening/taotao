package com.taotao.portal.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	//添加购物车
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,@RequestParam(defaultValue="1")Integer num,HttpServletRequest request,HttpServletResponse response){
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
		
	}
	
	@RequestMapping("/success")
	public String showCart(){
		return "cartSuccess";	
	}
	//展示购物车列表
	@RequestMapping("/cart")
	public String getCartItemList(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList",list);
		return "cart";
	}
	//修改购物车数量
	@RequestMapping(value="/update/num/{itemId}/{num}",method=RequestMethod.POST)
	public String updateCart(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> list = cartService.updateCartItemList(itemId, num, request, response);
		model.addAttribute("cartList",list);
		return "cart";
	}
	//删除购物车物品
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> list = cartService.deleteCartItem(itemId, request, response);
		model.addAttribute("cartList",list);
		return "cart";
			
	}
}
