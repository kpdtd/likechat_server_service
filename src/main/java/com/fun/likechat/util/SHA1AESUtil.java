package com.fun.likechat.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SHA1AESUtil {
	/**
	 * 将16进制转换为二进制 　　
	 * @param hexStr 　　
	 * @return 　　
	 */

	public static byte[] parseHexStr2Byte(String hexStr) {

		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制 　　
	 * @param buf 　　
	 * @return 　　
	 */

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/** 
	 * 加密 　　
	 * @param content 需要加密的内容 　　
	 * @param password 加密密码 　　
	 * @return 　　
	 */
	public static String encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			// kgen.init(128, new SecureRandom(password.getBytes()));
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			System.out.println("new AES Util");

			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			byte[] byteContent = content.getBytes("GBK");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);

			return parseByte2HexStr(result); // 加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解密 　　
	 * @param content 待解密内容 　　
	 * @param password 解密密钥 　　
	 * @return 　　
	 */
	public static String decrypt(String content, String password) {

		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(password.getBytes()));
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);

			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(parseHexStr2Byte(content));

			return new String(result);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 生成密钥
	 * @return
	 * @throws Exception
	 */
	public static String generateKey(String content) throws Exception{  
        //实例化  
        KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(content.getBytes());
        //设置密钥长度  
        kgen.init(128,random);  
        //生成密钥  
        SecretKey skey = kgen.generateKey();  
        //返回密钥的二进制编码  
        return parseByte2HexStr(skey.getEncoded());  
    }  
	
	/**
	 * 加密方法
	 * @param content 需要加密的内容
	 * @param password 加密的密钥
	 * @return
	 */
	public static String md5aessign(String content, String password) {

		MessageDigest md;
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			/*
			 * SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
			 * //需要自己手动设置   5.              random.setSeed(pwd.getBytes());   
			 * 6.              keyGen.init(128, random);
			 */
			// kgen.init(128, new SecureRandom(password.getBytes()));
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);

			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			// 准备MD5摘要算法
			byte[] byteContent = content.getBytes("GBK");

			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(byteContent);
			// 对md5摘要进行AES加密
			byte[] result = cipher.doFinal(md.digest());

			return parseByte2HexStr(result); // 转换为16进制字符串

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String [] args) throws Exception{
		String pwd = "Ym8174I6hGwIkI4t";
//		System.out.println("oupeng="+pwd);
		System.out.println(SHA1AESUtil.generateKey("aaaaa"));
		String content="082DE821DF09B1B5009D12B8EE5CC26E";
		String newContent = SHA1AESUtil.encrypt(content, pwd);
		System.out.println(newContent);
		System.out.println(SHA1AESUtil.decrypt(newContent, "Ym8174I6hGwIkI4t"));
	}
}
