package com.operation.vo;

public class ItemBankVO {
	//table:item_bank  ���
	private String item_bank_id;       //�������
	private String item_bank_code;     //������
	private String item_bank_name;     //�������
	private String item_bank_description;     //���˵��
	private String catalog_level;     //���Ŀ¼����
	private String item_bank_type;     //����������
	private String item_bank_percent;     //�����ռ�ٷֱ�
	private String item_bank_status;     //���״̬
	
	private String colmuns="item_bank_id,item_bank_code,item_bank_name,item_bank_description,catalog_level,item_bank_type,item_bank_percent,item_bank_status";
	private String updateColmuns="item_bank_name,item_bank_description,item_bank_type,item_bank_percent";
	public String getItem_bank_id() {
		return item_bank_id;
	}
	public void setItem_bank_id(String item_bank_id) {
		this.item_bank_id = item_bank_id;
	}
	public String getItem_bank_code() {
		return item_bank_code;
	}
	public void setItem_bank_code(String item_bank_code) {
		this.item_bank_code = item_bank_code;
	}
	public String getItem_bank_name() {
		return item_bank_name;
	}
	public void setItem_bank_name(String item_bank_name) {
		this.item_bank_name = item_bank_name;
	}
	public String getItem_bank_description() {
		return item_bank_description;
	}
	public void setItem_bank_description(String item_bank_description) {
		this.item_bank_description = item_bank_description;
	}
	public String getCatalog_level() {
		return catalog_level;
	}
	public void setCatalog_level(String catalog_level) {
		this.catalog_level = catalog_level;
	}
	public String getItem_bank_status() {
		return item_bank_status;
	}
	public void setItem_bank_status(String item_bank_status) {
		this.item_bank_status = item_bank_status;
	}
	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}
	public String getUpdateColmuns() {
		return updateColmuns;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
	}
	public String getItem_bank_type() {
		return item_bank_type;
	}
	public void setItem_bank_type(String item_bank_type) {
		this.item_bank_type = item_bank_type;
	}
	public String getItem_bank_percent() {
		return item_bank_percent;
	}
	public void setItem_bank_percent(String item_bank_percent) {
		this.item_bank_percent = item_bank_percent;
	}
	
	

}
