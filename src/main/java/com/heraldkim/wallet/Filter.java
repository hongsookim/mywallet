package com.heraldkim.wallet;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.http.HttpService;

import com.heraldkim.wallet.utils.Environment;

import rx.Subscription;

import java.math.BigInteger;

public class Filter {
	private static Web3j web3j;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));

		newBlockFilter(web3j);

		newTransactionFilter(web3j);

		replayFilter(web3j);

		catchUpFilter(web3j);

		//subscription.unsubscribe();
	}

	private static void newBlockFilter(Web3j web3j) {
		Subscription subscription = web3j.
				blockObservable(false).
				subscribe(block -> {
					System.out.println("new block come in");
					System.out.println("block number" + block.getBlock().getNumber());
				});
	}

	private static void newTransactionFilter(Web3j web3j) {
		Subscription subscription = web3j.
				transactionObservable().
				subscribe(transaction -> {
					System.out.println("transaction come in");
					System.out.println("transaction txHash " + transaction.getHash());
				});
	}

	private static void replayFilter(Web3j web3j) {
		BigInteger startBlock = BigInteger.valueOf(2000000);
		BigInteger endBlock = BigInteger.valueOf(2010000);

		Subscription subscription = web3j.
				replayBlocksObservable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock),
						false).
				subscribe(ethBlock -> {
					System.out.println("replay block");
					System.out.println(ethBlock.getBlock().getNumber());
				});

		Subscription subscription1 = web3j.
				replayTransactionsObservable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock)).
				subscribe(transaction -> {
					System.out.println("replay transaction");
					System.out.println("txHash " + transaction.getHash());
				});
	}

	private static void catchUpFilter(Web3j web3j) {
		BigInteger startBlock = BigInteger.valueOf(2000000);

		Subscription subscription = web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
				DefaultBlockParameter.valueOf(startBlock), false)
				.subscribe(block -> {
					System.out.println("block");
					System.out.println(block.getBlock().getNumber());
				});

		Subscription subscription2 = web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(
				DefaultBlockParameter.valueOf(startBlock))
				.subscribe(tx -> {
					System.out.println("transaction");
					System.out.println(tx.getHash());
				});
	}
}
