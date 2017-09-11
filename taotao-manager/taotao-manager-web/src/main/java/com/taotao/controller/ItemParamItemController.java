package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	@RequestMapping("/showitem/{itemId}")
	public String getItemParamByItemId(@PathVariable Long itemId,Model model){
		String result = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam",result);
		return "item";
	}
	
}
