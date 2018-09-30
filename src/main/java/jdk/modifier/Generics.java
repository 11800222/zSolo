package jdk.modifier;

import java.util.ArrayList;
import java.util.Collection;

/*  Difference between class and type？
 *       A class is a type. An interface is a type. A primitive is a type. An array is a type.Therefore, every type is also
 *  either a class (including an enum constant), an interface, a primitive, or an array.
 *       Every piece of data has a type which defines its structure, namely how much memory it takes up, how it is laid out, 
 *  and more importantly, how you can interact with it.
 */
//泛型是一中参数化类型的实现，泛型有泛型类和泛型方法，把类型作为参数声明泛型类的引用，可以让编译器检查该引用在调用泛型方法时入参的类型 ，
//来避免使用cast，减少运行期异常。
//	(Generics<?> == Generics<? extends Object>)
public class Generics<E> {

	public static void main(String wdw[]) {

	}

	//泛型对象间赋值
	public static void temp() {
		//关于new。。。
		Generics o = new Generics<?>();
		Generics o2 = new Generics<? extends Object>(); // 不是类型不匹配，而是不能new <？>()
		Generics extend = new Generics<Father>(); //只能new <certain>()

		Generics<? super Collection> super1 = new Generics();
		Generics<Collection> Collection = new Generics();
		Generics<? extends Collection> extends1 = new Generics();

		super1 = Collection;
		extends1 = Collection; //符合符号的意思

		Generics<Father> just_Father = new Generics<>();
		Generics<Son> just_Son = new Generics<>();

		just_Son = just_Father;
		just_Father = just_Son; // 严格匹配，即使其中的泛型类型是继承关系（Father、Son）也不能传递，为了解决这个，推出了通配符（？）
	}

	//不指定类型；
	public static void unCertain() {
		Generics no = new Generics<>();
		no.E_method(new Object());
		no.E_method(new String());
		Object o0 = no.E_return();
		Grandfather g0 = no.E_return();//泛型方法入参和返回值：简单用Object代替；

		Generics<Father> just_Father = new Generics<>();
		just_Father = no;
		no = just_Father;//泛型对象间赋值,没有限制，意味着不需要编译器做检查
	}

	//指定确定的类型： 
	public static void certain() {
		Generics<Father> just_Father = new Generics<>();
		Generics<Son> just_Son = new Generics<>();
		//		just_Father.E_method(new Object()); 
		just_Father.E_method(new Father());
		just_Father.E_method(new Son());
		Object o = just_Father.E_return();
		Grandfather g = just_Father.E_return();
		Father f = just_Father.E_return();//泛型方法入参和返回值：简单用泛型类型（Father）代替；

	}

	//通配符？与extends；严进宽出 
	public static void extend() {
		Generics<? extends Father> extend = new Generics<>();
		extend.E_method(new Object());
		extend.E_method(new Grandfather());
		extend.E_method(new Father());
		extend.E_method(new Son());
		extend.E_method(null); //泛型方法入参并不是(? extends Father)，被限定只能传入null ；

		Object o2 = extend.E_return();
		Grandfather g2 = extend.E_return();
		Father f2 = extend.E_return();
		Son s = extend.E_return(); //泛型方法返回也不是(? extends Father)，而是Father；      
	}

	//通配符？与super；宽进严出
	public static void super_() {
		Generics<? super Father> supers = new Generics<>();
		supers.E_method(new Object());
		supers.E_method(new Grandfather());
		supers.E_method(new Father());
		supers.E_method(new Son());
		supers.E_method(null);//泛型方法入参是(? extends Father)

		Object o1 = supers.E_return();
		Grandfather g1 = supers.E_return();
		Father f1 = supers.E_return();
		Son s1 = supers.E_return();//泛型方法返回也不是(? super Father),只能是Object         

	}

	//把方法声明成这样，编译会保证传入的e为E，引用返回的对象的也应为E
	public E E_method(E e) {
		//引用<Son>决定了l1的泛型（ 编译器负责检查）
		Generics<Son> l1 = new Generics<Son>();

		Son Ason = new Son();

		//Ason能不能cast为Son（上面决定）传入，由编译保证。

		Son son = l1.E_method(Ason); //编译后该方面与Son无关： INVOKEVIRTUAL jdk/modifier/Generics.get (Ljdk/modifier/grandfather;)Ljdk/modifier/grandfather;

		return null;
	}

	public E E_return() {
		return null;
	}

	//非泛型类，但需要泛型的方法
	public static <T> T getT(Generics<T> type) {
		Object obj = new Object();
		return (T) obj;
	}

	//泛型不能作为重载条件。
	public static ArrayList<Father> me(ArrayList<Father> d) {
		return null;
	}

	//去掉_diff， IDE报错。 
	public static ArrayList<Father> me_diff(ArrayList<Son> d) {
		return null;
	}

}

class Grandfather {
	void grandfatherWork() {
	};
}

class Father extends Grandfather {

}

class Son extends Father {

}