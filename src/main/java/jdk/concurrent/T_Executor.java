package jdk.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class T_Executor {
	static boolean num = true;

	public static void main(String[] args) throws InterruptedException {
		method();

	}

	public static void method() throws InterruptedException {
		ThreadPoolExecutor t = new ThreadPoolExecutor(1, 1, 70l, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		t.execute(new Runnable_Class());

	}
}

class Callable_Class implements Callable<String> {

	public String call() throws Exception {
		System.out.println(this + " has bean done");
		return null;
	}

}

class Runnable_Class implements Runnable {

	public void run() {

	}

}