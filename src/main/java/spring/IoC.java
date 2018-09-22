package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IoC implements ApplicationContextAware {
	static ApplicationContext context;
	public IoC ioC;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static void main(String[] dw) {
		FileSystemXmlApplicationContext F = new FileSystemXmlApplicationContext("temp");

	}

	public static void refresh() {//ApplicationContext抽象了这一过程
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();// 

		ClassPathResource res = new ClassPathResource("applicationContext.xml");//定位Bean资源文件

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);//创建BeanDefinition读取器Reader（XML）
		reader.loadBeanDefinitions(res);//把Resource载入并注册到factory

		System.out.println(factory.getBean("TdSystemLogMapper"));
	}

	@Test
	public void FactoryBean() {
		Object mapper = context.getBean("TdSystemLogMapper");
		System.out.println(mapper.getClass());//com.sun.proxy.$Proxy16

		mapper = context.getBean("&TdSystemLogMapper");
		System.out.println(mapper.getClass());//org.mybatis.spring.mapper.MapperFactoryBean
	}

	@Test
	public void ConcurrencyGetBean() {
		try {
			Object obj = context.getBean("TestBean3");
			System.out.println(obj.getClass());

			/*	Object obj2 = context.getBean("TestBean3");
				System.out.println(obj.getClass());*/
		} catch (BeansException e) {

			e.printStackTrace();
		}
	}

	public IoC getIoC() {

		return ioC;
	}

	public void setIoC(IoC ioC) {
		this.ioC = ioC;
	}

}
