package cn.e3.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringactiveMQTest {
	
	
	@Test
	public void testQueueProducer() {
		//获得spring容器对象
		ApplicationContext application = new ClassPathXmlApplicationContext("classpath:Spring/applicationContext-activemq.xml");
		
		//从spring容器中获得JmsTemplate对象
		JmsTemplate jms = (JmsTemplate) application.getBean("jmsTemplate");
		//从Spring容器中获取Destination对象
		Destination destination = (Destination) application.getBean("queueDestination");
		//使用JmsTemplate发送消息
		jms.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建一个对象并返回
				TextMessage textMessage = session.createTextMessage("spring activemq queue message");
				
				return textMessage;
			}
			
		});
	}
	
}
