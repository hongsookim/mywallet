package com.heraldkim.wallet;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.heraldkim.wallet.utils.Environment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TokenBalanceTask {

	public class Token {
		public String contractAddress;
		public int decimals;
		public String name;

		public Token(String contractAddress) {
			this.contractAddress = contractAddress;
			this.decimals = 0;
		}

		public Token(String contractAddress, int decimals) {
			this.contractAddress = contractAddress;
			this.decimals = decimals;
		}
	}

	private static Web3j web3j;

	private static List<Token> tokenList;

	private static List<String> addressList;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		loadData();
		requestDecimals();
		requestName();
		processTask();
	}


	private static void loadData() {
		tokenList = new ArrayList<>();
		// TODO: add...
		addressList = new ArrayList<>();
		// TODO: add...
	}

	private static void requestDecimals() {
		for (Token token : tokenList) {
			token.decimals = TokenClient.getTokenDecimals(web3j, token.contractAddress);
		}
	}

	private static void requestName() {
		for (Token token : tokenList) {
			token.name = TokenClient.getTokenName(web3j, token.contractAddress);
		}
	}

	private static void processTask() {
		for (String address : addressList) {
			for (Token token : tokenList) {
				BigDecimal balance = new BigDecimal(TokenClient.getTokenBalance(web3j, address, token.contractAddress));
				balance.divide(BigDecimal.TEN.pow(token.decimals));
				System.out.println("address " + address + " name " + token.name + " balance " + balance);
			}
		}
	}
}
