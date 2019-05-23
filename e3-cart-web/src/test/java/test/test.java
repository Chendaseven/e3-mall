package test;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class test {
		
	@Test
	public void test1() {
		List list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		String jsonString = JSON.toJSONString(list, false);
		System.out.println(list.toString());
		System.out.println(jsonString);
	}
	
	@Test
	public void test2() {
		List<student> list = new ArrayList<>();
		student s1 = new student("1","12");
		student s2 = new student("2","13");
		String s3 = "{\"name\":\"1\",\"age\":\"12\"}";
		list.add(s1);
		list.add(s2);
		String jsonString = JSON.toJSONString(list);
		String jsonString1 = JSON.toJSONString(s1);
		System.out.println(jsonString);
		System.out.println(jsonString1);
		System.out.println(JsonUtils.objectToJson(s1));
		//student s = (student)JSON.parseObject(jsonString1, student.class);
		student s = JSON.parseObject(s3, student.class);
		System.out.println(s);
	}
}
