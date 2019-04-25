package cn.e3.item.controller;

import cn.e3mall.pojo.TbItem;

public class TbItemDetails extends TbItem{
	private String[] images;
	

	
/*	public String[] getImages() {
		String image2 = this.getImage();
		if(image2 != null && !image2.equals("")) {
			String[] images = image2.split(",");
			return images;
		}
		return null;
	}*/
	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		String image2 = this.getImage();
		if(image2 != null && !image2.equals("")) {
			images = image2.split(",");
		}
	}



	public TbItemDetails(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
		
	}
	
	
	
}
