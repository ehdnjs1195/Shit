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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ServerReceiver extends Thread{
	private final static Logger logger = Logger.getLogger(ServerReceiver.class.getName());
	private Socket socket;
	private String path;
	
	public ServerReceiver() {}
	public ServerReceiver(Socket socket, String path) {
		this.socket = socket;
		this.path = path;
		setDaemon(true);
		BasicConfigurator.configure();
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
		logger.debug("["+Thread.currentThread().getName()+" : 작업중]");
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
			bos.close();
			bis.close();
			File ingFile = new File(path + fileName);
			//파일 저장 검사
			if(data == fileSize) {
				File file = FileFilter.renameToOri(ingFile, path);
				String oriFileName = file.getName();
				logger.debug(oriFileName+" : 받은 데이터 ["+data +"] = 파일사이즈 [" +fileSize+"]");
				logger.info(ip + " : [" +oriFileName + "] 저장 성공");
			}else if(fileSize == 0 || data != fileSize){				
				logger.info("파일 저장 실패");	 //발생할 수 있는 실패 이유는?
			}
			
		} catch (FileNotFoundException e) {	//fos에서 발생.
			logger.error(e+": 파일을 찾을 수 없습니다.");
		} catch(IOException e){	//dis에서 발생할 수 있음
			logger.error(e+": 파일을 읽을 수 없습니다.");
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
		logger.debug("["+Thread.currentThread().getName()+" : 작업 종료]");
	}
}
