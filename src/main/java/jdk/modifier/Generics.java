package jdk.modifier;

import java.util.ArrayList;

/*  Difference between class and type？
 *       A class is a type. An interface is a type. A primitive is a type. An array is a type.Therefore, every type is also
 *  either a class (including an enum constant), an interface, a primitive, or an array.
 *       Every piece of data has a type which defines its structure, namely how much memory it takes up, how it is laid out, 
 *  and more importantly, how you can interact with it.
 */

public class Generics<E extends grandfather> {

	//编译前检查：是否存在非法转换
	public static void control() {
		//extends与super
		Generics<? extends father> l1 = new Generics<Son>();
		Generics<? super father> l2 = new Generics<grandfather>();

		//extends与super没有的情况下，严格匹配
		//		List<father> l3 = new ArrayList<Son>();
		//		List<father> l4 = new ArrayList<grandfather>();
		Generics<father> l5 = new Generics<father>(); //father只接收father 
	}

	//把方法声明成这样，编译会保证传入的e为E，引用返回的对象的也应为E
	public E get(E e) {
		//引用<Son>决定了l1的泛型（只是告诉编译器，运行期不管）
		Generics<Son> l1 = new Generics<Son>();

		Son Ason = new Son();

		//Ason能不能cast为Son（上面决定）传入，由编译保证。

		Son son = l1.get(Ason); // INVOKEVIRTUAL jdk/modifier/Generics.get (Ljdk/modifier/grandfather;)Ljdk/modifier/grandfather;

		//get方法返回的E（Son）对象能不能被Son引用，也是由编译保证。

		return null;
	}

	public static void boxing() {
		Generics<father> l5 = new Generics<father>();
		father obj = l5.get(new father());
	}

	//泛型不能作为重载条件。
	public static ArrayList<father> me(ArrayList<father> d) {
		return null;
	}

	//去掉_diff， IDE报错。 
	public static ArrayList<father> me_diff(ArrayList<Son> d) {
		return null;
	}

}

class grandfather {
	void grandfatherWork() {
	};
}

class father extends grandfather {

}

class Son extends father {

}