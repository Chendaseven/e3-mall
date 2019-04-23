package e3.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
/**
 * 使用solrJ管理索引库，使用solrJ可以实现索引库的增删改查操作
 * @ClassName SearchTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月5日 下午9:06:04
 */

public class SearchTest {
	/**
	 * 添加文档
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void addDocument() throws SolrServerException, IOException {
		//1、把solrJ的jar包添加到工程中
		//2、创建一个SolrServer，使用HttpSolrServer创建对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//3、创建一个文档对象SolrInputDocument对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		//4、向文档中添加域，必须有id域，域的名称必须在schema.xml中定义
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "测试商品");
		solrInputDocument.addField("item_price", 199);
		solrInputDocument.addField("item_sell_point", "特卖");
		//5、把文档添加到索引库中
		solrServer.add(solrInputDocument);
		//6、提交
		solrServer.commit();
	}
	
	/**
	 * 简单查询
	 * @throws SolrServerException
	 */
	@Test
	public void searchTest() throws SolrServerException {
		//1、创建一个solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//2、创建一个solrQuery对象
		SolrQuery query = new SolrQuery();
		//3、向solrQuery中添加查询条件，过滤条件
		query.setQuery("*:*");
		//4、执行查询，得到一个Response对象
		QueryResponse response = solrServer.query(query);
		//5、取查询结果
		SolrDocumentList documentList = response.getResults();
		System.out.println("查询记录的总记录数："+documentList.getNumFound());
		//6、遍历结果打印
		for (SolrDocument solrDocument : documentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_sell_point"));
		}
	}
	
	/**
	 * 带高亮显示的查询
	 * @throws SolrServerException 
	 */
	@Test
	public void test2() throws SolrServerException {
		//1、创建一个solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//2、创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//3、向SolrQuery中添加查询条件
		query.setQuery("测试");
		//指定默认搜索域
		query.set("df", "item_keywords");
		//开启高亮显示
		query.setHighlight(true);
		//高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//4、执行查询，得到一个Response对象
		QueryResponse response = solrServer.query(query);
		//5、取查询结果
		SolrDocumentList documentList = response.getResults();
		System.out.println("查询记录的总记录数："+documentList.getNumFound());
		//6、遍历结果打印
		for (SolrDocument solrDocument : documentList) {
			
			System.out.println(solrDocument.get("id"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = null;
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}

			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
		
		
	
	}
	/**
	 * 删除文档
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	//根据id删除
	@Test
	public void deleDocumentById() throws SolrServerException, IOException {
		//1、创建一个SolrServer对像
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//2、调用SolrServer对象的根据id删除的方法
		solrServer.deleteById("679533");
		//3、提交
		solrServer.commit();
	}
	
	//根据查询删除
	@Test
	public void deleDocumentByQuery() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		solrServer.deleteByQuery("item_title:测试商品");
		solrServer.commit();
	}
}
