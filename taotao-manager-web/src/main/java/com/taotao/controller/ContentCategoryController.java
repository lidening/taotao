package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService ContentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getCategoryListByParentId(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EUTreeNode> result = ContentCategoryService.getCategoryList(parentId);
		return result;
	}
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult insertCategory(long parentId,String name){
		TaotaoResult result = ContentCategoryService.insertCategory(parentId, name);
		return result;
	}
	@RequestMapping("/delete/")
	@ResponseBody
	public TaotaoResult removeCategory(Long id){
		ContentCategoryService.removeCategory(id);
		return TaotaoResult.ok();
	}
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateCategory(long id,String name){
		ContentCategoryService.updateCategory(id, name);
		return TaotaoResult.ok();
	}
	
}
