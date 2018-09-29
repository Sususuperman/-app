package com.cs.android.util;


/**
 * 加密解密工具类
 * @author james
 *
 */
public abstract class Crypto
{
    static
    {
        System.loadLibrary("cscrypto");
    }
	/**
	 * 加密一个文件
	 * @param src	原文件
	 * @param des	目标文件
	 * @param passwd	密码
	 * @return	1为加密成功，0为加密失败
	 */
	public static native boolean encryptFile(String src, String des, byte[] passwd);

	/**
	 * 解密一个文件
	 * @param src	原文件，注意这个应该是上面的方法生成的文件
	 * @param des	目标文件
	 * @param passwd	密码
	 * @return	1为解密成功，0为解密失败
	 */
	public static native boolean decryptFile(String src, String des, byte[] passwd);
	
	/**
	 * 加密一个字符串
	 * @param src	要加密的字符串
	 * @param passwd	密码
	 * @return	加密后经过base64编码的字符串，失败则为NULL
	 */
	public static native String encryptString(String src, byte[] passwd);
	
	/**
	 * 解密一个字符串
	 * @param src	要解密的字符串，注意这个应该是上面的方法生成的字符串
	 * @param passwd	密码
	 * @return	解密后的字符串，失败则为NULL
	 */
	public static native String decryptString(String src, byte[] passwd);
	
	/**
	 * 对字符串进行散列运算
	 * @param src，要处理的字符串
	 * @return	结果散列处理后进行base64编码的字符串，失败则为NULL
	 */
	public static native String digest(String src);

}
