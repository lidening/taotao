package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString,int page);
}
