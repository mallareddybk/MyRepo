package com.cybrilla.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybrilla.dao.CurrentAccountDao;
import com.cybrilla.dao.SavingsAccountDao;
import com.cybrilla.domain.CurrentAccount;
import com.cybrilla.domain.CurrentTransaction;
import com.cybrilla.domain.SavingsAccount;
import com.cybrilla.domain.SavingsTransaction;
import com.cybrilla.domain.User;
import com.cybrilla.service.AccountService;
import com.cybrilla.service.TransactionService;
import com.cybrilla.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {

	private static int nextAccountNumber = 11223145;

	@Autowired
	private CurrentAccountDao currentAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;

	public CurrentAccount createCurrentAccount() {
		CurrentAccount currentAccount = new CurrentAccount();
		currentAccount.setAccountBal(new BigDecimal(0.0));
		currentAccount.setAccountNum(accountGen());

		currentAccountDao.save(currentAccount);

		return currentAccountDao.findByAccountNum(currentAccount.getAccountNum());
	}

	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBal(new BigDecimal(0.0));
		savingsAccount.setAccountNum(accountGen());

		savingsAccountDao.save(savingsAccount);

		return savingsAccountDao.findByAccountNum(savingsAccount.getAccountNum());
	}
	
	public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("current")) {
            CurrentAccount currentAccount = user.getCurrentAccount();
            currentAccount.setAccountBal(currentAccount.getAccountBal().add(new BigDecimal(amount)));
            currentAccountDao.save(currentAccount);

            Date date = new Date();

            CurrentTransaction CurrentTransaction = new CurrentTransaction(date, "Deposit to Current Account", "Account", "Finished", amount, currentAccount.getAccountBal(), currentAccount);
            transactionService.saveCurrentDepositTransaction(CurrentTransaction);
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBal(savingsAccount.getAccountBal().add(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBal(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Current")) {
            CurrentAccount CurrentAccount = user.getCurrentAccount();
            CurrentAccount.setAccountBal(CurrentAccount.getAccountBal().subtract(new BigDecimal(amount)));
            currentAccountDao.save(CurrentAccount);

            Date date = new Date();

            CurrentTransaction CurrentTransaction = new CurrentTransaction(date, "Withdraw from Current Account", "Account", "Finished", amount, CurrentAccount.getAccountBal(), CurrentAccount);
            transactionService.saveCurrentWithdrawTransaction(CurrentTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBal(savingsAccount.getAccountBal().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBal(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }

	private int accountGen() {
		return ++nextAccountNumber;
	}

}
