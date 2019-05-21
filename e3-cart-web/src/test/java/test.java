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
}
