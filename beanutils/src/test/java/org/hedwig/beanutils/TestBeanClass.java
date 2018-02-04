package org.hedwig.beanutils;

import java.util.Date;

public class TestBeanClass {
	Date a = new Date();
	String b = "abcde";

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}
}
