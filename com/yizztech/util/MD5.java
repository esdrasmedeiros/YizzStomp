package com.yizztech.util;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5 {

	public static String toHashCode(String toHash) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(toHash.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}

	public static String toHashCode(FileInputStream toHash) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] dataBytes = new byte[1024];

		int nread = 0;
		while ((nread = toHash.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		;
		byte[] mdbytes = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
		
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		
		return sb.toString();
	}

}
