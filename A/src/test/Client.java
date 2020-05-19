package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Client {
	private static String path ;
	private static int port;
	private static String ip;
	private static ExecutorService executorService ;
	private final static Logger logger = Logger.getLogger(Client.class.getName());
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		initProperties();
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		logger.debug("[클라이언트 디렉토리 감시중]");
		
		try {
			//디렉토리 감시.
			pollingDir();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		logger.debug("[클라이언트 종료]");
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
			logger.error(e+": properties 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			logger.error(e+": properties 파일을 읽는데 실패했습니다.");
		}
	}
	
	//지정 디렉토리 감시.
	public static void pollingDir() throws IOException, InterruptedException {
		initFileSend();
		WatchService myWatchService = FileSystems.getDefault().newWatchService();
		
		Path paths = Paths.get(path);
		WatchKey watchKey = paths.register(myWatchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
		String fileName = null;
		
		while(true) {
			Thread.sleep(3000);	//3초 간격으로 감시.
			System.out.println("디렉토리를 감시중입니다.");
			// 변화가 감지되는 경우 이벤트 종류와 파일명을 출력
			for (WatchEvent event : watchKey.pollEvents()) {
				//파일이 생성이 감지되면 파일전송하기.
				if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					fileName = event.context().toString();
					File f = new File(path + fileName);
					long fileSize = f.length();
					
					if(f.isFile()) {
						isFileChange(path+fileName);
						ClientSender cs = new ClientSender(port, ip, path, fileName, fileSize);
						executorService.submit(cs);
						
					}
				}
			}
			if (!watchKey.reset()) {
				break; // 디렉토리등이 삭제되는 경우
			}
		}
	}
	
	//파일이 변하는지 감지하는 메서드
	 public static void isFileChange(String filePath) throws InterruptedException {
	      File file=new File(filePath);
	      while(true) {
	         long time1=file.lastModified();
	         Thread.sleep(1000);
	         long time2=file.lastModified();
	         if(time1==time2) {
	            break;
	         }
	      }
	 }
	
	
	//클라이언트 처음 기동되었을 때 폴더내에 있는 파일 전송.
	public static void initFileSend() {
		File f = new File(path);
		File[] fileList = f.listFiles();
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
