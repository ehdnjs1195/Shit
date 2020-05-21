package test;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Test {
	private final static Logger logger = Logger.getLogger(Test.class.getName());
	public static void main(String[] args) {
		File f = new File("C:/myFolder/win64_11gR2_database_1of2.zip");

		File ingF=FileFilter.renameToIng(f, "c:/myFolder/");
		System.out.println(ingF.exists() +":"+ingF.canWrite());
		
	}
	
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
	public static void checkFile() {
		
		
		
		
		
		/*
		 *  결론 : 처음 파일 객체 f 에서 renameTo() 를 써도
		 *  f의 이름은 바뀌는 것이 아님.
		 *  새로운 파일 객체 f2 를 생성해서 사용해야 함.
		 *  => f 는 없게 됨. 
		 */
	}
}

