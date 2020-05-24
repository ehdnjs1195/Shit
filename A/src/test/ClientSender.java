package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ClientSender extends Thread{
	private final static Logger logger = Logger.getLogger(ClientSender.class.getName());
	private String path;
	private int port;
	private String ip;
	private String fileName;
	private long fileSize;
	
	public ClientSender() {}
	public ClientSender(int port, String ip, String path, String fileName, long fileSize) {
		this.path = path;
		this.port = port;
		this.ip = ip;
		this.fileName = fileName;
		this.fileSize = fileSize;
		setDaemon(true);
		BasicConfigurator.configure();
	}
	
	@Override
	public void run() {
		sendFile(fileName, fileSize);
		
	}
	
	//파일 전송
	public void sendFile(String fileName, long fileSize) {
		try(Socket socket = new Socket(ip, port);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				FileInputStream fi = new FileInputStream(path+fileName);
				BufferedInputStream bis = new BufferedInputStream(fi);
				BufferedOutputStream bos = new BufferedOutputStream(dos);) {
			//파일 정보 전송	
			dos.writeUTF(fileName);
			dos.writeLong(fileSize);

			byte[] buffer = new byte[1024];
			int length = -1;
			logger.debug(Thread.currentThread().getName()+ " : ["+fileName+"] 전송시작!");
			while((length = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, length);
				bos.flush();
			}
			logger.info(Thread.currentThread().getName() + " : [" + fileName + "] 전송완료");
			bos.close();
			bis.close();
			deleteFile(path, fileName);
			logger.debug("[" + fileName + "] 파일 삭제");
		} catch (UnknownHostException e) {
			logger.error(e+": 서버를 찾을 수 없습니다.");
		} catch (SocketException e) {
			logger.error(e + ": 서버와 연결 끊김");

			File f = new File(path+fileName);
			File f2=FileFilter.renameToOri(f, path);

			System.out.println(f2.exists() + " : " + f2.getPath()+" : [전송 재시도]");
		} catch (IOException e) {

			logger.error(e+": ["+fileName+"] 파일을 읽을 수 없습니다.");

		} 
	}
	
	public void deleteFile(String path, String fileName) {
		File f =new File(path + fileName);
		if(f.exists()) f.delete();
	}
	
}
