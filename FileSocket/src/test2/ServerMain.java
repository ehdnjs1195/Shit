package test2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerMain {
	private static int port = 5000;	//포트번호
	private static ExecutorService executorService;
	public static void main(String[] args) {
		//필요한 객체를 저장할 변수 만들기
		ServerSocket serverSocket=null;
		Runnable r = null;
		Thread t = null;
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try{
			serverSocket=new ServerSocket(port);
			System.out.println("[서버 시작]");
			while(true){
				//연결요청을 해오면 클라이언트와 연결된 Socket 객체의 참조값이 리턴된다.
				Socket socket=serverSocket.accept();
			
				r = new FileDownThread(socket);
				t = new Thread(r);
				t.setDaemon(true);
				t.join();
				executorService.submit(t);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(serverSocket!=null)serverSocket.close();
			}catch(Exception e){}
		}
		executorService.shutdown();
		System.out.println("Server main() 메소드가 종료 됩니다. ");
	}
}
