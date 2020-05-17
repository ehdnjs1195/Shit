package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Test {
	public static void main(String[] args) {
		checkFile();
	}
	
	public static void checkFile() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte[] buffer = new byte[1024];
		int length = -1;
		String fileName =null;
		while(true) {
			File f = new File("c:/myfolder/");
			File[] fileList = f.listFiles();
			for(File file:fileList) {
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					System.out.println("------------");
					
					
					length += bis.read(buffer);
					long fileSize = file.length();
					fileName = file.getName();
					System.out.println(fileName +" : "+ fileSize+" : " +length);
					Thread.sleep(500);
				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
					System.out.println(fileName + "디렉토리에 복사중");
				}catch (InterruptedException e) {
					e.printStackTrace();
				}catch(IOException e){	//dis에서 발생할 수 있음
					e.printStackTrace(); 
				}
			}
		}
	}


}
