package jdk.compiler;

public class Boxing {

	public static void main(String[] args) {
		test();
	}

	public static void test() {
		//自动装箱（把字面量转变为对应类型）：相当于Integer a = Integer.valueOf()，有可能指向缓存。
		//自动拆箱（把类型转变为对应字面量）：相当于int a =integer.intValue(); 
		Integer ten = 10;
		Integer ten2 = 10;
		//没有自动拆箱为10，比较内存地址，指向同一个Integer对象（缓存）的内存地址true
		System.out.println(ten == ten2);//ALOAD 0、 ALOAD 1、IF_ACMPNE L3

		Integer five = 5;
		Integer five2 = 5;
		//==两边都被自动拆箱，因为有算术运算，true；
		System.out.println(ten == (five + five2));
		//10被自动装箱为Integer后传入equals（Object）；
		System.out.println(ten.equals(10));
		System.out.println(ten.equals(five + five2));//(five + five2)被自动拆箱计算出int后,再被。

		Long lten = 10L;
		System.out.println(lten.equals(five + five2));//自动装箱为Integer传入，以为Long不能和Integer比较（看下面equals实现），返回false

	}

	public boolean equals(Object obj) {
		if (obj instanceof Long) { //不能比较的数据类型直接返回false
			return value == ((Long) obj).longValue();
		}
		return false;
	}

	private long value;
}
