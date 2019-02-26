package cn.e3.content.service;


import java.util.List;

import cn.e3mall.EasyUi.EasyUiTreeNode;
import util.E3Result;

public interface ContentService {
	//查询所有的内容分类
	List<EasyUiTreeNode> selectAllContent(long parentId);
	//新建内容分类
	E3Result addContentGategory(long parentId,String name);
	//删除内容分类
	E3Result deleteContent(long id);
	//对内容分类重命名
	void updateContent(long id,String name);
}
