package study2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NoResultExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		System.out.println("[작업 처리 요청]");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int sum = 0;
				for(int i=1;i<=10;i++) {
					sum += i;
				}
				System.out.println("[처리결과] " +sum);
			}
		};
		Callable callable = new Callable() {
			@Override
			public Object call() throws Exception {
				System.out.println("[작업중]");
				int a = Integer.parseInt("삼");
				return "[작업 완료]";
			}
		};
		
//		executorService.submit(runnable);
		Future f =executorService.submit(callable);
		System.out.println(f.get());
	}

}
