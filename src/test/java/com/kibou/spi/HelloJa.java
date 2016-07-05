package com.kibou.spi;

public class HelloJa implements IHello{

	@Override
	public void sayHello(String name) {
		System.out.println("こんにちは " + name);
	}
}
