package core.core;

import com.blankj.utilcode.util.LogUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.CertificateRequest;
import org.bouncycastle.crypto.tls.DefaultTlsClient;
import org.bouncycastle.crypto.tls.TlsAuthentication;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.crypto.tls.TlsCredentials;

import core.util.Server;

public class TcpConnection {

	
	private Socket connection1 = new Socket();

	private Server server;
	
	public TcpConnection(Server server) throws IOException {
		this.server = server;
		if (server.getProtocol().equals("s")) {
			LogUtils.d(server.getHost());
			LogUtils.d("connected");
			
		} else {
			InetSocketAddress address = new InetSocketAddress(server.getHost(), server.getPort());
			connection1.connect(address);

	/*		outputStream = connection1.openDataOutputStream();
			inputStream = connection1.openDataInputStream();
	*/	}
		
	}

	public void reconnect() throws IOException{
		if (null != connection1) {
			try {
				connection1.close();
		 	} catch (IOException e) {

			}
		}
		InetSocketAddress address = new InetSocketAddress(server.getHost(), server.getPort());
		connection1 = new Socket();
		connection1.connect(address);
	}
	
	public static String getSSLResponse(InputStream in, OutputStream out,
			String request) throws IOException {
		String retval = null;
		TlsClientProtocol tlsClientProtocol = new TlsClientProtocol(in, out);
		DefaultTlsClient tlsClient = new DefaultTlsClient() {

			public TlsAuthentication getAuthentication() throws IOException {
				return new TlsAuthentication() {

					public void notifyServerCertificate(
							Certificate serverCertificate) throws IOException {
					}

					public TlsCredentials getClientCredentials(
							CertificateRequest certificateRequest)
							throws IOException {
						return null;
					}
				};
			}
		};

		tlsClientProtocol.connect(tlsClient);
		retval = getResponse(tlsClientProtocol.getInputStream(),
				tlsClientProtocol.getOutputStream(), request);
		tlsClientProtocol.close();
		return retval;
	}
	
	public static String getResponse(InputStream in, OutputStream out, String request)
			throws IOException {

		StringBuffer retval = new StringBuffer();
		byte[] content = new byte[100];
		out.write(request.getBytes());
		out.flush();

		int read = 0;
		while ((read = in.read(content)) != -1) {
			retval.append(new String(content, 0, read));
		}

		return retval.toString();
	}

	
	public Server getServer() {
		return server;
	}
	public DataOutputStream getOutputStream() throws IOException {
		return new DataOutputStream(connection1.getOutputStream());
//		return connection.openDataOutputStream();
	}
	public DataInputStream getInputStream() throws IOException {
		return new DataInputStream(connection1.getInputStream());
//		return connection.openDataInputStream();
	}
	public void close() {
		try {
			if(connection1 != null){
				connection1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
