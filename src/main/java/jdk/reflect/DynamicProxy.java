/*******************************************************************************
 * Project Key : 
 * Create on 2018年8月3日 上午9:58:34
 * Copyright (c) 2004 - 2014. 拉卡拉支付有限公司版权所有. 京ICP备12007612号
 * 注意：本内容仅限于拉卡拉支付有限公司内部传阅，禁止外泄以及用于其他的商业目的
 ******************************************************************************/
package jdk.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <P>TODO</P>
 * @author 陈冠达   2018年8月3日 上午9:58:34  
 */
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
}

class BeingProxyed implements jdkProxyNeedInterface {
	public void doWhatever() {
		System.out.println("Doing Whatever and ends");
	}

}

interface jdkProxyNeedInterface {
	void doWhatever();
}