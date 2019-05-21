package cn.e3.cart.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.e3.service.ItemService;
import cn.e3mall.pojo.TbItem;
import util.CookieUtils;
import util.E3Result;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 商品添加到购物车方法，前端传入商品id和数量，此时为未登陆账号下添加购物车
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String cartAdd(@PathVariable Long itemId,@RequestParam(defaultValue="1") int num,
			HttpServletRequest request,HttpServletResponse response) {
		
		//根据商品id搜索TbItem
		TbItem tbItem = itemService.findById(itemId);
		//从cookie中查询商品列表
		String cartValue = CookieUtils.getCookieValue(request, "cart", true);
		List<TbItem> list = JSON.parseArray(cartValue, TbItem.class);
		//判断商品是否在列表中，如果存在则增加数量，不存在则添加商品至cookie
		if(StringUtils.isBlank(cartValue)) {
			//如果cookie中不存在数据，那么将此商品加入购物车
			tbItem.setNum(num);
			tbItem.setImage(tbItem.getImage().split(",")[0]);
			list.add(tbItem);
			String jsonString = JSON.toJSONString(list);
			CookieUtils.setCookie(request, response, "cart", jsonString, 3600, true);
			//return new ArrayList<>();
		}else {
			Boolean flag = true;
			//如果cookie中有商品数据
			for (TbItem tbItem2 : list) {
				//查找cookie中是否有当前商品
				if(tbItem2.getId().longValue() == itemId.longValue()) {
					//如果有，那么更改购物车商品数量
					tbItem2.setNum(tbItem2.getNum()+num);
					flag = false;
					break;
				}
				
			}
			//如果cookie没有当前商品
			if(flag) {
				tbItem.setNum(num);
				list.add(tbItem);
			}
			String string = JSON.toJSONString(list);
			CookieUtils.setCookie(request, response, "cart", string, 3600, true);
		}
		//将购物车数据添加至cookie，有效时间半个月
		return "cartSuccess";
	}
	
	
	/**
	 * 跳转到购物车结算页面
	 */
	@RequestMapping("/cart/cart.html")
	public String toCart(HttpServletRequest request,Model model) {
		//从cookie中将商品取出来
 		String cartValue = CookieUtils.getCookieValue(request, "cart", true);
		List<TbItem> cartList = JSON.parseArray(cartValue, TbItem.class);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	/**
	 * 购物车页面修改商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result numChange(@PathVariable Long itemId,@PathVariable int num,
			HttpServletRequest request,HttpServletResponse response) {
		//从cookie中将数据取出来
		String cartValue = CookieUtils.getCookieValue(request, "cart", true);
		//转换为list
		List<TbItem> cartList = JSON.parseArray(cartValue, TbItem.class);
		//遍历itemId
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId.longValue()) {
				//更新商品数量
				tbItem.setNum(num);
				break;
			}
		}
		//更新cookie值
		String string = JSON.toJSONString(cartList);
		CookieUtils.setCookie(request, response, "cart", string, 3600, true);
		return E3Result.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String delSingleItem(@PathVariable Long itemId,
			HttpServletRequest request,HttpServletResponse response){
			//从cookie中将数据取出来
			String cartValue = CookieUtils.getCookieValue(request, "cart", true);
			//转换为list
			List<TbItem> cartList = JSON.parseArray(cartValue, TbItem.class);
			//遍历itemId
			for (TbItem tbItem : cartList) {
				if(tbItem.getId().longValue() == itemId.longValue()) {
					//更新商品数量
					cartList.remove(tbItem);
					break;
				}
			}
			//更新cookie值
			String string = JSON.toJSONString(cartList);
			CookieUtils.setCookie(request, response, "cart", string, 3600, true);
			//返回逻辑视图，返回购物车页面，使用redirect跳转
			return "redirect:/cart/cart.html";
			
	}
	
	
}
