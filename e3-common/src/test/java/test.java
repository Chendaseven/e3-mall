import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;



public class test {
		
	@Test
	public void test1() {
		List<student> list = new ArrayList<>();
		//JSON.toJSONString(list, true);
		String s = "123";
//		s.substring(beginIndex, endIndex)
//		Map<> map = new HashMap<>();
//		map.put(key, value)
//		map.containsKey(key)
		student s1 = new student("1","12");
		student s2 = new student("2","13");
		list.add(s1);
		list.add(s2);
		
				
	}
	
	public static boolean test2(String s) {
			
		 	int l = s.length();
	        int i = 0;
	        String temp;
	        Map<String,Integer> map = new HashMap<String,Integer>();
	        map.put("()",1);
	        map.put("{}",2);
	        map.put("[]",3);
	        if(l==1 || l % 2 != 0){
	            //当字符串长度为1或奇数，直接返回false
	            return false;
	        }else{
	            while(i<l){
	                temp = s.substring(i,i+2);
	                if(!map.containsKey(temp)){
	                    return false;
	                }
	                i+=2;
	            }
	        }
	        return true;
	}
	
	@Test
	public static void main(String[] args) {
		String s = "()";
		System.out.println(test2(s));
	}
}
