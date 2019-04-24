package e3.search.Listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3.SearchResult.Service.Impl.SearchResultServiceImpl;
import cn.e3.search.service.impl.SearchServiceImpl;

public class MyMessageListener implements MessageListener{
	
	@Autowired
	private SearchServiceImpl searchServiceImpl;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null;
			//取消息内容
			if(message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			//向索引库添加文档
			searchServiceImpl.inputSingleDate(itemId);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
