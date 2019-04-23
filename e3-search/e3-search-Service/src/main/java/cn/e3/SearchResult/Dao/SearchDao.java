package cn.e3.SearchResult.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3.search.pojo.SearchPojo;
import cn.e3.search.pojo.SearchResult;

@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws SolrServerException {
		//根据查询条件查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		long numFound = solrDocumentList.getNumFound();
		//创建一个返回结果对象
		SearchResult result = new SearchResult();
		result.setRecordCount((int)numFound);
		//创建一个商品列表对象
		List<SearchPojo> itemList = new ArrayList<>();
		//取商品列表
		//取高亮后的结果
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			//取商品信息
			SearchPojo searchPojo = new SearchPojo();
			searchPojo.setCatagoryName((String) solrDocument.get("item_category_name"));
			searchPojo.setId((String)(solrDocument.get("id")));
			searchPojo.setImage((String) solrDocument.get("item_image"));
			searchPojo.setPrice((long) solrDocument.get("item_price"));
			searchPojo.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			searchPojo.setTitle(itemTitle);
			//添加到商品列表
			itemList.add(searchPojo);
		}
		//把列表添加到返回结果对象中
		result.setItemList(itemList);
		return result;

	}
}
