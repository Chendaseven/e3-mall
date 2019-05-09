package cn.e3.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
public class freemarkerTest {
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@RequestMapping("/genhtml")
	@ResponseBody
	public String test() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
//		1、从spring容器中获得FreeMarkerConfigurer对象。
//		2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = freeMarkerConfig.getConfiguration();
//		3、使用Configuration对象获得Template对象。
		Template template = configuration.getTemplate("hello.ftl");
//		4、创建数据集
		Map dataModel = new HashMap<>();
//		5、创建输出文件的Writer对象。
		Writer out = new FileWriter(new File("H:/temp/file1.html"));
//		6、调用模板对象的process方法，生成文件。
		template.process(dataModel, out);
//		7、关闭流。
		out.close();
		return "ok";
	}
}
