package jdk.modifier;

public class Final {
	public static void main() {
		final Object outerfinalObj = new Object();
		final Object outerfinalObj2 = new Object();

		Thread t = new Thread(new Runnable() {// jdk.modifier.Final$1(java.lang.Object, java.lang.Object); 在编译该内部类时多了个构造函数，传入用到的final对象
			public void run() {
				outerfinalObj.hashCode();
				outerfinalObj2.hashCode();
			}
		});
	}

	//方法一带有final修饰
	public void foo(final int arg) {
		final int var = 0;
		//do something
	}

	//方法二没有final修饰
	public void foo2(int arg) {
		int var = 0;
		//do something
	}
	/* 在这个方法中，第一个方法在代码编写时程序肯定受到final修饰符的影响，但这两段代码编译出来的Class文件是没有任何区别的，因为局部变量是在常量池中是没有CONSTANT_Fieldref_info表的，
	 自然就没有访问标志（Access_Flags）的信息，甚至可能连名称(CONSTANT_Utf8_info ）都不会保留下来（取决于编译时的选项）.*/
}
