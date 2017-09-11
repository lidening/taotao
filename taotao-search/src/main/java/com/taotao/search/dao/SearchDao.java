package com.taotao.search.dao;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
