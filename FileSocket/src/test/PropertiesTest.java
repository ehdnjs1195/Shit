package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setProperty("autosave", "5");
		p.setProperty("language", "한글");
		p.setProperty("timeout", "10");
		
		System.out.println(p);
		System.out.println(p.getProperty("autosave"));
		System.out.println(p.getProperty("autosave2","0"));	//키값에 해당하는 것이 없을때 디폴트로 0 을 출력함.
		
		p.list(System.out);
		/*
		 *  properties 의 장점은
		 *  쉽게 txt파일이나 xml 파일로 저장할 수 있다.
		 *  
		 *  간혹 한글을 저장할 때 txt에서는 깨지지만 xml 에서는 깨지지 않는다. 
		 *  따라서 한글을 저장할 때는 xml로 저장하면 된다.
		 */
		
		try {
			p.load(new FileInputStream("output.txt"));	//properties 파일로 부터 읽어오기
			System.out.println(p);
			//store()는 일반 txt 파일로 저장.
			p.store(new FileOutputStream("output.txt")	//저장할 파일의 경로(이름)
					, "Properties Example");	//파일에대한 설명. 파일의 주석으로 저장된다.
			//storeToXML()은 XML형식으로 저장한다.
			p.storeToXML(new FileOutputStream("output.xml"), "Properties Example");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
