package com.fun.likechat.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	private static final String VIPARA = "0102030405060708";
	private static final String ENCODING = "UTF-8";
	private static final int seed = 5108;

	private static byte[] getKey() {
		byte[] key = new byte[16];
		for (int i = 0; i < 16; i++) {
			key[i] = (byte) ((i + seed) * seed);
		}
		return key;
	}
	
	public static String encrypt(byte[] input) {
		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
		SecretKeySpec key = new SecretKeySpec(getKey(), "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的加解密算法不受支持: " + ALGORITHM);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的对齐算法不受支持: " + ALGORITHM);
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的秘钥有误: " + key.toString());
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的加解密算法参数有误: " + zeroIv.toString());
		}
		byte[] encryptedData = null;
		try {
			encryptedData = cipher.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("非法的数据块尺寸: " + e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("非法的数据对齐: " + e.getMessage());
		}
		return Base64Crypto.encode(encryptedData);
	}

	public static String encrypt(String cleartext) {
		try {
			return encrypt(cleartext.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的编码不受支持: " + ENCODING);
		}
	}
	
	/**
	 * 解密成byte数组
	 * @param encrypted - String
	 * @return byte[]
	 */
	public static byte[] decryptBytes(String encrypted) {
		byte[] byteMi = Base64Crypto.decode(encrypted);
		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
		SecretKeySpec key = new SecretKeySpec(getKey(), "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的加解密算法不受支持: " + ALGORITHM);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的对齐算法不受支持: " + ALGORITHM);
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的秘钥有误: " + key.toString());
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的加解密算法参数有误: " + zeroIv.toString());
		}
		byte[] decryptedData = null;
		try {
			decryptedData = cipher.doFinal(byteMi);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("非法的数据块尺寸: " + e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("非法的数据对齐: " + e.getMessage());
		}
		return decryptedData;
	}

	/**
	 * 解密成字符串
	 * @param encrypted - String
	 * @return String
	 */
	public static String decrypt(String encrypted) {
		byte[] decryptedData = decryptBytes(encrypted);
		try {
			return new String(decryptedData, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("指定的编码不受支持: " + ENCODING);
		}
	}
	
}
