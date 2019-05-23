package cn.e3.cart.service;

import java.util.List;

import cn.e3mall.pojo.TbItem;
import util.E3Result;

public interface CartService {
	//登陆状态添加购物车
	E3Result addCart(long userId,long itemId,int num);
	//合并cookie中的购物车数据到redis
	E3Result mergeCart(long userId,List<TbItem> itemList);
	//查询redis中所有购物车数据
	List<TbItem> getCartList(long userId);
	//更新商品数量
	E3Result updateCartNum(long userId,long itemId,int num);
	//删除商品
	E3Result deleteCartItem(long userId,long itemId);
}
