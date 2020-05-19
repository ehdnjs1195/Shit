package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
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
		Socket socket = null;
		DataOutputStream dos = null;
		FileInputStream fi = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			socket = new Socket(ip, port);

			//파일 정보 전송
			dos = new DataOutputStream(socket.getOutputStream());	
			dos.writeUTF(fileName);
			dos.writeLong(fileSize);
			
			fi = new FileInputStream(path+fileName);
			bis = new BufferedInputStream(fi);
			bos = new BufferedOutputStream(dos);
			
			byte[] buffer = new byte[1024];
			int length = -1;
			logger.debug(Thread.currentThread().getName()+ " : ["+fileName+"] 전송시작!");
			while((length = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, length);
				bos.flush();
			}
			logger.info(Thread.currentThread().getName() + " : [" + fileName + "] 전송완료");
			bis.close();
			bos.close();
			deleteFile(path, fileName);
			logger.debug("[" + fileName + "] 파일 삭제");
		} catch (UnknownHostException e) {
			logger.error(e+": 서버를 찾을 수 없습니다.");
		} catch (IOException e) {
			logger.error(e+": ["+fileName+"] 파일을 읽을 수 없습니다.");
		} finally {
				try {
					if(bos != null)bos.close();
					if(bis != null)bis.close();
					if(dos != null)dos.close();
					if(fi != null)fi.close();
					if(socket != null)socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void deleteFile(String path, String fileName) {
		File f =new File(path + fileName);
		if(f.exists()) f.delete();
	}
	
}
