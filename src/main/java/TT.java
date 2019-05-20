import java.util.concurrent.locks.LockSupport;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.Data;
import lombok.ToString;

public class TT {

	public static void main(String[] args) throws InterruptedException {

		final Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					synchronized (TT.class) {
						LockSupport.park();

					}
					System.out.println(22);
				} catch (Exception e) {
					System.out.println("interupted");
					e.printStackTrace();
				}
			}
		});
		t.start();
		Thread.sleep(2000);
		System.out.println("-+" + t.getState());
		t.interrupt();
		Thread.sleep(2000);
		System.out.println("-+" + t.getState());
	}

	public static void temp() {
		synchronized (TT.class) {

		}
	}
}


