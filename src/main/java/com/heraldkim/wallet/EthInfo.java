package com.heraldkim.wallet;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;

import com.heraldkim.wallet.utils.Environment;

import java.io.IOException;
import java.math.BigInteger;

public class EthInfo {
	private static Web3j web3j;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		getEthInfo();
	}

	/**
	 * get ether information
	 */
	private static void getEthInfo() {

		Web3ClientVersion web3ClientVersion = null;
		try {
			web3ClientVersion = web3j.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);

			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
			BigInteger blockNumber = ethBlockNumber.getBlockNumber();
			System.out.println(blockNumber);

			EthCoinbase ethCoinbase = web3j.ethCoinbase().send();
			String coinbaseAddress = ethCoinbase.getAddress();
			System.out.println(coinbaseAddress);

			EthSyncing ethSyncing = web3j.ethSyncing().send();
			boolean isSyncing = ethSyncing.isSyncing();
			System.out.println(isSyncing);

			EthMining ethMining = web3j.ethMining().send();
			boolean isMining = ethMining.isMining();
			System.out.println(isMining);

			EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
			BigInteger gasPrice = ethGasPrice.getGasPrice();
			System.out.println(gasPrice);

			EthHashrate ethHashrate = web3j.ethHashrate().send();
			BigInteger hashRate = ethHashrate.getHashrate();
			System.out.println(hashRate);

			EthProtocolVersion ethProtocolVersion = web3j.ethProtocolVersion().send();
			String protocolVersion = ethProtocolVersion.getProtocolVersion();
			System.out.println(protocolVersion);

			NetPeerCount netPeerCount = web3j.netPeerCount().send();
			BigInteger peerCount = netPeerCount.getQuantity();
			System.out.println(peerCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
