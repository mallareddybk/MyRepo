/**
 * 
 */
package com.cybrilla.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author user
 *
 */
@Entity
public class CurrentTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date date;
	private String description;
	private String type;
	private String status;
	private double amount;
	private BigDecimal availableBal;
	
	@ManyToOne
	@JoinColumn(name = "current_account_id")
	private CurrentAccount currentAccount;
	
	public CurrentTransaction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param date
	 * @param description
	 * @param type
	 * @param status
	 * @param amount
	 * @param availableBal
	 * @param currentAccount
	 */
	public CurrentTransaction(Date date, String description, String type, String status, double amount,
			BigDecimal availableBal, CurrentAccount currentAccount) {
		super();
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.availableBal = availableBal;
		this.currentAccount = currentAccount;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the availableBal
	 */
	public BigDecimal getAvailableBal() {
		return availableBal;
	}

	/**
	 * @param availableBal the availableBal to set
	 */
	public void setAvailableBal(BigDecimal availableBal) {
		this.availableBal = availableBal;
	}

	/**
	 * @return the currentAccount
	 */
	public CurrentAccount getCurrentAccount() {
		return currentAccount;
	}

	/**
	 * @param currentAccount the currentAccount to set
	 */
	public void setCurrentAccount(CurrentAccount currentAccount) {
		this.currentAccount = currentAccount;
	}
	
	 

}
