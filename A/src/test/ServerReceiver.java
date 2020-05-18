package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerReceiver implements Runnable{
	private Socket socket;
	private String path;
	
	public ServerReceiver() {}
	public ServerReceiver(Socket socket, String path) {
		this.socket = socket;
		this.path = path;
	}
	
	@Override
	public void run() {
		receiveFile();
	}
	
	//파일 저장
	public void receiveFile() {
		DataInputStream dis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		
		String ip=socket.getInetAddress().getHostAddress();
		System.out.println("["+Thread.currentThread().getName()+" : 작업중]");
		try {
			dis = new DataInputStream(socket.getInputStream());
			String fileName = dis.readUTF();
			long fileSize = dis.readLong();
			long data = 0;
			fos = new FileOutputStream(path+fileName);
			bos = new BufferedOutputStream(fos);
			is = socket.getInputStream();
			bis = new BufferedInputStream(is);
			
			byte[] buffer = new byte[1024];
			int length = -1;
			while((length = bis.read(buffer)) >0) {
				bos.write(buffer, 0, length);
				bos.flush();
				data += length;
			}
			
			//파일 저장 검사
			if(data == fileSize) {
				System.out.println("받은 데이터 ["+data +"] : 파일사이즈 [" +fileSize+"]");
				System.out.println(ip + " : [" +fileName + "] 저장 성공");
			}else if(fileSize == 0 || data != fileSize){				
				File f = new File(path+fileName);
				f.delete();
				System.out.println("파일 저장 실패");	 //발생할 수 있는 실패 이유는?
			}
			
		} catch (FileNotFoundException e) {	//fos에서 발생.
			e.printStackTrace();
		} catch(IOException e){	//dis에서 발생할 수 있음
			e.printStackTrace(); 
		} finally {
			try {
				if(bos!=null)bos.close();
				if(bis!=null)bis.close();
				if(is!=null)is.close();
				if(fos!=null)fos.close();
				if(dis!=null)dis.close();
				if(socket!=null)socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("["+Thread.currentThread().getName()+" : 작업 종료]");
	}
	
	
}
