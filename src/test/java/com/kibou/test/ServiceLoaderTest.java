package com.kibou.test;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.kibou.spi.IHello;

public class ServiceLoaderTest {
	public static void main(String[] args) {
		ServiceLoader<IHello> helloServiceLoader =  ServiceLoader.load(IHello.class);
		Iterator<IHello> iterator = helloServiceLoader.iterator();
		while(iterator.hasNext()){
			IHello helloService = iterator.next();
			helloService.sayHello("shijun");
		}
		
		ServiceLoader<Driver> driverServiceLoader = ServiceLoader.load(Driver.class);
		Iterator<Driver> drivers = driverServiceLoader.iterator();
		while(drivers.hasNext()){
			System.out.println(drivers.next());
		}
	}
}
