
package util;

import java.util.Scanner;

public class ObjectUtil {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String wd[]) {
		while (true) {
			String option = sc.nextLine();
			switch (option) {
			case "1": {
				System.out.println(1);
				break;
			}
			case "2": {
				System.out.println(2);
				break;
			}
			case "3": {
				System.out.println(3);
				break;
			}
			case "4": {
				System.out.println(4);
				break;
			}
			}
		}
	}

	public static String getObjectId(Object o) {
		String org = o.toString();
		return org.substring(org.length() - 4);
	}
}
