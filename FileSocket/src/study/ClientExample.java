package study;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientExample {
	Socket socket;
	
	void startClient() {	//연결 시작 코드
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress("localhost", 5001));	//5001에서 서버가 실행하고 있지 않다면 connect() 에서 IOException 발생할 수 있다.
					System.out.println("[연결 성공: "+socket.getRemoteSocketAddress()+"]"); //서버의 ip,port
				} catch (IOException e) {
					System.out.println("[서버 통신 안됨]");
					if(!socket.isClosed())stopClient();
					return;	//run() 종료.
				}
				receive();	//서버가 보낸 데이터를 받도록.
			}
		};
		thread.start();
	}
	
	void stopClient() {
		try {
			System.out.println("[연결 끊음]");
			if(socket!=null && !socket.isClosed()) socket.close();
		}catch(Exception e) {}
	}
	
	void receive() {	
		//항상 서버의 데이터를 받아야 하므로 무한루프
		while(true) {
			try {
				byte[] buffer = new byte[512];
				InputStream inputStream = socket.getInputStream();
				
				int readByteCount = inputStream.read(buffer);	//read()메소드는 서버가 데이터를 보내기 전까지 블로킹 되어있는 상태이다. / 서버가 비정상적 종료되면 IOException 발생함.
				if(readByteCount == -1) {
					throw new IOException();	
				}
				String data = new String(buffer, 0 , readByteCount, "UTF-8");
				System.out.println("[받기완료] "+data);
				
			}catch(Exception e) {
				System.out.println("[서버 통신 안됨]");
				stopClient(); //안전하게 클라이언트 종료.
				break;	//더이상 while 문이 실행되면 안되므로
			}
			
		}
	}
	
	void send(String data) {}
}
