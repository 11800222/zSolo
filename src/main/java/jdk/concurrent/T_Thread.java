package jdk.concurrent;

public class T_Thread extends Thread {

	public static void main(String[] args) {
		ThreadLocal<String> strBoundThreadLocal = new ThreadLocal<>();
		ThreadLocal<Integer> IntBoundThreadLocal = new ThreadLocal<>();

		strBoundThreadLocal.set("qwqw");
		System.out.println(strBoundThreadLocal.get());

		IntBoundThreadLocal.set(2);
		System.out.println(IntBoundThreadLocal.get());
	}

}

class Runner implements Runnable {
	public void run() {
		System.out.println("run");
	}
}