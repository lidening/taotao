package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode euTreeNode=new EUTreeNode();
			euTreeNode.setId(tbContentCategory.getId());
			euTreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			euTreeNode.setText(tbContentCategory.getName());
			resultList.add(euTreeNode);
		}
		
		return resultList;
	}
	@Override
	public TaotaoResult insertCategory(long parentId,String name) {
		TbContentCategory example=new TbContentCategory();
		example.setCreated(new Date());
		example.setUpdated(new Date());
		example.setParentId(parentId);
		example.setName(name);
		example.setSortOrder(1);
		example.setStatus(1);
		example.setIsParent(false);
		contentCategoryMapper.insert(example);
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok(example);
	}
	@Override
	public TaotaoResult removeCategory(long id) {
		//如果删除的是父节点，该父节点的子节点一并删除
//		removeAll(parentId,id);
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		//先删除该节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		//查询出对应parentId的子节点
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(category.getParentId());
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//如果没有子节点则修改isParent为false
		if(list==null || list.size()==0){
			TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
			contentCategory.setIsParent(false);
//			contentCategory.setCreated(contentCategory.getCreated());
			contentCategory.setUpdated(new Date());
//			contentCategory.setId(contentCategory.getId());
//			contentCategory.setName(contentCategory.getName());
//			contentCategory.setParentId(contentCategory.getParentId());
//			contentCategory.setSortOrder(contentCategory.getSortOrder());
//			contentCategory.setStatus(contentCategory.getStatus());
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		return TaotaoResult.ok();
	}
	// TODO 递归删除父节点下的所有节点
//	private TaotaoResult removeAll(long parentId, long id){
//		
//		
//	}
	
	@Override
	public TaotaoResult updateCategory(long id, String name) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		category.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKey(category);
		return TaotaoResult.ok();
	}

}
