import java.io.IOException;
import java.net.ServerSocket;

public class Temp {

	public static void main(String args[]) throws IOException {
		ServerSocket ss = new ServerSocket(33112);
		System.out.println(1);
		ss.accept();
		System.out.println(2);
		System.out.println("stage");
	}

}
