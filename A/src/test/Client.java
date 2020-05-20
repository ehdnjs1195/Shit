package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Client {
	private static File[] fileList;
	private static String path;
	private static int port;
	private static String ip;
	private static ExecutorService executorService;
	private final static Logger logger = Logger.getLogger(Client.class.getName());

	public static void main(String[] args) {
		BasicConfigurator.configure();
		initProperties();
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		logger.debug("[클라이언트 디렉토리 감시중]");

		// 디렉토리 감시.
		pollingDir();
		

		logger.debug("[클라이언트 종료]");
		executorService.shutdown();
	}

	// properties 설정정보 얻어오기
	public static void initProperties() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("properties.txt"));
			path = p.getProperty("client_path");
			port = Integer.parseInt(p.getProperty("port"));
			ip = p.getProperty("ip");
		} catch (FileNotFoundException e) {
			logger.error(e + ": properties 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			logger.error(e + ": properties 파일을 읽는데 실패했습니다.");
		}
	}

	// 지정 디렉토리 감시.
	public static void pollingDir() {
		//클라이언트 처음 기동 되었을 때 ing 파일 원본으로 바꾸기.
		File initFiles = new File(path);
		File[] initFileList = initFiles.listFiles();
		for(File file:initFileList) {
			if(file.getName().contains("_ing")) {
				FileFilter.renameToOri(file, path);
			}
		}
		while (true) {
			try {
//				System.out.println("감시중?");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			createFileInfo();
			for (File file : fileList) {
				System.out.println("바꾸기 전: "+file.exists());
				if (file.isFile() && !file.getName().contains("_ing")) {
					File f = FileFilter.renameToIng(file, path);
					System.out.println("바꾸기전 file : " + file.exists());
					File ingFile = new File(f.getPath());
					String fileName = ingFile.getName();
					long fileSize = ingFile.length();
					System.out.println("바꾼 후 존재여부: "+ingFile.exists()+": fileSize: "+fileSize+" : 파일경로: "+ingFile.getPath());
					//파일 변화 감지
					if(isFileChange(ingFile)) continue;
					System.out.println("continue 되면 안됨.");
					ClientSender cs = new ClientSender(port, ip, path, fileName, fileSize);
					// 작업큐에 담기
					executorService.submit(cs);
				}
			}
		}
	}

	// 파일이 변하는지 감지하는 메서드
	public static boolean isFileChange(File file) {

		long time1 = file.lastModified();
		try {
			Thread.sleep(1000);	//1초 간격 주기.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		long time2 = file.lastModified();
		System.out.println(time1 +" : " +time2 + " : " + file.exists());
		if (time1 == time2) {
			return false;
		}else {
			return true;			
		}
	}

	// 파일 정보 정보 생성
	public static void createFileInfo() {
		File f = new File(path);
		fileList = f.listFiles();
	}
}
