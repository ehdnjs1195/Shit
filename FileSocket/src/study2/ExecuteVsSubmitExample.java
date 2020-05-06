package study2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecuteVsSubmitExample {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());	//최대 2개 스레드
		
		for(int i = 0 ; i < 10; i ++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					
					ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
					int poolSize = threadPoolExecutor.getPoolSize();	//현재 스레드 풀에 있는 스레드 수
					
					System.out.println("[총 스레드 개수: "+poolSize+"] 작업 스레드 이름: "+ Thread.currentThread().getName());
					int value = Integer.parseInt("삼");	//NumberFormatException 발생시킴.
				}
			};
		
			//executorService.execute(runnable);
			Future<Integer> result = executorService.submit(runnable, 3);
			System.out.println(result.isCancelled());
			Thread.sleep(10); //일부러 잠깐 쉼. 예외 문자를 스레드 바로 밑에 보이도록
		}
		
		executorService.shutdown();
	}
}
