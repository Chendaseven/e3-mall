package cn.e3.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentService;
import cn.e3mall.EasyUi.EasyUiTreeNode;
import util.E3Result;

@Controller
public class contentController {
	@Autowired 
	private ContentService contentService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUiTreeNode> indexPage(@RequestParam(value="id",defaultValue="0") long parentId) {
		List<EasyUiTreeNode> contentResult = contentService.selectAllContent(parentId);
		return contentResult;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result createCategory(long parentId,String name) {
		E3Result e3Result = contentService.addContentGategory(parentId, name);
		return e3Result;
	}
	/**
	 * 删除节点
	 * @param id
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteCategory(long id) {
		contentService.deleteContent(id);
		return E3Result.ok();
	}
	
	/**
	 * 更新节点name
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateCategory(long id,String name) {
		contentService.updateContent(id, name);
		return E3Result.ok();
	}
	
}
