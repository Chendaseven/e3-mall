package cn.e3.content.service;

import java.util.List;

import cn.e3mall.pojo.TbContent;
import util.E3Result;

public interface ContentGategoryService {
	//显示每个内容分类下的内容
	List<TbContent> selectAll(long id);
	//对某个内容分类加上其内容
	void addContent(TbContent tbContent);
	//编辑更新某个内容
	void editContent(TbContent tbContent);
	//删除某个内容
	void deleteContent(long ids);
}
