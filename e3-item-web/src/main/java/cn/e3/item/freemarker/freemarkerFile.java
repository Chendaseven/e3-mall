package cn.e3.item.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cn.e3.item.controller.TbItemDetails;
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
 * 生成静态文件
 * @ClassName freemarkerFile
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月13日 下午5:52:16
 */
public class freemarkerFile {
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Autowired
	private SearchItemDetails searchItemDetails;
	@Value("${StaticFIleAddress}")
	private String StaticFIleAddress;
	
	public void creatStaticFile(Long ItemId) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
//		2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = freeMarkerConfig.getConfiguration();
//		3、使用Configuration对象获得Template对象。
		Template template = configuration.getTemplate("item.ftl");
		
		//根据搜索商品
		TbItem tbItem = searchItemDetails.selectItemDetails(ItemId);
		//将TbItem转化为TbItemDetails，因为前端展示页面没有images字段，所以需要重写TbItem
		TbItemDetails item = new TbItemDetails(tbItem);
		//根据id搜索商品描述信息
		TbItemDesc tbItemDesc = searchItemDetails.selectItemDesc(ItemId);

//		4、创建数据集
		Map dataModel = new HashMap<>();
		dataModel.put("item", item);
		dataModel.put("itemDesc", tbItemDesc);
		
		String fileName = ItemId + "html";
//		5、创建输出文件的Writer对象。
		Writer out = new FileWriter(new File(StaticFIleAddress+fileName));
//		6、调用模板对象的process方法，生成文件。
		template.process(dataModel, out);
//		7、关闭流。
		out.close();
	}
}
