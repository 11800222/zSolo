package jdk.object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Copying {

	public static void main(String[] args) throws Exception { //调用clone方法的类不实现Clonable时抛出
		Copying dw = new Copying();
		System.out.println(dw);
		System.out.println(dw.hashCode());
	}

	public static void Copy() throws CloneNotSupportedException, IOException {
		ArrayList<String> nestArrayList = new ArrayList<>();

		Copyable org = new Copyable("org");
		org.nestArrayList = nestArrayList;
		nestArrayList.add("under org");

		System.out.println("org.nestArrayList:    " + org.nestArrayList + "\n");
		//clone进行浅复制
		//		Copyable clone = (Copyable) org.clone();

		//序列化进行深复制
		byte[] orgByteForm = SerializeUtils.serialize(org);
		Copyable clone = (Copyable) SerializeUtils.deserialize(orgByteForm);

		org.nestArrayList.add("change");
		System.out.println("add \"change\" to " + "org.nestArrayList   " + "\n");

		System.out.println("clone.nestArrayList:   " + clone.nestArrayList);

	}

}

class Copyable implements Cloneable, Serializable {
	String name;

	ArrayList<String> nestArrayList;

	Copyable(String a) {
		name = a;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}

class Customized_Serializable implements Serializable { //自定义的序列化方式
	char c;
	long activationTime;

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeChar(c);
		System.out.println("session serialized");
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		c = ois.readChar();
		activationTime = System.currentTimeMillis();
		System.out.println("session deserialized");
	}
}
