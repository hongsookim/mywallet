package com.heraldkim.wallet;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

import com.heraldkim.wallet.utils.Environment;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class AccountManager {
	private static Admin admin;

	public static void main(String[] args) {
		admin = Admin.build(new HttpService(Environment.RPC_URL));
		String password="123456789";
		String address = createNewAccount(password);
		getAccountList();
		unlockAccount(address, password);
		
		Transaction transaction = createNewTransaction(address);
		admin.personalSendTransaction(transaction, password); 
	}

	private static String createNewAccount(String password) {
		String address = "";
		try {
			NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
			address = newAccountIdentifier.getAccountId();
			System.out.println("new account address " + address);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return address;
	}

	private static void getAccountList() {
		try {
			PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
			List<String> addressList;
			addressList = personalListAccounts.getAccountIds();
			System.out.println("account size " + addressList.size());
			for (String address : addressList) {
				System.out.println("----"+address);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void unlockAccount(String address, String password) {

		BigInteger unlockDuration = BigInteger.valueOf(60L);
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
			Boolean isUnlocked = personalUnlockAccount.accountUnlocked();
			System.out.println("account unlock " + isUnlocked);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Transaction createNewTransaction(String to) {
		//TODO:: create transaction
	    String from = "0xfa4f1e2c171fb088e9a3785b6854af763b9a3c6c";
//	    String to = "0xd13ef4a175c5ed6430254f887ae39a5f4d639732";
	    BigInteger gas = BigInteger.valueOf(90000);
	    BigInteger gasPrice = BigInteger.valueOf(1000000000);
	    BigInteger value = BigInteger.valueOf(1);
	    BigInteger nonce = BigInteger.valueOf(1);
	    
	    Transaction transaction = Transaction.createEtherTransaction(from, nonce, gasPrice, gas, to, value);
	    
	    System.out.println("Sent "+value+ " ether from " +from+" to "+to);
	    
	    return transaction;
	}

}
