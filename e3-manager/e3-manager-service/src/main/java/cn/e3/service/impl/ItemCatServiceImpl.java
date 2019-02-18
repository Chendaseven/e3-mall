package cn.e3.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * 创建商品分类菜单是服务层
 */
import org.springframework.stereotype.Service;

import cn.e3.service.ItemCatService;
import cn.e3mall.EasyUi.EasyUiTreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemMapper;
		
	@Override
	public List<EasyUiTreeNode> selectItemCat(long parentId) {
		//设置查询条件，根据parentId查找对应的TbItemCat，从而查得其对应的TreeNode
		TbItemCatExample catExample = new TbItemCatExample();
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemMapper.selectByExample(catExample);
		//创建一个List来保存所有查询得到的TreeNode值，使用异步加载
		//设置POJO-EasyUiTreeNode来保存所有的菜单结点，使用Json格式将数据传到前台
		List<EasyUiTreeNode> treeList = new ArrayList<>();
		for(TbItemCat itemCat:list) {
			EasyUiTreeNode treeNode = new EasyUiTreeNode();
			treeNode.setId(itemCat.getId());
			treeNode.setText(itemCat.getName());
			treeNode.setState(itemCat.getIsParent() ? "closed":"open");
			treeList.add(treeNode);
		}
		return treeList;
	}

}
