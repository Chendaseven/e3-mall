package cn.e3.search.Dao;

import java.util.List;

import cn.e3.search.pojo.SearchPojo;

public interface SearchMapper {
	List<SearchPojo> searchList(); 
	//根据商品id查询商品
	SearchPojo getItemById(long itemId);
}
