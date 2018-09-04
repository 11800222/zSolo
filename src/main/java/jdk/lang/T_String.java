package jdk.lang;

public class T_String {
	public static void main(String ad[]) {
		Integer i = 1;
		Integer i2 = 1;
		System.out.println(i == i2);

		Integer a = new Integer(1);
		Integer a2 = new Integer(1);
		System.out.println(a == a2);

	}

	public static void test1() {
		String s1 = "abc";//如果常量池没有"abc"，只在字符串池创建了一个字符串对象 

		String s2 = "abc";//↑ 字符串pool已经存在对象“abc”(共享),s2指向它，创建0个对象，累计创建一个对象 

		System.out.println(s1 == s2); //验证可知指向相同的内存(常量池)地址

	}
}
