package test;

import java.io.DataInputStream;
import java.net.Socket;

public class ServerReceiver implements Runnable{
	private Socket socket;
	private String path;
	
	public ServerReceiver() {}
	public ServerReceiver(Socket socket, String path) {
		this.socket = socket;
		this.path = path;
	}
	
	@Override
	public void run() {
		
	}
	
	public void receiveFile() {
		DataInputStream dis = null;
		
	}
	
	
}
