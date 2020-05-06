package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesEx2 {
	public static void main(String[] args) {
		//commandline에서(String[] args 말하는듯) inputfile을 지정해주지 않으면 프로그램을 종료한다.
		if(args.length != 1) {	//커맨드라인에서 값의 개수가 모자르면 프로그램이 종료된다.	=>Run Configuration 에서 input.txt를 넣어준다.
			System.out.println("USAGE: java PropertiesEx2 INPUTFILENAME");
			System.exit(0);	//시스템 종료인듯.
		}
		Properties prop = new Properties();
		
		String inputFile = args[0];
		System.out.println(inputFile);
		
		try {
			prop.load(new FileInputStream(inputFile));
		} catch(IOException e) {
			System.out.println("지정된 파일을 찾을 수 없습니다.");
			System.exit(0);
		}

		String name = prop.getProperty("name");
		String[] data = prop.getProperty("data").split(",");
		int max = 0 ;
		int min = 0 ;
		int sum = 0 ;
		for(int i=0 ; i < data.length; i++) {
			int intValue = Integer.parseInt(data[i]);
			if(i==0) max = min = intValue;
			
			if(max < intValue) {
				max = intValue;
			} else if(min > intValue) {
				min = intValue;
			}
			
			sum += intValue;
		}
		
		System.out.println("이름: " + name);
		System.out.println("최대값: " + max);
		System.out.println("최소값: " + min);
		System.out.println("합계: " + sum);
		System.out.println("평균: " +(double)sum/data.length);
	}
}
