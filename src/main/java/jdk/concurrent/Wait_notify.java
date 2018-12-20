package jdk.concurrent;

import java.util.Scanner;

public class Wait_notify {
	static Scanner sc = new Scanner(System.in);
	static int cpu = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) {
		try {
			Object lock = new Object();

			creatThreadsForLock(lock);

			sc.nextLine();

			synchronized (lock) {

				lock.notify();

				//				lock.notifyAll();

				System.out.println("main ends");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void creatThreadsForLock(Object o) {
		for (int i = 0; i < cpu + 2; ++i) {
			new Thread(new CompetLock(o)).start();

		}
	}

}

class CompetLock implements Runnable {
	Object lock;

	public CompetLock(Object o) {
		this.lock = o;
	}

	@Override
	public void run() {
		String thread = Thread.currentThread().toString();
		try {

			System.out.println(thread + " trying to get lock : " + lock);
			synchronized (lock) {
				System.out.println(thread + " has got lock " + lock + " and lock.wait()");

				lock.wait();

				System.out.println(thread + " has wake up from lock " + lock + " .wait() ");
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}