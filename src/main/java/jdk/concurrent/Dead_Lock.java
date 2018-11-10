
package jdk.concurrent;

import java.util.concurrent.CountDownLatch;

public class Dead_Lock {
	public static CountDownLatch countDownLatch = new CountDownLatch(2);

	public static void main(String[] args) {

		new Thread(new Run_holder(1, 2)).start();
		new Thread(new Run_holder(2, 1)).start();
	}

}

class Run_holder implements Runnable {
	private int first_Lock;
	private int second_Lock;

	Run_holder(int first, int second) {
		first_Lock = first;
		second_Lock = second;

	}

	@Override
	public void run() {
		try {
			synchronized (Integer.valueOf(first_Lock)) {
				Dead_Lock.countDownLatch.countDown();

				Dead_Lock.countDownLatch.await();

				System.out.println("one Thread in expected postion");
				synchronized (Integer.valueOf(second_Lock)) {
					System.out.println("fail");
				}
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}