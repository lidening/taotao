package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.rest.service.RedisService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Value("${INDEX_CAT_REDIS_KEY}")
	private String INDEX_CAT_REDIS_KEY;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public CatResult getItemCatList() {
		//查询redis缓存
		try {
			String string = jedisClient.get(INDEX_CAT_REDIS_KEY);
			if(!StringUtils.isEmpty(string)){
				return JsonUtils.jsonToPojo(string, CatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CatResult result = new CatResult();
		result.setData(getCatList(0));
		
		//写入redis缓存
		try {
			jedisClient.set(INDEX_CAT_REDIS_KEY,JsonUtils.objectToJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private List<?> getCatList(long parentId){		
		TbItemCatExample example =new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List nodeList =new ArrayList<>();
		int conut=0;
		for (TbItemCat tbItemCat : list) {
			if(tbItemCat.getIsParent()){
				CatNode catNode=new CatNode();
				if(parentId==0){
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				nodeList.add(catNode);
				conut++;
				if(parentId==0 && conut>=14){
					break;
				}
			}else{
				nodeList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		
		return nodeList;
	}
}
