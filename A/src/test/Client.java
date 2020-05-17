package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static File[] fileList;
	private static String path ;
	private static int port;
	private static String ip;
	private static ExecutorService executorService ;
	
	public static void main(String[] args) {
		initProperties();
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		System.out.println("[클라이언트 디렉토리 감시중]");
		pollingDir();
		
		
		
		System.out.println("[클라이언트 종료]");
		executorService.shutdown();
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
	
	//지정 디렉토리 감시.
	public static void pollingDir() {
		while(true) {
			try {
				System.out.println("감시중?");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			createFileInfo();
			for(File file:fileList) {
				if(file.isFile()) {
					String fileName = file.getName();
					long fileSize = file.length();
					ClientSender cs = new ClientSender(port, ip, path, fileName, fileSize);
					//작업큐에 담기
					executorService.submit(cs);
				}
			}
		}
	}
	
	//파일 정보 정보 생성
	public static void createFileInfo() {
		File f = new File(path);
		fileList = f.listFiles();
	}
}
