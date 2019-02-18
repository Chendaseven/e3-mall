package cn.e3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.service.ItemService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import util.E3Result;

/**
 * TbItemController包
 * @ClassName TbItemController
 * @Description TbItem的新增、修改及删除
 * @Author Chen Peng
 * @Date 2019年2月18日 下午4:12:38
 */
@Controller
public class TbItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * TbItem的新增
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result TbItemAdd(TbItem tbItem,String desc) {
		E3Result e3Result = itemService.addItem(tbItem, desc);
		return e3Result;
	}
	
	/**
	 * 显示Item编辑页面
	 * @return "item-edit"
	 */
	@RequestMapping("/rest/page/item-edit")
	public String selectItem() {
		//TbItem tbItem = itemService.findById(id);
		return "item-edit";
	}
	/**
	 * 
	 * @param id
	 * @return 加载商品描述
	 */
	@RequestMapping(value="/rest/item/query/item/desc/{id}",method=RequestMethod.GET)
	@ResponseBody
	public E3Result findDesc(@PathVariable long id) {
		TbItemDesc tbItemDesc = itemService.selectDesc(id);
		E3Result e3Result = new E3Result(tbItemDesc);
		return e3Result;
	}
	
	/**
	 * 删除指定的商品
	 * @param params
	 * @return E3Result
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteItem(String ids) {
		E3Result deleteResult = itemService.deleteItem(ids);
		return deleteResult;
	}
	
	
	
	
	

	
}
