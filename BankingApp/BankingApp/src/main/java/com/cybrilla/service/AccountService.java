/**
 * 
 */
package com.cybrilla.service;

import java.security.Principal;

import com.cybrilla.domain.CurrentAccount;
import com.cybrilla.domain.SavingsAccount;

/**
 * @author user
 *
 */
public interface AccountService {

	CurrentAccount createCurrentAccount();
	SavingsAccount createSavingsAccount();
	
	void deposit(String accountType, double amount, Principal principal);
	void withdraw(String accountType, double amount, Principal principal);
}