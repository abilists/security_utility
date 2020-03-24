package io.utility.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TokenUtility {

	public static String SHA_256 = "SHA-256";
	public static String MD5 = "MD5";

	public static String getUuId() {
		StringBuffer sb = new StringBuffer();
		sb.append(UUID.randomUUID().toString().toUpperCase())
		  .append(System.currentTimeMillis());

		return sb.toString();
	}

	public static String generateToken() {
		return generateToken(getUuId(), MD5);
	}

	public static String generateToken(String algorithm) {
		return generateToken(getUuId(), algorithm);
	}

	public static String generateToken(String value, String algorithm) {

		// The default is SHA-256
		if(algorithm == null) {
			algorithm = MD5;
		}

		StringBuffer sb = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(value.getBytes());
			byte [] byteData = md.digest();

			//convert the byte to hex format method 1
			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString(
						(byteData[i] & 0xff) + 0x100, 16).substring(1));
		    }

		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return sb.toString();
	}

}