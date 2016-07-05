package com.kibou.spi;

public class HelloZh implements IHello{

	@Override
	public void sayHello(String name) {
		System.out.println("ÄãºÃ " + name);
	}
}
