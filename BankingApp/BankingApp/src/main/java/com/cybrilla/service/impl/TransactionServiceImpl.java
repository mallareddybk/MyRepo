package com.cybrilla.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybrilla.dao.CurrentAccountDao;
import com.cybrilla.dao.CurrentTransactionDao;
import com.cybrilla.dao.RecipientDao;
import com.cybrilla.dao.SavingsAccountDao;
import com.cybrilla.dao.SavingsTransactionDao;
import com.cybrilla.domain.CurrentAccount;
import com.cybrilla.domain.CurrentTransaction;
import com.cybrilla.domain.Recipient;
import com.cybrilla.domain.SavingsAccount;
import com.cybrilla.domain.SavingsTransaction;
import com.cybrilla.domain.User;
import com.cybrilla.service.TransactionService;
import com.cybrilla.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private CurrentTransactionDao CurrentTransactionDao;

	@Autowired
	private SavingsTransactionDao savingsTransactionDao;

	@Autowired
	private CurrentAccountDao CurrentAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private RecipientDao recipientDao;

	public List<CurrentTransaction> findCurrentTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<CurrentTransaction> CurrentTransactionList = user.getCurrentAccount().getCurrentTransactionList();

		return CurrentTransactionList;
	}

	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();

		return savingsTransactionList;
	}

	public void saveCurrentDepositTransaction(CurrentTransaction CurrentTransaction) {
		CurrentTransactionDao.save(CurrentTransaction);
	}

	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	public void saveCurrentWithdrawTransaction(CurrentTransaction CurrentTransaction) {
		CurrentTransactionDao.save(CurrentTransaction);
	}

	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}
	
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CurrentAccount CurrentAccount, SavingsAccount savingsAccount) throws Exception {
        if (transferFrom.equalsIgnoreCase("Current") && transferTo.equalsIgnoreCase("Savings")) {
            CurrentAccount.setAccountBal(CurrentAccount.getAccountBal().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBal(savingsAccount.getAccountBal().add(new BigDecimal(amount)));
            CurrentAccountDao.save(CurrentAccount);
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            CurrentTransaction CurrentTransaction = new CurrentTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), CurrentAccount.getAccountBal(), CurrentAccount);
            CurrentTransactionDao.save(CurrentTransaction);
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Current")) {
            CurrentAccount.setAccountBal(CurrentAccount.getAccountBal().add(new BigDecimal(amount)));
            savingsAccount.setAccountBal(savingsAccount.getAccountBal().subtract(new BigDecimal(amount)));
            CurrentAccountDao.save(CurrentAccount);
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBal(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }

	public List<Recipient> findRecipientList(Principal principal) {
        String username = principal.getName();
        List<Recipient> recipientList = recipientDao.findAll().stream() 			//convert list to stream
                .filter(recipient -> username.equals(recipient.getUser().getUserName()))	//filters the line, equals to username
                .collect(Collectors.toList());

        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientDao.save(recipient);
    }

    public Recipient findRecipientByName(String recipientName) {
        return recipientDao.findByName(recipientName);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientDao.deleteByName(recipientName);
    }
    
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, CurrentAccount CurrentAccount, SavingsAccount savingsAccount) {
        if (accountType.equalsIgnoreCase("current")) {
            CurrentAccount.setAccountBal(CurrentAccount.getAccountBal().subtract(new BigDecimal(amount)));
            CurrentAccountDao.save(CurrentAccount);

            Date date = new Date();

            CurrentTransaction CurrentTransaction = new CurrentTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), CurrentAccount.getAccountBal(), CurrentAccount);
            CurrentTransactionDao.save(CurrentTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            savingsAccount.setAccountBal(savingsAccount.getAccountBal().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBal(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        }
    }
}
