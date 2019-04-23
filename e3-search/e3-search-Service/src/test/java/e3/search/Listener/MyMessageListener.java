package e3.search.Listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
		TextMessage textMessage = (TextMessage) message;
		//取消息内容
		String text;
		try {
			text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
