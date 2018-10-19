
package jdk.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
	public static void main(String s[]) {
		//被代理的对象
		BeingProxyed beingProxyed = new BeingProxyed();
		//代理逻辑对象。假设代理逻辑需要被代理对象，传入被代理对象
		Proxyer proxyer = new Proxyer(beingProxyed);

		//可以看到需要传入被代理类的接口、被代理类的类加载器、代理逻辑对象 
		jdkProxyNeedInterface Proxyed = (jdkProxyNeedInterface) Proxy.newProxyInstance(beingProxyed.getClass().getClassLoader(), beingProxyed.getClass().getInterfaces(), proxyer);

		Proxyed.doWhatever();
	}
}

class Proxyer implements InvocationHandler {
	BeingProxyed beingProxyed;

	//   Advice advice;    
	public Proxyer(BeingProxyed beingProxyed) {
		this.beingProxyed = beingProxyed;
	}

	//回调函数，调用运行时创建的代理实例对象的代理方法时会调用回这个函数
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			//	advice.before();
			System.out.println("——————————before doing Whatever——————————");
			method.invoke(beingProxyed, args); //beingProxyed.method(args);
			System.out.println("——————————after doing Whatever——————————");
			//	advice.after(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//this调用导致aop失效
	public void thisInvoke() throws Throwable {
		invoke(null, null, null);
		this.invoke(null, null, null); //同上
	}
}

class BeingProxyed implements jdkProxyNeedInterface {
	public void doWhatever() {
		System.out.println("Doing Whatever and ends");
	}

}

interface jdkProxyNeedInterface {
	void doWhatever();
}