package zadanie2;

import java.util.Date;

public class Travel {

	private String country;
	private Date from;
	private Date to;
	private String place;
	private Double price;
	private String currency;
	
	public Travel(){ }
	
	public Travel(String country, 
				  Date from, 
				  Date to, 
				  String place, 
				  Double price, 
				  String currency) {
		
		this.country = country;
		this.from = from;
		this.to = to;
		this.place = place;
		this.price = price;
		this.currency = currency;
	}
	
	public String getCountry() {
		return country;
	}
	
	public Date getFrom() {
		return from;
	}
	
	public Date getTo() {
		return to;
	}
	
	public String getPlace() {
		return place;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
