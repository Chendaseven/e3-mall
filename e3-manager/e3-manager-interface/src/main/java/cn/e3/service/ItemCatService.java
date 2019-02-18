package cn.e3.service;

import java.util.List;

import cn.e3mall.EasyUi.EasyUiTreeNode;

public interface ItemCatService {
	List<EasyUiTreeNode> selectItemCat(long parentId);
}
