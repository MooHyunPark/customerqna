package kr.co.greenart.web.customer.qna;

public class Order {
	private String column;
	private String order;
	
	public String getSortProvider() {
		return column + " " + order;
	}
}
