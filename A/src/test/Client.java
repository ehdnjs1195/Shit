package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static String path ;
	private static int port;
	private static String ip;
	private static ExecutorService executorService ;
	
	public static void main(String[] args) {
		initProperties();
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		ClientSender cs = new ClientSender(port, ip, path);
		
		executorService.submit(cs);
	}
	
	//properties 설정정보 얻어오기
	public static void initProperties() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("properties.txt"));
			path = p.getProperty("client_path");
			port = Integer.parseInt(p.getProperty("port"));
			ip = p.getProperty("ip");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
