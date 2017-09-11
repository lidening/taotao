package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;

@Controller
@RequestMapping("/cache/sync")
public class RedisSyncController {
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentId}")
	@ResponseBody
	public TaotaoResult ContentRedisSync(@PathVariable Long contentId){
		TaotaoResult result = redisService.ContentRedisSync(contentId);
		return result;
	}
	
	@RequestMapping("/itemcatlist")
	@ResponseBody
	public TaotaoResult CatListRedisSync(){
		TaotaoResult result = redisService.CatListRedisSync();
		return result;
		
	}
}
