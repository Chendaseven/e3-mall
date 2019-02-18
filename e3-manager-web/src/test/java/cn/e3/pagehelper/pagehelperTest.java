package cn.e3.pagehelper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 测试pageHelper
 * @author Administrator
 *
 */
public class pagehelperTest {
	public void Test() {
		//初始化Spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring/applicationContext-dao.xml");
		//从容器中获得mapper代理对象
		//TbItemMapper itemmapper = context.getBean(TbItemMapper.class);
	}
}
