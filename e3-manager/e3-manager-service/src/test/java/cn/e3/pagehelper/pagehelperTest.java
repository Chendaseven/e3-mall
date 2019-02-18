package cn.e3.pagehelper;

import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class pagehelperTest {
	
	public void Test() {
		//创建Spring容器
		ApplicationContext applicationcontext = new ClassPathXmlApplicationContext("classpath:Spring/applicationContext-dao.xml");
		//创建mapper对象
		TbItemMapper itemmapper = applicationcontext.getBean(TbItemMapper.class);
		//设置pageHelper
		PageHelper.startPage(1, 10,true);
		//查询所有的item
		TbItemExample example = new TbItemExample();
		List<TbItem> itemlist = itemmapper.selectByExample(example);
		//List<TbItem> itemlist = itemmapper.selectAllItem();
		//取分页信息
		PageInfo<TbItem> pageinfo = new PageInfo<>(itemlist);
		System.out.println(pageinfo.getTotal());
		System.out.println(pageinfo.getPageNum());
		System.out.println(pageinfo.getPages());
		System.out.println(itemlist.size());
	}
}
