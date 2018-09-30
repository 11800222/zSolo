package spring;

public class Aop {
	Aop AopAopProperty;
	Aop SecondAopProperty;

	public void Advice() {
		System.out.println("Advice procceed");
	}

	public Aop getAopAopProperty() {
		return AopAopProperty;
	}

	public void setAopAopProperty(Aop aopAopProperty) {
		AopAopProperty = aopAopProperty;
	}

	public Aop getSecondAopProperty() {
		return SecondAopProperty;
	}

	public void setSecondAopProperty(Aop secondAopProperty) {
		SecondAopProperty = secondAopProperty;
	}
}
