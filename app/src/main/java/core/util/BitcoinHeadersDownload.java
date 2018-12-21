package core.util;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import core.core.Network;

public class BitcoinHeadersDownload  extends Thread{
	private String saveLocation;
	private Network network;
	private HttpURLConnection httpsURLConnection;

	public BitcoinHeadersDownload(String saveLocation, Network network) {
		this.saveLocation = saveLocation;
		this.network = network;
	}
	public void run() {
		try {

			LogUtils.d("Header downloading start");

//			URL url = new URL("https://" + BitcoinMainnet.HEADERS_URL + BitcoinMainnet.HEADERS_PATH);
			URL url = new URL("http://dev.wormhole.cash/headers/file");
			httpsURLConnection = (HttpURLConnection) url.openConnection();
			httpsURLConnection.setConnectTimeout(15000);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(false);
			httpsURLConnection.connect();
			InputStream inputStream = httpsURLConnection.getInputStream();
//			HttpsConnectionImpl connection = new HttpsConnectionImpl(BitcoinMainnet.HEADERS_URL,443,BitcoinMainnet.HEADERS_PATH);
//			connection.setAllowUntrustedCertificates(true);
//			DataInputStream inputStream = connection.openDataInputStream();
			Files.download(inputStream, saveLocation);
			LogUtils.d("download complete");
			network.setDownloadingHeaders(false);
			httpsURLConnection.disconnect();
		}
		catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
	}
}
