package test2;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class FileDownThread implements Runnable{
	private Socket socket;
	private String directory = "c:"+File.separator+"yourFolder"+File.separator;	//파일을 저장할 지정경로
	
	public FileDownThread(Socket socket) {
		this.socket=socket;
		
	}
	
	@Override
	public void run() {
		InputStream is=null;
		FileOutputStream fos=null;
		DataInputStream dis = null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		BufferedOutputStream bo=null;
		System.out.println("["+Thread.currentThread().getName()+" : 작업중]");
		try{
			//파일을 전송한 클라이언트의 ip 주소 
			String ip=socket.getInetAddress().getHostAddress();
			
			is=socket.getInputStream();
			
			//파일명 읽어오기
//			isr=new InputStreamReader(is);
//			br=new BufferedReader(isr);
//			String name = br.readLine();
			dis = new DataInputStream(socket.getInputStream());
			String name = dis.readUTF();
			long size = dis.readLong();
			System.out.println("["+name+"]"+" size: "+size);
			//저장 경로
			String path=directory+name;
			if(name==null) {
				throw new IOException();
			}
			
			fos=new FileOutputStream(path);
			bo = new BufferedOutputStream(fos);
			byte[] buffer=new byte[1024];
			
			while(true){
				int readedByte=is.read(buffer);
				if(readedByte==-1) {
					break;
				}
				//읽어들인 만큼 파일에 출력하기
				bo.write(buffer, 0, readedByte);
				bo.flush();
			}
			System.out.println(ip+" 에서 전송한 파일["+name+"] 저장성공");
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try{
				if(is!=null)is.close();
				if(fos!=null)fos.close();
				if(bo!=null)bo.close();
				if(br!=null)br.close();
				if(isr!=null)isr.close();
				if(socket!=null)socket.close();
			}catch(Exception e){}
		}
		System.out.println("file 스레드가 종료 됩니다.");		
	}
	
}









