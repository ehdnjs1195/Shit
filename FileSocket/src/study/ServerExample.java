package study;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerExample {
	ExecutorService executorService;
	ServerSocket serverSocket;
	List<Client> connections = new Vector<Client>();	//벡터를 사용한 이유: 클라이언트를 관리하기 위해, 스레드풀에 안전한 벡터를 사용함
	
	void startServer() {	//서버 시작 코드
		executorService = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors());	//현재 본인 pc가 지원하는 코어의 수를 얻어내고, 그 수만큼 스레드를 생성해서 사용한다.
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 5001));
		} catch(Exception e) {
			if(!serverSocket.isClosed())stopServer();
			return;	//startServer() 를 종료하도록.
		}
		
		Runnable runnable = new Runnable() {	
			@Override
			public void run() {	//연결 수락 코드를 적는다.
				while(true) {
					try {
						System.out.println("[연결 대기중]");
						Socket socket = serverSocket.accept();	//통신용 소켓을 만듬.
						System.out.println("[연결 수락: " + socket.getRemoteSocketAddress() + ": "	//클라이언트의 ip,port 정보 출력
						+ Thread.currentThread().getName() + " ]");	//현재 스레드풀의 담당 스레드 이름 출력.
						
						Client client = new Client(socket);	//클라이언트 객체 생성
						connections.add(client);
					} catch (IOException e) {
						e.printStackTrace();
						if(!serverSocket.isClosed())stopServer();
						break;	//예외 발생하면 서버 닫고 무한루프 탈출.
					}	
					
				}
			}
		};
		//작업 객체 runnable을 매개값으로 전달.
		executorService.submit(runnable);	
	}
	
	void stopServer() {	//서버 종료 코드 
		/*
		 * 	1. 연결된 모든 클라이언트 소켓 종료
		 *  2. 리스트에서 클라이언트 삭제
		 *  3. 서버소켓 종료
		 *  4. ExecutorService 종료
		 */
		try {
			Iterator<Client> iterator = connections.iterator();
			while(iterator.hasNext()) {	//읽어올 요소가 남아있는지 확인.
				Client client = iterator.next();	//클라이언트를 가져온다.
				client.socket.close();	//소켓연결 끊기.
				iterator.remove();	//리스트에서 삭제.
			}
			//서버소켓 종료
			if(serverSocket!=null && !serverSocket.isClosed())serverSocket.close();
			//ExecutorService 종료
			if(executorService!=null && !executorService.isShutdown())executorService.shutdown();
			System.out.println("[서버 종료]");
		} catch(Exception e) {}
	}
	
	class Client {	//데이터 통신 코드  => 서버에 연결되는 클라이언트 하나당 하나씩 객체 생성.
		Socket socket;
		Client(Socket socket){//생성자
			this.socket = socket;
			receive();	//클라이언트 객체를 생성할 때 receive 되도록 메소드 호출.
		}	
		
		void receive() {	//클라이언트가 보낸 데이터 받는 메소드
			//스레드 풀의 스레드가 작업하도록 객체 생성.
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						while(true) {	//이 무한루프의 종료 시점은 예외가 발생하여 catch 문으로 넘어갔을 때 이다.
							byte[] buffer = new byte[512];
							InputStream inputStream = socket.getInputStream();
							
							int readByteCount = inputStream.read(buffer);	//클라이언트 비정상 종료시 IOException 발생.
							//읽어올 것이 없으면 아래 코드를 실행했을 때 Exception이 발생하므로
							if(readByteCount == -1) {	//클라이언트가 정상 종료를 했다면.(-1리턴)
								throw new IOException();	//강제로 IOException 발생되도록 해서 아래 catch 에서 한번에 처리할 수 있도록한다.
							}
							System.out.println("[요청 처리: " + 
									socket.getRemoteSocketAddress() + ": " +	//클라이언트 ip, port 
									Thread.currentThread().getName() + " ]");	//사용중인 스레드 이름
							//데이터 출력.(어떤 방식이든)
							
							for(Client client:connections) {
								//client.send(data);
							}
						}
					}catch(Exception e) {	//IOException 처리.
						try {
							//더이상 클라이언트가 연결되어 있지 않으므로 List에서 제거
							connections.remove(Client.this);
							System.out.println("[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + //통신이 끊긴 클라이언트 ip,port
									": "+Thread.currentThread().getName()+" ]");	//해당 스레드 이름.
							socket.close();	//클라이언트가 소켓을 종료 했으므로 서버쪽에서도 종료.
						}catch(IOException e1) {}
					}
				}
			};
			//runnable 객체를 만들고 나서 executorService 즉, 스레드 풀이 이 작업 객체를 작업 큐에 저장할 수 있도록 하기 위해
			executorService.submit(runnable);	//submit을 하면 executorService 내부의 스레드가 run() 작업을 실행함. 
		}
		
		void send(String data) {	//클라이언트로 데이터를 보내는 메소드
			//스레드 풀의 스레드가 작업하도록 runnable 만들기
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						byte[] buffer = data.getBytes("UTF-8");
						OutputStream outputStream = socket.getOutputStream();
						outputStream.write(buffer);	//write() 에서 IOException 발생. (클라이언트가 통신이 안될 경우)
						outputStream.flush();
					} catch (Exception e) {
						try {
							e.printStackTrace();
							System.out.println("[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + //통신이 끊긴 클라이언트 ip,port
									": "+Thread.currentThread().getName()+" ]");	//해당 스레드 이름.
							//클라이언트가 통신이 더이상 안되므로 List에서 제거
							connections.remove(Client.this);
							socket.close();
						} catch (IOException e1) {}
					}
					
				}
			};
			//보내는 작업을 스레드 풀의 작업큐에 저장
			executorService.submit(runnable);
		}
	}
}
