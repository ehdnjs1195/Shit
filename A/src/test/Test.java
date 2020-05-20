package test;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Test {
	private final static Logger logger = Logger.getLogger(Test.class.getName());
	public static void main(String[] args) {
//		BasicConfigurator.configure();
		checkFile();
	}
	
	public static void checkFile() {
		
		File f = new File("C:/myFolder/test.txt");
//		File[] listFiles = f.listFiles();
//		for(File file:listFiles) {
//			System.out.println(file.getName());
//		}
		
//		logger.info("info: 파일이 존재하는가? : " + f.exists());
//		logger.debug("디버그 체크");
//		logger.error("에러체크");
		System.out.println(f.exists());
		System.out.println(f.getName());
		String d=f.getName().replace(".", "-ing.");
		System.out.println(d);
		System.out.println(f.renameTo(new File("c:/myFolder/"+d)));
		File f2 =new File("c:/myFolder/"+d);
		System.out.println(f.getName());
		System.out.println(f2.getName());
		
		/*
		 *  결론 : 처음 파일 객체 f 에서 renameTo() 를 써도
		 *  f의 이름은 바뀌는 것이 아님.
		 *  새로운 파일 객체 f2 를 생성해서 사용해야 함.
		 *  => f 는 없게 됨. 
		 */
	}
}

