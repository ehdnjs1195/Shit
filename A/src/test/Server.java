package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static String path;
	private static int port;
	private static ExecutorService executorService;
	
	
	public static void main(String[] args) {
		initProperties();
		ServerSocket serverSocket = null;
		Runnable r = null;
		
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("[서버 시작]");
			while(true) {
				Socket socket = serverSocket.accept();
				r = new ServerReceiver(socket, path);
				
				executorService.submit(r);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void initProperties() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("properties.txt"));
			path = p.getProperty("server_path");
			port = Integer.parseInt(p.getProperty("port"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
