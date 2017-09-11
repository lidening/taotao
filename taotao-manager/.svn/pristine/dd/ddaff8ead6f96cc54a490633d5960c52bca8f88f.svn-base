package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${REDIS_BASE_URL}")
	private String REDIS_BASE_URL;
	@Value("${REDIS_CONTENT_SYC_URL}")
	private String REDIS_CONTENT_SYC_URL;
	@Override
	public EUDataGridResult getContentList(long categoryId, int page, int rows) {
		TbContentExample example =new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		EUDataGridResult result=new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//调用清除前台广告缓存
		try {
			HttpClientUtil.doGet(REDIS_BASE_URL+REDIS_CONTENT_SYC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		//调用清除前台广告缓存
		try {
			HttpClientUtil.doGet(REDIS_BASE_URL+REDIS_CONTENT_SYC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteContent(String ids) {
//		contentMapper.deleteByExample(example);
		String[] strings = ids.split(",");
		TbContent tbContent = contentMapper.selectByPrimaryKey(Long.parseLong(strings[0]));
		for (String string : strings) {
			contentMapper.deleteByPrimaryKey(Long.parseLong(string));
			//调用清除前台广告缓存
			try {
				HttpClientUtil.doGet(REDIS_BASE_URL+REDIS_CONTENT_SYC_URL+tbContent.getCategoryId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return TaotaoResult.ok();
	}

}
