package io.utility.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.bouncycastle.crypto.CryptoException;

public class CipherCompressUtility {
	
	private static final int DEFAULT_VALUE = 6;

	private static CipherUtility cipherUtility = null;

	public static byte[] encryptCompress(String data, String cutomKey) 
			throws CryptoException, Exception {
		return encryptCompress(data, cutomKey, DEFAULT_VALUE);
	}

	public static byte[] encryptCompress(String data, String cutomKey, final int compressLevel) 
			throws CryptoException, Exception {

		ByteArrayOutputStream baOs = null;
		GZIPOutputStream gzOs = null;
		ByteArrayInputStream baIs = null;
		CipherOutputStream cos = null;

		try{
			if (cipherUtility == null) {
				cipherUtility = new CipherUtility(cutomKey);				
			}

			Cipher cipher = cipherUtility.getEncrypter();

			baIs = new ByteArrayInputStream(data.getBytes("UTF-8"));
			baOs = new ByteArrayOutputStream(data.length());
			cos = new CipherOutputStream(baOs, cipher);
			gzOs = new GZIPOutputStream(cos) {
					{def.setLevel(compressLevel);}
				};

			byte[] bf = new byte[1024];
			int len = 0;

			while((len = baIs.read(bf, 0, bf.length)) != -1){
				gzOs.write(bf, 0, len);
			}
			if(gzOs != null){
				gzOs.close();
			}

			return baOs.toByteArray();
		}catch (IOException e) {
			throw e;
		}finally{
			if(cos != null){
				cos.close();
			}
			if(baOs != null) {
				baOs.close();
			}
			if(baIs != null){
				baIs.close();
			}
		}

	}

	public static byte[] decompressDecrypt(byte[] data, String cutomKey) throws CryptoException, Exception {

		ByteArrayInputStream baIs = null;
		GZIPInputStream gzIs = null;
		ByteArrayOutputStream baOs = null;
		CipherInputStream cIs = null;

		try{
			if (cipherUtility == null) {
				cipherUtility = new CipherUtility(cutomKey);				
			}

			Cipher cipher = cipherUtility.getDecrypter();

			baIs = new ByteArrayInputStream(data);
			cIs = new CipherInputStream(baIs, cipher);
			gzIs = new GZIPInputStream(cIs);
			baOs = new ByteArrayOutputStream();

			byte[] bf = new byte[1024];
			int len = 0;

			while((len = gzIs.read(bf, 0, bf.length)) != -1){
				baOs.write(bf, 0, len);
			}

			return baOs.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			if(baOs != null){
				baOs.close();
			}
			if(gzIs != null){
				gzIs.close();
			}
			if(cIs != null) {
				cIs.close();
			}
			if(baIs != null){
				baIs.close();
			}
		}

	}

}
