package cn.e3.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.search.service.SearchItemDetails;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

@Service
public class SearchItemDetailsImpl implements SearchItemDetails {
	
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public TbItem selectItemDetails(long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItem;
	}

	@Override
	public TbItemDesc selectItemDesc(long id) {
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return tbItemDesc;
	}

}
