package com.heraldkim.wallet;

import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.heraldkim.wallet.utils.Environment;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;

public class Security {
	private static Web3j web3j;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));

		exportPrivateKey("/Users/yangzhengwei/Library/Ethereum/testnet/keystore/UTC--2018-03-03T03-51-50.155565446Z--7b1cc408fcb2de1d510c1bf46a329e9027db4112",
				"yzw");

		importPrivateKey(new BigInteger("", 16),
				"yzw",
				WalletUtils.getTestnetKeyDirectory());

		exportBip39Wallet(WalletUtils.getTestnetKeyDirectory(),
				"yzw");
	}

	private static void exportPrivateKey(String keystorePath, String password) {
		try {
			Credentials credentials = WalletUtils.loadCredentials(
					password,
					keystorePath);
			BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
			System.out.println(privateKey.toString(16));
		} catch (IOException | CipherException e) {
			e.printStackTrace();
		}
	}

	private static void importPrivateKey(BigInteger privateKey, String password, String directory) {
		ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
		try {
			String keystoreName = WalletUtils.generateWalletFile(password,
					ecKeyPair,
					new File(directory),
					true);
			System.out.println("keystore name " + keystoreName);
		} catch (CipherException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void exportBip39Wallet(String keystorePath, String password) {
		try {
			Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet(password, new File(keystorePath));
			System.out.println(bip39Wallet);
		} catch (CipherException | IOException e) {
			e.printStackTrace();
		}
	}

}
