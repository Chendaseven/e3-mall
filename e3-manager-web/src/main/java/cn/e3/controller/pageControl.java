package cn.e3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.service.ItemService;
import cn.e3mall.EasyUi.EasyUiReslut;
import cn.e3mall.pojo.TbItem;
@Controller
public class PageControl {
	
	@Autowired
	private ItemService itemservice;
	
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String itemPage(@PathVariable String page) {
		return page;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUiReslut itemList(int page,int rows) {
		EasyUiReslut easyUiResult = itemservice.findAllItem(page,rows);
//		System.out.println(easyUiResult.getTotal());
//		for(int i=0;i<easyUiResult.getRows().size();i++) {
//			System.out.println(easyUiResult.getRows().get(i));
//		}
		return easyUiResult;
	}
	
}
