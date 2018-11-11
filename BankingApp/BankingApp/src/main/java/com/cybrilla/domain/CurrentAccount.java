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
public class CurrentAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int accountNum;
	private BigDecimal accountBal;
	
	@OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CurrentTransaction> currentTransactionList;
	
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
	 * @return the currentTransactionList
	 */
	public List<CurrentTransaction> getCurrentTransactionList() {
		return currentTransactionList;
	}
	
	/**
	 * @param currentTransactionList the currentTransactionList to set
	 */
	public void setCurrentTransactionList(List<CurrentTransaction> currentTransactionList) {
		this.currentTransactionList = currentTransactionList;
	}
}
