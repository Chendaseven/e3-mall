package e3.activemqTest;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testQueueConsumer {
	
	@Test
	public void test() throws IOException {
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:Spring/applicationContext-activemq.xml");
		//等待
		System.in.read();
	}
	
}
