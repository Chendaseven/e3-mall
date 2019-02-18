package cn.e3mall.EasyUi;

import java.io.Serializable;
import java.util.List;

public class EasyUiReslut implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long total;
	private List rows;
	
	public EasyUiReslut(Long total,List rows) {
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
