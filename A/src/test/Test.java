package test;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Test {
	private final static Logger logger = Logger.getLogger(Test.class.getName());
	public static void main(String[] args) {
		BasicConfigurator.configure();
		checkFile();
	}
	
	public static void checkFile() {
		
		File f = new File("c:/myfolder/test.txt");
		logger.info("info: 파일이 존재하는가? : " + f.exists());
		logger.debug("디버그 체크");
		logger.error("에러체크");
	}
}

