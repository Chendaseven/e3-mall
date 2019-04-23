package cn.e3.activemq;
/**
 * activemq测试
 * @ClassName activemqTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月13日 下午9:10:32
 */



import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;



public class activemqTest {
	/**
	 * 创建一个queue测试对象
	 * @throws Exception
	 */
	@Test
	public void queueProducertest() throws Exception{
//		1、创建ConnectionFactory对象，需要指定服务端ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//		2、使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();		
//		3、开启链接，调用Connection对象的start方法
		connection.start();
//		4、使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		//第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		5、使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Queue queue = session.createQueue("test-queue");
//		6、使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(queue);
//		7、创建一个Message对象，使用session创建一个TextMessage对象。
		TextMessage textMessage = session.createTextMessage("this is a queue test");
//		8、使用Producer对象发送消息。
		producer.send(textMessage);
//		9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 创建一个queue接收者
	 * @throws Exception
	 */
	@Test
	public void queueConsumertest() throws Exception{
//		1、创建ConnectionFactory对象，需要指定服务端ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//		2、使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();		
//		3、开启链接，调用Connection对象的start方法
		connection.start();
//		4、使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		//第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		5、使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Queue queue = session.createQueue("test-queue");
//		6、使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(queue);
//		7、接受消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text = null;
				//取消息内容
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//等待键盘输入
		System.in.read();
//		9、关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	
	/**
	 * 创建一个topic producer测试对象
	 * @throws Exception
	 */
	@Test
	public void topicProducertest() throws Exception{
//		1、创建ConnectionFactory对象，需要指定服务端ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//		2、使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();		
//		3、开启链接，调用Connection对象的start方法
		connection.start();
//		4、使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		//第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		5、使用Session对象创建一个Destination对象（topic、queue），此处创建一个Topic对象。
		Topic topic = session.createTopic("topic-test");
//		6、使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(topic);
//		7、创建一个Message对象，使用session创建一个TextMessage对象。
		TextMessage textMessage = session.createTextMessage("this is a topic test");
//		8、使用Producer对象发送消息。
		producer.send(textMessage);
//		9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 创建一个topic consumer测试
	 * @throws Exception
	 */
	@Test
	public void topicConsumertest() throws Exception{
//		1、创建ConnectionFactory对象，需要指定服务端ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//		2、使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();		
//		3、开启链接，调用Connection对象的start方法
		connection.start();
//		4、使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		//第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		5、使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Topic topic = session.createTopic("topic-test");
//		6、使用Session对象创建一个Consumer对象。
		MessageConsumer consumer = session.createConsumer(topic);
//		7、接受消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text = null;
				//取消息内容
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		System.out.println("topic 消费段03");
		//等待键盘输入
		System.in.read();
//		9、关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	


}
