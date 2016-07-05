package com.kibou.spi;

public class HelloEn implements IHello{

	@Override
	public void sayHello(String name) {
		System.out.println("Hello " + name);
	}
}
