package cn.e3.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.search.service.SearchService;
/**
 *  将商品列表导入solr索引库的controller层
 * @ClassName SearchController
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月7日 下午8:36:17
 */
import util.E3Result;
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result inportAllItem() {
		E3Result result = searchService.inputAllDate();
		return result;
	}
	
}
