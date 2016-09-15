package com.kibou.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.kibou.domain.User;

public class KryoTest {

	@Test
	public void testSerialize() throws IOException{
		User user = User.create("shijun", 27);
		Kryo kryo = new Kryo();
		kryo.register(User.class);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
		Output output = new Output(baos,128);
		kryo.writeClassAndObject(output, user);
		
//		output.flush();
		output.close(); //XXX comment this and test 
		//1. 如果没有给Output指定 OutputStream的话,这里不需要flush最后可以通过output.toBytes()得到序列化后的字节数组
		//   使用底层自己的一个byte数组
		//2. 如果指定了OutputStream
		//  - 2.1 但是没有执行output的flush方法那么,序列话后的数据未flush到Outpustream中,所以baos.toByteArray()返回时空,而output.toBytes()是有数据的
		//      另外output.close同样也会执行flush方法,并关闭传入的outputstream
		//  - 2.2 执行了flush,output的当前数据就会刷新到底层传入的outputstream中
		
		byte[] bytes = output.toBytes();
		System.out.println(Arrays.toString(bytes));
		
		System.out.println(Arrays.toString(baos.toByteArray()));
//		System.out.println(outputStream);
//		User readObject = kryo.readObject(new Input(new ByteArrayInputStream(outputStream.toByteArray())), User.class);
//		System.out.println(readObject);
	}
}
