package io.utility.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

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

	public static String generateHashMd5(String value) {
		return generateToken(value, null);
	}

	public static String generateHashSha256(String value) {
		return generateToken(value, SHA_256);
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

	public static String hashHmacSha1(String value, String key) {
		try {
			byte[] hexBytes = Hex.encode(hashHmacSha1Raw(value, key));
			return new String(hexBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] hashHmacSha1Raw(String value, String key) {
		try {
			byte[] keyBytes = key.getBytes();           
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			return mac.doFinal(value.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}