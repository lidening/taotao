package com.taotao.rest.jedis;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void AddDOcument() throws Exception{
		SolrServer server=new HttpSolrServer("http://192.168.25.157:8080/solr");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id","test001");
		document.addField("item_title", "测试商品002");
		document.addField("item_price", 33333);
		server.add(document);
		server.commit();
	}
	@Test
	public void DeleteDocument() throws Exception{
		SolrServer server=new HttpSolrServer("http://192.168.25.157:8080/solr");
		server.deleteById("change.me");
		server.commit();
			
	}
	@Test
	public void qureyDocument() throws Exception{
		SolrServer server=new HttpSolrServer("http://192.168.25.157:8080/solr");
		SolrQuery query=new SolrQuery();
		query.setQuery("*:*");
		query.setStart(5);
		query.setRows(20);
		QueryResponse response=server.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("共查询："+solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			
		}
		
	}
}
