public class Temp {

	public static void main(String[] args) {
		temp();

	}

	public static void temp() {
		father f = new father();
		f.sus();
	}

}

class father {
	protected void sus() {
		System.err.println("ama");
	}
}