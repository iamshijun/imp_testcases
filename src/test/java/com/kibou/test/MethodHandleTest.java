package com.kibou.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class MethodHandleTest {
	
	@Test
	public void invokeExact() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		String str = (String) mh.invokeExact("Hello world",1,3);
		Assert.assertEquals(str, "el");
	}
	
	@Test
	public void testAsCarargsCollector() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(void.class,String.class,int.class,int[].class);
		MethodHandle mh = lookup.findVirtual(VarArgs.class, "normalMethod", type);
		
		VarArgs varArgs = new VarArgs();
		mh.invoke(varArgs,"Hello",2,new int[]{3,4,5});
		
		//ת���ɿɱ�����ķ������
		mh = mh.asVarargsCollector(int[].class);
		mh.invoke(varArgs,"Hello again",2,3,4,5);
	}
	
	@Test
	public void testAsSpreader() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(void.class,String.class,int.class,int.class,int.class);
		MethodHandle mh = lookup.findVirtual(VarArgs.class, "toBeSpreaded", type);
		
		VarArgs varArgs = new VarArgs();
		
		mh = mh.asSpreader(int[].class, 3);
		mh.invoke(varArgs,"Hi",new int[]{3,4,5});
	}
	
	@Test
	public void testAsFixArity() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(void.class,String.class,int[].class);
		MethodHandle mh = lookup.findVirtual(VarArgs.class, "varargsMethod", type);
		
		VarArgs varArgs = new VarArgs();
		mh.invoke(varArgs,"Hi",3,4,5);
		
		mh = mh.asFixedArity();
		mh.invoke(varArgs,"Hi again",new int[]{3,4,5});
	}
	
	@Test
	public void testBindTo() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "length",MethodType.methodType(int.class));
		
		int len = (int)mh.invoke("Hello world");
		
		mh = mh.bindTo("Hello world");
		int len2 = (int) mh.invoke();
		
		Assert.assertEquals(len, len2);
	}
	
	//ͨ��invoker������exactInvoker�����õ��ķ����������Ϊ"Ԫ�������",���е���ita�������������
	@Test
	public void testInvoker() throws Throwable{
		MethodType typeInvoker = MethodType.methodType(String.class,Object.class,int.class,int.class);
		MethodHandle invoker = MethodHandles.invoker(typeInvoker);
		
		MethodType typeFind = MethodType.methodType(String.class,int.class,int.class);
		
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh1 = lookup.findVirtual(String.class, "substring", typeFind);
		MethodHandle mh2 = lookup.findVirtual(TestClass.class, "testMethod", typeFind);
		//
		String str = (String)invoker.invoke(mh1,"Hello",3,5);
		System.out.println(str);
		String str2 = (String) invoker.invoke(mh2,new TestClass(),1,2);
		System.out.println(str2);
	}
	
	@Test
	public void testInvokerTransform() throws Throwable{
		MethodType typeInvoker = MethodType.methodType(String.class,String.class,int.class,int.class);
		MethodHandle invoker = MethodHandles.invoker(typeInvoker);
		
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		
		invoker = MethodHandles.filterReturnValue(invoker, mhUpperCase);
		
		MethodType typeFind = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mh1 = lookup.findVirtual(String.class, "substring", typeFind);
		
		String result = (String) invoker.invoke(mh1,"Hello",1,4);
		Assert.assertEquals("ELL", result);
		
	}
	
	
	@Test
	public void testUseMethodHandleProxy() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(TestClass.class, "doSomething", MethodType.methodType(void.class));
		mh = mh.bindTo(new TestClass());
		//����һ��Runnable�ӿڵĴ��� ,��run����ִ�е�ʱ�����mh�������.
		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		Thread thread = new Thread(runnable);
		thread.start(); 	thread.join();
		//���� -- 1 ������Ľӿڱ����ǹ�����,2 �ӿ�ֻ�ܰ���һ������Ψһ�ķ���.. (���ƺ���@FunctionalInterface���Ĳ��)
		/// ͨ�����������ʵ�ֽӿڵ��������ڲ���Ҫ�½������Java��,ֻ��Ҫ�������еķ�������!!!
	}
	
	@Test // ������
	public void testUseSwitchPoint() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhMax = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhMin = lookup.findStatic(Math.class, "min", type);
		
		SwitchPoint sp = new SwitchPoint();
		MethodHandle mhNew = sp.guardWithTest(mhMin, mhMax);
		int val = (int) mhNew.invoke(3,4); // 3
		System.out.println(val);
		
		SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
		
		int val2 = (int) mhNew.invoke(3,4); // 4
		System.out.println(val2);
	}
	
	
	public static class VarArgs{
		public void normalMethod(String arg1,int arg2,int[] arg3){
			System.out.println(arg1 + ":" + arg2 + ":" + (arg3 != null ?Arrays.toString(arg3) : ""));
		}
		public void toBeSpreaded(String arg1,int arg2,int arg3,int arg4){
			System.out.println(arg1 + ":" + arg2 + ":" + arg3 + ":" + arg4);
		}
		public void varargsMethod(String arg1,int... args){
			System.out.println(arg1 + ":" + ":" + (args != null ?Arrays.toString(args) : ""));
		}
	}
	
	public static class TestClass{
		public String testMethod(int arg1,int arg2){
			return arg1 + "-" + arg2;
		}
		public void doSomething(){
			System.out.println("WORK");
		}
	}
	
	
	//private static final MethodType typeCallback = MethodType.methodType(Object.class,Object.class,int.class);
	
	public static void forEach(Object[] array,MethodHandle handle) throws Throwable{
		for (int i = 0 , len =  array.length; i < len; i++) {
			handle.invoke(array[i],i);
		}
	}
	
	public static Object[] map(Object[] array,MethodHandle handle) throws Throwable{
		Object[] result = new Object[array.length];
		for (int i = 0 , len =  array.length; i < len; i++) {
			result[i] = handle.invoke(array[i],i);
		}
		return result;
	}
	
	public static Object reduce(Object[] array,Object initValue,MethodHandle handle) throws Throwable{
		Object result = initValue;
		for (int i = 0 , len =  array.length; i < len; i++) {
			result =  handle.invoke(result,array[i]);
		}
		return result;
	}
	
	//�����Ŀ��ﻯcurrying
	public static MethodHandle curry(MethodHandle mh,int value){
		return MethodHandles.insertArguments(mh, 1, value);
	}
	
	public static int add(int a, int b){
		return a + b;
	}
	
	public static int add5(int a)throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhAdd = lookup.findStatic(MethodHandleTest.class, "add", type);
		mhAdd = curry(mhAdd, 5);
		return (int)mhAdd.invoke(a);
	}
	
	@Test
	public void testCurrying() throws Throwable{
		int ret = add5(4);
		System.out.println(ret);
	}
}
