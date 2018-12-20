package jdk.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T_ReentrantLock {
	static int a = 3;
	static ReentrantLock re = new ReentrantLock();

	public static void main(String[] args) {
		Re_entrant();
	}

	public static void Re_entrant() {
		re.lock();
		if (a != 0) {
			a--;
			Re_entrant();
		}
		System.out.println("ends");
	}

	public static void RW_Lock() {
		ReentrantReadWriteLock re = new ReentrantReadWriteLock();
		re.readLock();
		re.writeLock();
	}
}
