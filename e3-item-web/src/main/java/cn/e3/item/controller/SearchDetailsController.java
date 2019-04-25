package cn.e3.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3.search.service.SearchItemDetails;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

@Controller
public class SearchDetailsController {
	@Autowired
	private SearchItemDetails searchItemDetails;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model) {
		//根据搜索商品
		TbItem tbItem = searchItemDetails.selectItemDetails(itemId);
		//将TbItem转化为TbItemDetails，因为前端展示页面没有images字段，所以需要重写TbItem
		TbItemDetails item = new TbItemDetails(tbItem);
		//根据id搜索商品描述信息
		TbItemDesc tbItemDesc = searchItemDetails.selectItemDesc(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		
		return "item";
	}
	
	
}
