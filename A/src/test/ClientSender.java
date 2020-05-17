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

public class ClientSender extends Thread{
//	private File[] fileList;
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
			dos = new DataOutputStream(socket.getOutputStream());	//try ~ with resource 구문으로 쓸 수 있나? 순서가 어떻게 되지?
			dos.writeUTF(fileName);
			dos.writeLong(fileSize);
			
			fi = new FileInputStream(path+fileName);
			bis = new BufferedInputStream(fi);
			bos = new BufferedOutputStream(dos);
			
			byte[] buffer = new byte[1024];
			int length = -1;
			System.out.println(Thread.currentThread().getName()+ " : ["+fileName+"] 전송시작!");
			while((length = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, length);
				bos.flush();
			}
			System.out.println(Thread.currentThread().getName() + " : [" + fileName + "] 전송완료");
			bis.close();
			bos.close();
			deleteFile(path, fileName);
			System.out.println("[" + fileName + "] 파일 삭제");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
