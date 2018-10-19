
package jdk.object;

public class mutil_Thread {
	public static Object lockA = new Object();
	public static Object lockB = new Object();

	public static void main(String[] args) {
		try {
			lockA.wait();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println(2);
	}

}

class Run_holder implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}