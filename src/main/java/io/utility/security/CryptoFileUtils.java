package io.utility.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.CryptoException;

public class CryptoFileUtils {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
	
	private static String seedKey;

	public CryptoFileUtils (String keyString) {
		CryptoFileUtils.seedKey = keyString;
	}

	public static byte[] encryptByte(String fullFilePath) throws Exception {
		return doCrypto(Cipher.ENCRYPT_MODE, fullFilePath);
	}

	public static byte[] decryptByte(String fullFilePath) throws Exception {
		return doCrypto(Cipher.DECRYPT_MODE, fullFilePath);
	}

	public static void encryptFile(InputStream inputIs, File outputFile) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, inputIs, outputFile);
	}

	public static void decryptFile(InputStream inputIs, File outputFile) throws Exception {
		doCrypto(Cipher.DECRYPT_MODE, inputIs, outputFile);
	}

	public static void encryptFile(File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
	}

	public static void decryptFile(File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
	}

	private static byte[] doCrypto(int cipherMode, String fullFilePath) throws Exception {

		File file = new File(fullFilePath);
		if (!file.exists()) {
			return null;
		}

		Key secretKey = new SecretKeySpec(CryptoFileUtils.seedKey.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
		cipher.init(cipherMode, secretKey);

		// Set length of file
		long fileLength = file.length();

		// Create the byte array to hold the data
		byte[] byteOut = new byte[(int) fileLength];
		int read = -1;
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			while ((read = input.read(byteOut)) != -1) {
				byteOut = cipher.update(byteOut, 0, read);
			}
			cipher.doFinal();
		} catch (IOException e) {
			throw e;
		}

		return byteOut;
	}

	private static void doCrypto(int cipherMode, InputStream input, File outputFile) throws Exception {

        OutputStream output = null;
        try {
    		
    		Key secretKey = new SecretKeySpec(CryptoFileUtils.seedKey.getBytes(), ALGORITHM);
    		Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
    		cipher.init(cipherMode, secretKey);

            output = new BufferedOutputStream(new FileOutputStream(outputFile));
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                output.write(cipher.update(buffer, 0, read));
            }
            output.write(cipher.doFinal());
		} catch (Exception e) {
			throw new Exception(e);
        } finally {
            if (output != null) {
            	output.close();
            }
            if (input != null) {
            	input.close();
            }
        }

	}

	private static void doCrypto(int cipherMode, File inputFile, File outputFile) throws Exception {

        InputStream input = null;
        OutputStream output = null;
        try {
    		
    		Key secretKey = new SecretKeySpec(CryptoFileUtils.seedKey.getBytes(), ALGORITHM);
    		Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
    		cipher.init(cipherMode, secretKey);

            input = new BufferedInputStream(new FileInputStream(inputFile));
            output = new BufferedOutputStream(new FileOutputStream(outputFile));
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                output.write(cipher.update(buffer, 0, read));
            }
            output.write(cipher.doFinal());
		} catch (Exception e) {
			throw new Exception(e);
        } finally {
            if (output != null) {
            	output.close();
            }
            if (input != null) {
            	input.close();
            }
        }

	}

	/**
	 * This is a sample code for backup
	 * @param cipherMode
	 * @param key
	 * @param inputFile
	 * @param outputFile
	 * @throws CryptoException
	 */
	private static void doCrypto2(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {

		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			byte[] outputBytes = cipher.doFinal(inputBytes);
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);
			inputStream.close();
			outputStream.close();
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | 
				BadPaddingException | IllegalBlockSizeException | IOException ex) {
			throw new CryptoException("Error encrypting/decrypting file");
		}

	}

	public void setSeedKey(String seedKey) {
		this.seedKey = seedKey;
	}

}
