package jdk.concurrent;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class T_MyLock {

	public static void main(String[] args) throws InterruptedException {

	}

	public static void Lock_Test() throws InterruptedException {
		final CountDownLatch cdl = new CountDownLatch(1);
		final CountDownLatch cdl2 = new CountDownLatch(100);

		for (int i = 0; i < 100; ++i) {
			new Thread(new Runnable() {
				public void run() {
					try {
						cdl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					My_AtomicInteger.Optimistic_Increment();
					cdl2.countDown();
				}
			}).start();
		}

		Thread.sleep(3000);
		cdl.countDown();
		cdl2.await();
		System.out.println(My_AtomicInteger.value);
	}

}

class My_AtomicInteger {
	static volatile int value;
	static Simple_SpinLock simple_SpinLock = new Simple_SpinLock();

	public static void Pessimistic_Increment() {
		simple_SpinLock.lock();

		int current = value;
		int next = current + 1;
		value = next;

		simple_SpinLock.unlock();
	}

	public static void Optimistic_Increment() {
		for (;;) {
			int current = value;
			int next = current + 1;

			if (compareAndSet(current, next))//检测冲突，并返回结果，本身是一个复合操作，需要同步起来；
				return;

		}
	}

	public static boolean compareAndSet(int expect, int next) {
		synchronized (My_AtomicInteger.class) {//提供同步性
			if (value == expect) {
				value = next;
				return true;
			}
			return false;
		}

	}
}

//同步,自旋轮询锁状态
class Simple_SpinLock {
	static AtomicBoolean islocked = new AtomicBoolean(false);

	public void lock() {
		while (!islocked.compareAndSet(false, true)) {

		}

	}

	public void unlock() {
		islocked.set(false);

	}

}

//异步，通知锁状态,有点像AQS了
class Park_Lock {
	static AtomicBoolean islocked = new AtomicBoolean(false);
	static Queue<Thread> ParkList = new LinkedBlockingQueue<Thread>();

	public void lock() {
		while (!islocked.compareAndSet(false, true)) {
			ParkList.add(Thread.currentThread());
			LockSupport.park();
		}

	}

	public void unlock() {
		islocked.set(false);
		Thread t = ParkList.poll();
		LockSupport.unpark(t);
	}

}
