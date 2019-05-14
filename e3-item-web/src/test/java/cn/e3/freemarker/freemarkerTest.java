package cn.e3.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 测试freemarker用法
 * @ClassName freemarkerTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年5月8日 上午10:38:30
 */
public class freemarkerTest {
	
	@Test
	public void test() throws IOException, TemplateException {
		//使用步骤：
		//第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("G:/Java/e3-Mall/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
		//第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("hello.ftl");
		//第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map dataModel = new HashMap<>();
		//向数据中添加数据
		dataModel.put("hello", "freemarkerTest");
		//第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		//输出静态文件，由ftl文件来定义生成的数据样式
		Writer out = new FileWriter("H:/temp/hello.html");
		//第七步：调用模板对象的process方法输出文件。
		template.process(dataModel, out);
		//第八步：关闭流。
		out.close();
	}
	
	/**
	 * 测试freemarker模板的语法
	 * @throws IOException
	 * @throws TemplateException
	 */
	@Test
	public void test1() throws IOException, TemplateException {
		//使用步骤：
		//第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		//第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("G:/Java/e3-Mall/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("UTF-8");
		//第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("file.ftl");
		//第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map dataModel = new HashMap<>();
		
		//1、向数据中添加pojo数据
		Student student = new Student("24","kobe",30);
		dataModel.put("stu", student);
		//2、添加集合的数据
		List<Student> studentList = new ArrayList<>();
		studentList.add(new Student("25","kobe1",31));
		studentList.add(new Student("26","kobe2",32));
		studentList.add(new Student("27","kobe3",33));
		studentList.add(new Student("28","kobe4",34));
		studentList.add(new Student("29","kobe5",35));
		dataModel.put("studentList", studentList);
		
		//添加日期类型
		dataModel.put("date", new Date());
		
		//null值的测试
		dataModel.put("myval", null);
		
		//include测试
		dataModel.put("hello", "freemarkerTest");
		
		//第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		//输出静态文件，由ftl文件来定义生成的数据样式
		Writer out = new FileWriter("H:/temp/file.html");
//		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("H:/temp/file.html"), "UTF-8");
//		PrintWriter out = new PrintWriter(writer);
		//第七步：调用模板对象的process方法输出文件。
		template.process(dataModel, out);
		//第八步：关闭流。
		out.close();
	}
	
}
