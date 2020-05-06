package example3;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	private static int port = 5000;	//포트번호
	
	public static void main(String[] args) {
		//필요한 객체를 저장할 변수 만들기
		ServerSocket serverSocket=null;
		try{
			serverSocket=new ServerSocket(port);
			System.out.println("클라이언트의 Socket 연결 요청을 대기중...");
			while(true){
				//연결요청을 해오면 클라이언트와 연결된 Socket 객체의 참조값이 리턴된다.
				Socket socket=serverSocket.accept();
				
				//FileDownThread 객체를 생성하고 start() 메소드를 호출해서
				FileDownThread ft=new FileDownThread(socket);
				ft.setDaemon(true);	//데몬형태
				ft.start();
				ft.join();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(serverSocket!=null)serverSocket.close();
			}catch(Exception e){}
		}
		System.out.println("Server main() 메소드가 종료 됩니다. ");
	}
}
