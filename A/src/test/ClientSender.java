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
	private File[] fileList;
	private String path;
	private int port;
	private String ip;
	
	public ClientSender() {}
	public ClientSender(int port, String ip, String path) {
		this.path = path;
		this.port = port;
		this.ip = ip;
	}
	
	@Override
	public void run() {
		pollingDir();
		
	}
	
	//지정 디렉토리 감시 아직 미완성.
	public void pollingDir() {
		createFileInfo();
		for(File file:fileList) {
			if(file.isFile()) {
				String fileName = file.getName();
				long fileSize = file.length();
				sendFile(fileName ,fileSize);
			}
		}
	}
	
	//파일 정보 정보 생성
	public void createFileInfo() {
		File f = new File(path);
		if(fileList.length != 0) {	//디렉토리에 파일이 있다면
			fileList = f.listFiles();			
		}
	}
	
	//파일 전송
	public void sendFile(String fileName, long fileSize) {
		Socket socket = null;
		DataOutputStream dos = null;
		try(FileInputStream fi = new FileInputStream(path+fileName);
				FileOutputStream fo = new FileOutputStream(path+fileName);
				BufferedInputStream bis = new BufferedInputStream(fi);
				BufferedOutputStream bos = new BufferedOutputStream(dos);	//dos를 여기서 넣어도 되나?
				) {
			socket = new Socket(ip, port);

			dos = new DataOutputStream(socket.getOutputStream());	//try ~ with resource 구문으로 쓸 수 있나? 순서가 어떻게 되지?
			dos.writeUTF(fileName);
			dos.writeLong(fileSize);
			
			byte[] buffer = new byte[1024];
			int length = -1;
			while((length = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, length);
				bos.flush();
			}
			System.out.println(Thread.currentThread().getName() + " : [" + fileName + "] 전송완료");
			deleteFile(path, fileName);
			System.out.println("[" + fileName + "] 파일 삭제");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if(dos != null)dos.close();
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
