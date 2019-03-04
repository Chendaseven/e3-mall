package cn.e3.content.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.e3.content.service.ContentGategoryService;
import cn.e3.redis.JedisClient;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import util.E3Result;
@Service
public class contentGategoryServiceImpl implements ContentGategoryService {
	
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Override
	public List<TbContent> selectAll(long id) {
		//加入redis缓存，先看redis缓存中是否有数据，如果有那么直接返回该数据
		try {
			String TbContentList = jedisClient.hget(CONTENT_LIST, Long.toString(id));
			if(org.apache.commons.lang3.StringUtils.isNotBlank(TbContentList)) {
				List<TbContent> parseObject = 	JSON.parseArray(TbContentList, TbContent.class);							
				
				return parseObject;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: handle exception
		//如果redis缓存中没有改数据，那么直接从数据库中查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> TbContentList = tbContentMapper.selectByExample(example);
		try {
			//将查到的数据加在redis缓存中，以便下次直接使用
			String result = JSON.toJSONString(TbContentList);
			jedisClient.hset(CONTENT_LIST, Long.toString(id), result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return TbContentList;	
	}

	@Override
	public void addContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		int insertSelective = tbContentMapper.insertSelective(tbContent);
		//缓存同步实现，就是每次增加content的时候把redis中的缓存删除
		jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
		
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
