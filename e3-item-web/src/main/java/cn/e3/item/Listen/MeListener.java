package cn.e3.item.Listen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cn.e3.item.controller.TbItemDetails;
import cn.e3.item.freemarker.freemarkerFile;
import cn.e3.search.service.SearchItemDetails;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
/**
 * 创建消息接收者，当接收到商品添加消息时，生成静态页面
 * @ClassName MeListener
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月13日 下午5:22:13
 */
public class MeListener implements MessageListener{
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Autowired
	private SearchItemDetails searchItemDetails;
	@Value("${StaticFIleAddress}")
	private String StaticFIleAddress;
	
	@Override
	public void onMessage(Message message){
		try {
			TextMessage textMessage = null;
			Long itemId = null;
			//取消息内容
			if(message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				//获取商品添加的商品id
				itemId = Long.parseLong(textMessage.getText());
			}
			
			//生成静态文件
//			2、从FreeMarkerConfigurer对象中获得Configuration对象。
			Configuration configuration = freeMarkerConfig.getConfiguration();
//			3、使用Configuration对象获得Template对象。
			Template template = configuration.getTemplate("item.ftl");
			//等待事务提交
			Thread.sleep(1000);
			//根据搜索商品
			TbItem tbItem = searchItemDetails.selectItemDetails(itemId);
			//将TbItem转化为TbItemDetails，因为前端展示页面没有images字段，所以需要重写TbItem
			TbItemDetails item = new TbItemDetails(tbItem);
			//根据id搜索商品描述信息
			TbItemDesc tbItemDesc = searchItemDetails.selectItemDesc(itemId);

//			4、创建数据集
			Map dataModel = new HashMap<>();
			dataModel.put("item", item);
			dataModel.put("itemDesc", tbItemDesc);
			
			String fileName = itemId + ".html";
			System.out.println(fileName);
//			5、创建输出文件的Writer对象。
			Writer out = new FileWriter(StaticFIleAddress+fileName);
//			6、调用模板对象的process方法，生成文件。
			try {
				template.process(dataModel, out);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			7、关闭流。
			out.close();
			
		} catch (JMSException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
