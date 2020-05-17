package test2;

import java.io.File;

public class ClientMain {
	public static void main(String[] args) {
		System.out.println("[클라이언트 폴더 감지중]");
		Monitor monitor = new Monitor();
		monitor.setDaemon(true);	
		monitor.start();
		try {
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("클라이언트 종료.");
	}
	
	public void sendFileInfo() {
		File f = new File("c:/myfolder");
		//경로에 디렉토리가 없을 경우 디렉토리 생성
		if(!f.exists()) {
			f.mkdir();
		}
		File[] fileList = f.listFiles();
		for(File file:fileList) {
			String fileName = file.getName();
			long fileSize = file.length();
		}
	}
}
