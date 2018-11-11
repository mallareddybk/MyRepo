/**
 * 
 */
package com.cybrilla.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author user
 *
 */
@Entity
public class SavingsAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int accountNum;
	private BigDecimal accountBal;
	
	@OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SavingsTransaction> savingsTransactionList;
	
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
	 * @return the accountNum
	 */
	public int getAccountNum() {
		return accountNum;
	}
	
	/**
	 * @param accountNum the accountNum to set
	 */
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	
	/**
	 * @return the accountBal
	 */
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	
	/**
	 * @param accountBal the accountBal to set
	 */
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	
	/**
	 * @return the savingsTransactionList
	 */
	public List<SavingsTransaction> getSavingsTransactionList() {
		return savingsTransactionList;
	}
	
	/**
	 * @param savingsTransactionList the savingsTransactionList to set
	 */
	public void setSavingsTransactionList(List<SavingsTransaction> savingsTransactionList) {
		this.savingsTransactionList = savingsTransactionList;
	}
}
