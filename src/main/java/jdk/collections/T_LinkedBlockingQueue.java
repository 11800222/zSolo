package jdk.collections;

public class T_LinkedBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		Ww();
	}

	public static String Ww() {
		int a = 1;
		retry: for (;;) {
			for (;;) {

				if (a == -1)
					break;
				++a;
				if (a == 3)
					continue retry;
				System.out.println(3);
			}
			System.out.println(3);
		}

	}
}
