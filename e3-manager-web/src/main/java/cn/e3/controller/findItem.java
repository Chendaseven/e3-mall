package cn.e3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.service.ItemService;
import cn.e3mall.pojo.TbItem;

@Controller
public class FindItem {
	@Autowired
	private ItemService itemservice;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem findItemById(@PathVariable Long id) {
		TbItem tbitem = itemservice.findById(id);
		tbitem.toString();
		return tbitem;
	}
	

}
