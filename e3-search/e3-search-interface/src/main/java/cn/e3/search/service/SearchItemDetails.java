package cn.e3.search.service;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface SearchItemDetails {
	TbItem selectItemDetails(long id);
	TbItemDesc selectItemDesc(long id);
}
