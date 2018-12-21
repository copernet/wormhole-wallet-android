package core.core;

import core.java.util.Queue;
import core.util.Server;

public class Connection{	
	
	public Connection(Server server, Queue queue) throws Exception {
		TcpConnection connection = new TcpConnection(server);
		queue.insert(new ServerSocketTuple(server,connection));
	}
	
}
