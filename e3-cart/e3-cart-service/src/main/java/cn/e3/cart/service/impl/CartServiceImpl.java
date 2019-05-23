package cn.e3.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.e3.cart.service.CartService;
import cn.e3.redis.JedisClient;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import util.E3Result;

public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		List<TbItem> list = new ArrayList<>();
		//向redis中添加购物车
		//数据类型是hash key：用户id field：商品id  value：商品信息
		//1、判断商品是否在redis中存在
		String value = jedisClient.hget(userId+"", itemId+"");
		if(StringUtils.isNotBlank(value)) {
			//如果存在数量相加
			TbItem item = JSON.parseObject(value, TbItem.class);
			//List<TbItem> item = JSON.parseArray(value, TbItem.class);
			item.setNum(item.getNum()+num);
			//并重新redis中的数据
			list.add(item);
			jedisClient.hset(userId+"", itemId+"", list.toString());
			return  E3Result.ok();
		}
		//2、如果不存在根据id取商品信息
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		list.add(tbItem);
		//添加到购物车列表
		jedisClient.hset(userId+"", itemId+"", list.toString());
		//返回成功
		return E3Result.ok();
	}
	
	/**
	 * 合并cookie中的数据到redis
	 */
	@Override
	public E3Result mergeCart(long userId,List<TbItem> itemList) {
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}
	/**
	 * 查询购物车数据
	 */
	@Override
	public List<TbItem> getCartList(long userId) {
		List<String> jsonList = jedisClient.hvals(userId+"");
		List<TbItem> itemList = new ArrayList<>();
		for (String string : jsonList) {
			TbItem tbItem = JSON.parseObject(string, TbItem.class);
			itemList.add(tbItem);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		List<TbItem> list = new ArrayList<>();
		//从redis中取商品信息
		String json = jedisClient.hget(userId+"", itemId+"");
		//转换为pojo
		TbItem tbItem = JSON.parseObject(json, TbItem.class);
		tbItem.setNum(num);
		//list.add(tbItem);
		jedisClient.hset(userId+"", itemId+"", JSON.toJSONString(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		jedisClient.hdel(userId+"", itemId+"");
		return E3Result.ok();
	}

}
