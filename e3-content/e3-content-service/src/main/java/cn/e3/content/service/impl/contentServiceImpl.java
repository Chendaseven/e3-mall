package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3.content.service.ContentService;
import cn.e3mall.EasyUi.EasyUiTreeNode;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import util.E3Result;
@Service
public class contentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUiTreeNode> selectAllContent(long parentId) {
		//设置查询条件，根据parentId来查找对应的分类树
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> contentCategoryList = tbContentCategoryMapper.selectByExample(categoryExample);
		//设置POJO-EasyUiTreeNode来保存所有的菜单结点，使用Json格式将数据传到前台
		List<EasyUiTreeNode> treeList = new ArrayList<>();
		for (TbContentCategory contentCategory : contentCategoryList) {
			EasyUiTreeNode easyUiTreeNode2 = new EasyUiTreeNode();
			easyUiTreeNode2.setId(contentCategory.getId());
			easyUiTreeNode2.setText(contentCategory.getName());
			easyUiTreeNode2.setState(contentCategory.getIsParent() ? "closed" : "open");
			treeList.add(easyUiTreeNode2);
		}
		
		return treeList;
	}

	@Override
	public E3Result addContentGategory(long parentId, String name) {
		// 1、接受两个参数：parentId、name
		// 2、向tb_content_gategory表中插入数据
		// a）创建一个tbContentGategory对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		// b)补全contentGateGory信息
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		tbContentCategory.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// c)向content_category表中插入数据
		tbContentCategoryMapper.insert(tbContentCategory);
		
		//3、判断父结点的isparent是否为true，不是true需要改为true
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父结点
			tbContentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		//4、需要主键返回
		//5、返回E3Result，其中包装TbContentCategory对象
		
		return E3Result.ok(tbContentCategory);
	}
	/**
	 * 删除内容管理节点
	 */
	@Override
	public E3Result deleteContent(long id) {
		TbContentCategory resultCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
/*		判断此节点是否是父节点，此时两种处理方案，
		1、如果是父节点那么需要先将子节点全部删除之后才能删除此节点
		2、递归删除全部子节点之后一起删除父节点 */		
		long parent_id = resultCategory.getParentId();
		if(resultCategory.getIsParent()) {
			
		}else {
			//如果此节点不是父节点那么可以删除此节点并且判断此节点的父节点是否还有子节点
			tbContentCategoryMapper.deleteByPrimaryKey(id);
			List<TbContentCategory> categoryList = tbContentCategoryMapper.selectByParentId(parent_id);
			//如果删除节点的父结点无其他子节点，那么将其父节点的is_parentId改为false
			if(categoryList.isEmpty()) {
				TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
				parentCategory.setIsParent(false);
				tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
			}
		}
		return E3Result.ok();
	}
	
	/**
	 * 对内容管理节点重命名
	 */
	@Override
	public void updateContent(long id, String name) {
		//根据id值获取内容管理节点
		TbContentCategory resultCategroy = tbContentCategoryMapper.selectByPrimaryKey(id);
		//重命名节点name
		resultCategroy.setName(name);
		//更新到数据库
		tbContentCategoryMapper.updateByPrimaryKey(resultCategroy);	
	}
	
}
