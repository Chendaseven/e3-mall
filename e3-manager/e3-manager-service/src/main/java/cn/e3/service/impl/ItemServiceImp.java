package cn.e3.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.service.ItemService;
import cn.e3mall.EasyUi.EasyUiReslut;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import util.E3Result;
import util.IDUtils;
/**
 * 测试框架整合，根据id查询商品信息
 * @author Administrator
 *
 */
@Service
public class ItemServiceImp implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	@Override
	public TbItem findById(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public EasyUiReslut findAllItem(int page,int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//查询数据
		TbItemExample example = new TbItemExample();
		List<TbItem> itemList = (List<TbItem>) tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pageinfo = new PageInfo<>(itemList);
		//创建返回值对象
		EasyUiReslut easyUiResult = new EasyUiReslut(pageinfo.getTotal(), pageinfo.getList());
		return easyUiResult;
	}
	
	/**
	 * 新增商品的方法
	 */
	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		//生成随机的商品id,商品信息TbItem中只有商品id、时间信息、商品状态没有，只能手动添加，其他信息由前端from表单提供
		//作为activeMQ发布的消息必须为final
		final long ItemId = IDUtils.genItemId();
		tbItem.setId(ItemId);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte) 1);
		//向表中插入数据
		tbItemMapper.insert(tbItem);
		//创建商品描述类，将商品描述加入数据表中
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(ItemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		//向表中插入数据
		tbItemDescMapper.insert(tbItemDesc);
		//当商品新增完成之后发送一个商品添加消息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 将商品id作为消息发布出去
				TextMessage textMessage = session.createTextMessage(ItemId + "");
				return textMessage;
			}
		});
		
		
		return E3Result.ok();
	}
	
	//查找对应的商品描述
	@Override
	public TbItemDesc selectDesc(long id) {
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return tbItemDesc;
	}

	@Override
	public E3Result deleteItem(String ids) {
		String[] id = ids.split(",");
		for (String string : id) {
			long primarykey = Long.parseLong(string);
			tbItemMapper.deleteByPrimaryKey(primarykey);
		}
		return E3Result.ok();
	}

	@Override
	public E3Result updateItem(TbItem tbItem, String desc) {
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte) 1);
		tbItemMapper.updateByPrimaryKey(tbItem);
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDescMapper.updateByPrimaryKey(tbItemDesc);
		return E3Result.ok();
	}

}
