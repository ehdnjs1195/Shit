package test2;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

public class Monitor extends Thread{
	private int port = 5000;	//포트번호
	private String ip = "127.0.0.1";	//ip주소
	private String directory = "c:"+File.separator+"myFolder"+File.separator;	//지정경로
	private List<String> failedFiles= new ArrayList<>();
	
	
	public Monitor() {}
	
	
	@Override
	public void run() {
		try {
				monitoringDir(directory);	
				
				Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("감시 중단1");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("감시 중단2");
		}
	}
	//지정 디렉토리를 감시하기
	public void monitoringDir(String dir) throws IOException, InterruptedException {
		Object context = null;
		WatchService myWatchService = FileSystems.getDefault().newWatchService();
	
		// 모니터링을 원하는 디렉토리 Path를 얻는다.
		Path path = Paths.get(dir); 

		// 모니터링 서비스를 할 path에 의해 파일로케이션을 등록
		WatchKey watchKey = path.register(myWatchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE); 
		while(true) {
			Thread.sleep(1000);	//1초 간격으로 감시.
			//System.out.println("디렉토리를 감시중입니다.");
			// 변화가 감지되는 경우 이벤트 종류와 파일명을 출력
			for (WatchEvent event : watchKey.pollEvents()) {
				System.out.println("변화 감지!!");
				System.out.println(event.kind() + ": " + event.context());
				//파일이 생성이 감지되면 파일전송하기.
				if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					context = event.context();
					sendFile(context);
					}
				}
			if (!watchKey.reset()) {
				break; // 디렉토리등이 삭제되는 경우
			}
		}
	}
	
	//소켓 연결하고 파일 전송.
	public void sendFile(Object context) {
		//필요한 객체를 담을 변수 만들기
		Socket socket=null;
		OutputStream os=null;
		DataOutputStream dos = null;
		FileInputStream fis=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw=null;
		BufferedOutputStream bo=null;
		try{
			socket=new Socket(ip, port);
			System.out.println("Socket 연결 성공!");
			fis=new FileInputStream(directory+context);
//			os=socket.getOutputStream();
			dos = new DataOutputStream(socket.getOutputStream());
//			bo = new BufferedOutputStream(os);
			//파일명 보내기.
//			osw=new OutputStreamWriter(os);
//			bw=new BufferedWriter(osw);
//			String name = context.toString();
//			bw.write(name);	
//			bw.newLine(); 	
//			bw.flush(); 	
			dos.writeUTF(context.toString());
			File f = new File(directory + context);
			dos.writeLong(f.length());
			int length = 0;
			byte[] buffer=new byte[1024];
			while((length = fis.read(buffer)) != -1) {
				dos.write(buffer, 0 , length);
				dos.flush();
			}
			
			//byte 데이터를 읽어들일 배열
//			
//			while(true){
//				int readedByte=fis.read(buffer);
//				if(readedByte==-1)break;
//				bo.write(buffer, 0, readedByte);
//				bo.flush();//방출
//			}
			System.out.println("파일 전송 성공!");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(os!=null)os.close();
				if(fis!=null)fis.close();
				if(bw!=null)bw.close();
				if(osw!=null)osw.close();
				if(socket!=null)socket.close();
			}catch(Exception e){}
		}
	}
	
	
}
