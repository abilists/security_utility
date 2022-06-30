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
	
	public static void encryptFile(String key, File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	public static void decryptFile(String key, File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}

	private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws Exception {

        InputStream input = null;
        OutputStream output = null;
        try {
    		
    		Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
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

}
