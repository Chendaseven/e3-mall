package cn.e3.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.content.service.ContentGategoryService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import util.E3Result;
@Service
public class contentGategoryServiceImpl implements ContentGategoryService {
	
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Override
	public List<TbContent> selectAll(long id) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> TbContentList = tbContentMapper.selectByExample(example);
		return TbContentList;
	}

	@Override
	public void addContent(TbContent tbContent) {
		int insertSelective = tbContentMapper.insertSelective(tbContent);
		System.out.println(insertSelective);
	}

	@Override
	public void editContent(TbContent tbContent) {
		// 编辑更新内容
		int updateByPrimaryKey = tbContentMapper.updateByPrimaryKey(tbContent);
		
	}

	@Override
	public void deleteContent(long ids) {
		// 删除某内容
		int deleteByPrimaryKey = tbContentMapper.deleteByPrimaryKey(ids);
	}

}
