package org.springframework.samples.petclinic.owner;

import java.util.Date;

public class BillDto {
	
	private Integer id;
	
	private long idNumber;
	
	private Date paymentDate;

	private double money;
	
	private Integer visit;
	
	
	
	public BillDto(Integer id, long idNumber, Date paymentDate, double money, Integer visit) {
		super();
		this.id = id;
		this.idNumber = idNumber;
		this.paymentDate = paymentDate;
		this.money = money;
		this.visit = visit;
	}


	public BillDto () { }
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public long getIdNumber() {
		return idNumber;
	}


	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}


	public Date getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}


	public double getMoney() {
		return money;
	}


	public void setMoney(double money) {
		this.money = money;
	}


	public Integer getVisit() {
		return visit;
	}


	public void setVisit(Integer visit) {
		this.visit = visit;
	}


	@Override
	public String toString() {
		return "BillDto [id=" + id + ", idNumber=" + idNumber + ", paymentDate=" + paymentDate + ", money=" + money
				+ ", visit=" + visit + "]";
	}

}
