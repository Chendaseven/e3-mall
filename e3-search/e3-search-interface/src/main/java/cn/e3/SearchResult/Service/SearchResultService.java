package cn.e3.SearchResult.Service;


//import org.apache.solr.client.solrj.SolrServerException;

import cn.e3.search.pojo.SearchResult;

public interface SearchResultService {
	SearchResult search(String keyWord, int page, int rows) throws Exception;
}
