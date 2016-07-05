package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author lenovo
 * 
 */
public class MD5Util {

	public static String encrypt(byte[] salt, byte[] encrypted) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			// 将salt和要加密的明文一起放进digest中
			digest.update(salt);
			digest.update(encrypted);
			// 最后使用digest() 返回加密后的结果!
			byte[] s = digest.digest();
			// 将得到的最终结果 byte[] 转换成 hex 十六进制的形式(string)
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0xff & s[i]) | 0xffffff00) // 这里最后与0xffffff00(得到一个负数值)主要是为了往前24位补点东西
						// 不然的话toHexString对于前面的开头的连续位的0都会被省略掉 ,其实这里可以使用
						// 0xf?????00,0xe??????00,0x8??????00.. 只要是最高位为1即可
						.substring(6); // 因为有效的只是最后的两位罢了
			}

			// System.out.println(result);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encrypt0(byte[] salt, byte[] encrypted) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			digest.update(salt);
			digest.update(encrypted);
			byte[] bs = digest.digest();
			String result = "";
			for (byte b : bs) {
				result += digit(b, 2);
			}

			// System.out.println(result);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 该方法是参考 {@link java.util.UUID}的digit方法
	 * 
	 * @param val
	 * @param digit
	 *            可以认为你当前想显示多少位16进制字符
	 * @return
	 */
	public static String digit(byte val, int digit) {
		int hi = 1 << (digit * 4);
		return Integer.toHexString(hi | (val & (hi - 1))).substring(1);
		// 个人感觉这里的 val & (hi-1)没有必要 这里一定会等于val的 !right?
	}

	public static String encrypt(Object encrypted) {
		if (encrypted == null) {
			throw new NullPointerException("encryted data give null");
		}
		Random rand = new Random();
		byte[] salt = new byte[12];
		rand.nextBytes(salt);// 产生一个随机的byte[]作为salt (test!)
		return encrypt(salt, encrypted.toString().getBytes());
	}

	public static void main(String[] args) {
		// byte b = 4;
		// System.out.println(digit(b, 2));

		byte[] salt = "reina".getBytes();
		byte[] encrypted = "shijun".getBytes();

		String e0 = encrypt0(salt, encrypted);
		String e1 = encrypt(salt, encrypted);

		System.out.println(e0);
		System.out.println(e1);
	}
}
