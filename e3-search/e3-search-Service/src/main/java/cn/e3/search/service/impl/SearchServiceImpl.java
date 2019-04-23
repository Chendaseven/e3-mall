package cn.e3.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.search.Dao.SearchMapper;
import cn.e3.search.pojo.SearchPojo;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import util.E3Result;
/**
 * 将所有的Item商品数据导入solr索引库
 * @ClassName SearchService
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月7日 下午7:36:21
 */
@Service
public class SearchServiceImpl implements cn.e3.search.service.SearchService {
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public E3Result inputAllDate(){
		//1、获取搜索到的所有商品信息
		List<SearchPojo> AllItem = searchMapper.searchList();
		try {
		for (SearchPojo searchPojo : AllItem) {
			SolrInputDocument solrInputDocument = new SolrInputDocument();
			solrInputDocument.addField("id", searchPojo.getId());
			solrInputDocument.addField("item_title", searchPojo.getTitle());
			solrInputDocument.addField("item_sell_point", searchPojo.getSell_point());
			solrInputDocument.addField("item_price", searchPojo.getPrice());
			solrInputDocument.addField("item_image", searchPojo.getImage());
			solrInputDocument.addField("item_category_name", searchPojo.getCatagoryName());
			solrServer.add(solrInputDocument);
		}
			solrServer.commit();
			return E3Result.ok();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return E3Result.build(100, "SolrServer服务器链接失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return E3Result.build(300, "链接失败");
		}
		
	}

}
