package cn.e3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.service.ItemCatService;
import cn.e3mall.EasyUi.EasyUiTreeNode;

/**
 * 
 * @author Administrator
 * 使用@RequestParam，使前端的变量id赋值给parentId
 */
@Controller
public class getCatList {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUiTreeNode> getNodeList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EasyUiTreeNode> selectItemCat = itemCatService.selectItemCat(parentId);
		return selectItemCat;
	}
}
