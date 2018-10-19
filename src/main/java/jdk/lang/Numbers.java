
package jdk.lang;

public class Numbers {

	public static void main(String[] args) {
		System.out.println(2f);
		System.out.println(Integer.toBinaryString(Float.floatToIntBits(7f)));
		System.out.println(Integer.toBinaryString(Float.floatToIntBits(16777214f)));
		System.out.println(Integer.toBinaryString(Float.floatToIntBits(16777215f)));
		System.out.println(Integer.toBinaryString(Float.floatToIntBits(16777216f)));

	}

	public static void IntBit() {
		int a = 1;
		System.out.println(Integer.toHexString(a));
		System.out.println(Integer.toBinaryString(a));

		int b = -1;
		System.out.println(Integer.toHexString(b));
		System.out.println(Integer.toBinaryString(b));
	}

	/**
	 * Bit 31   represents the sign of the floating-point number.
	 * Bits 30-23 represent the exponent.
	 * Bits 22-0  represent the significand (sometimes called the mantissa) of the floating-point number.
	 */
	//可以看成是24位(31+（22~0）)的int乘以一个指数,那么int部分的范围为（正数部分）0~2^24-1 = 0~16777216-1；
	public static void floatBit() {
		float one = (float) 1;
		float a = (float) 16777215;
		float c = (float) 16777216; //注意最后一位数 

		System.out.println(a);
		System.out.println(a == (a + one));
		System.out.println(a == c);
		System.out.println("取负数后看其二进制");

		String temp = Integer.toBinaryString(Float.floatToIntBits(-a));
		for (int i = temp.length() - 1; i >= 0; --i)
			System.out.print((i) % 10 + " ");
		System.out.println(" ");
		System.out.println(temp);
		for (int i = 0; i < temp.length(); ++i)
			System.out.print(temp.charAt(i) + " ");

	}
}
