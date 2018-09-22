package jdk.compiler;

public class Outer {
	private String s1 = "this is s1 in Outer";
	private String s_outer = "this is s_outer in Outer";

	private String method1() {
		return "this is method1 in Outer";
	}

	private String method2() {
		return "this is method2 in Outer";
	}

	public void method3() {
		final String s_method = "this is s_method in method3";
		class Inner {
			private String s1 = "this is s1 in Inner";

			public void method1() {
				// 内部类访问外部方法的变量，需要有final修饰
				System.out.println(s_method);
				// 局部内部类可直接访问外部类的变量，即使是私有的
				System.out.println(s_outer);
				// 内部类和外部类有同名变量和方法时
				System.out.println(s1);
				System.out.println(Outer.this.s1);
				System.out.println(method2());
				System.out.println(Outer.this.method2());
			}

			private String method2() {
				return "this is method2 in Inner";
			}
		}
		Inner inner = new Inner();
		inner.method1();
	}
}
