package cn.e3.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentGategoryService;
import cn.e3mall.pojo.TbContent;
import util.E3Result;

@Controller
public class contentCategoryController {
	
	@Autowired
	private ContentGategoryService contentGategoryService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public List<TbContent> selectAllContent(long categoryId){
		List<TbContent> result = contentGategoryService.selectAll(categoryId);
		return result;
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent tbContent) {
		contentGategoryService.addContent(tbContent);
		return E3Result.ok();
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateContent(TbContent tbContent) {
		contentGategoryService.editContent(tbContent);
		return E3Result.ok();
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(String ids) {
		long id = Long.parseLong(ids);
		contentGategoryService.deleteContent(id);
		return E3Result.ok();
	}
	
}
