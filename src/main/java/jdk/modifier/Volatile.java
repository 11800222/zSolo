package jdk.modifier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Volatile implements Runnable {
	public static volatile int volatile_num = 0;
	public static int usuall_num = 0;
	public static int synchronized_num = 0;
	public static AtomicInteger atomicInteger = new AtomicInteger(0);
	public static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String dw[]) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		Volatile v = new Volatile();
		for (int i = 0; i < 50000; ++i) {
			executorService.submit(v);
		}

		System.out.println("synchronized_num : " + synchronized_num + ";  volatile_num : " + volatile_num + ";  usuall_num : " + usuall_num);
		countDownLatch.countDown();
		executorService.shutdown();
		if (executorService.awaitTermination(10, TimeUnit.SECONDS))
			System.out.println("synchronized_num : " + synchronized_num + ";  volatile_num : " + volatile_num + ";  usuall_num : " + usuall_num);

	}

	public static void selfplus() throws Exception {
		countDownLatch.await();
		++volatile_num;
		++usuall_num;
		atomicInteger.incrementAndGet();
		synchronized (Volatile.class) {
			++synchronized_num;
		}
	}

	@Override
	public void run() {
		try {
			selfplus();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
