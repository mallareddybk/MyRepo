package com.cybrilla.service;

import java.security.Principal;
import java.util.List;

import com.cybrilla.domain.CurrentAccount;
import com.cybrilla.domain.CurrentTransaction;
import com.cybrilla.domain.Recipient;
import com.cybrilla.domain.SavingsAccount;
import com.cybrilla.domain.SavingsTransaction;

public interface TransactionService {
	List<CurrentTransaction> findCurrentTransactionList(String username);

	List<SavingsTransaction> findSavingsTransactionList(String username);

	void saveCurrentDepositTransaction(CurrentTransaction currentTransaction);

	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

	void saveCurrentWithdrawTransaction(CurrentTransaction currentTransaction);

	void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

	void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CurrentAccount CurrentAccount,
			SavingsAccount savingsAccount) throws Exception;

	List<Recipient> findRecipientList(Principal principal);

	Recipient saveRecipient(Recipient recipient);

	Recipient findRecipientByName(String recipientName);

	void deleteRecipientByName(String recipientName);
	
	void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, CurrentAccount CurrentAccount, SavingsAccount savingsAccount);
	

}
