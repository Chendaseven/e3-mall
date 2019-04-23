package cn.e3.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SearchPojo> itemList;
	private int totalPages;
	private int recordCount;
	public List<SearchPojo> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchPojo> itemList) {
		this.itemList = itemList;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
}
