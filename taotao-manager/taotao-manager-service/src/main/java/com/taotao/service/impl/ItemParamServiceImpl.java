package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.ItemParam;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example=new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list!=null && list.size()>0){
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}
	

	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		TbItemParamExample example=new TbItemParamExample();
		PageHelper.startPage(page, rows);
//		List<ItemParam> list = itemParamMapper.selectParamWithTwoTable();
		List<TbItemParam> list1 = itemParamMapper.selectByExampleWithBLOBs(example);
		List<ItemParam> list=new ArrayList<>();
		EUDataGridResult result=new EUDataGridResult();
		for (TbItemParam tbItemParam : list1) {
			ItemParam itemParam=new ItemParam();
			itemParam.setCreated(tbItemParam.getCreated());
			itemParam.setId(tbItemParam.getId());
			itemParam.setItemCatId(tbItemParam.getItemCatId());
			itemParam.setParamData(tbItemParam.getParamData());
			itemParam.setUpdated(tbItemParam.getUpdated());
			TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbItemParam.getItemCatId());
			itemParam.setItemCatName(itemCat.getName());
			list.add(itemParam);
		}
		result.setRows(list);
		PageInfo<ItemParam> pageInfo=new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
