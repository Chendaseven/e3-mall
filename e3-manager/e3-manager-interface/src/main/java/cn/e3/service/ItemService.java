package cn.e3.service;

import java.util.List;


import cn.e3mall.EasyUi.EasyUiReslut;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import util.E3Result;

public interface ItemService {
	TbItem findById(Long id);
	//查找所有TbItem并分页管理
	EasyUiReslut findAllItem(int page,int rows);
	//用于新增商品时向前端返回值E3Result.status
	E3Result addItem(TbItem tbItem,String desc);
	//查找商品描述
	TbItemDesc selectDesc(long id);
	//删除TbItem
	E3Result deleteItem(String ids);
	//TbItem Update
	E3Result updateItem(TbItem tbItem,String desc);
}
