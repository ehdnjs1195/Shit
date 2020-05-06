package example3;


public class ClientMain {
	public static void main(String[] args) {
		System.out.println("[클라이언트 폴더 감지중]");
		Monitor monitor = new Monitor();
		monitor.setDaemon(true);	
		monitor.start();
		try {
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("클라이언트 종료.");
	}
}
