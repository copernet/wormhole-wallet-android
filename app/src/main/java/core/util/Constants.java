package core.util;

import com.blankj.utilcode.util.PathUtils;

public class Constants {
	public static boolean TEST_NET = true;
	public static final String HOST_API = "api.wormhole.cash";
	public static final String HOST_TESTNET = "dev.wormhole.cash";
	public static final String HEX_STRING = "0123456789abcdef";
	public static final String MAINNET_PREFIX = "bitcoincash";
	public static final String TESTNET_PREFIX = "bchtest";
	public static final String SEPARATOR_COLON = ":";
	public static final String PROTOCOL_VERSION_12 = "1.2";
	public static final int NUM_SERVERS = 10;
	public static final String STORAGE_PATH = PathUtils.getInternalAppDataPath() + "/whc_wallet1/s_";
	public static final String STORAGE_NO_SECURE_PATH = PathUtils.getInternalAppDataPath() + "/whc_wallet1/no_secure_wallet";
	public static final String BASE_58_CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	public static final String PACKAGE_VERSION = "3.1.5";     //version of the client package
	public static final String PROTOCOL_VERSION_11 = "1.1";     //protocol version requested

	public static final String STORAGE_WALLET_NAME = "wallet_name";
	public static final String STORAGE_WALLET_NAME_LIST = "wallet_name_list";
	public static final String STORAGE_FEE = "miner_fee";
	public static final String STORAGE_NOTICE_TIME = "storage_notice_time";
	public static final String STORAGE_ADDR_CHANGE_NEW = "change_new_style";
	public static final String STORAGE_ADDR_RECEIVE_NEW = "recevie_new_style";
	public static final String LANGUAGE = "language";

	public static String getHost() {
		return TEST_NET ? HOST_TESTNET : HOST_API;
	}

}
