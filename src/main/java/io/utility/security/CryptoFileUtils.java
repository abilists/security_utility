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

	public static byte[] encryptByte(String seedkey, String fullFilePath) throws Exception {
		return doCrypto(Cipher.ENCRYPT_MODE, seedkey, fullFilePath);
	}

	public static byte[] decryptByte(String seedkey, String fullFilePath) throws Exception {
		return doCrypto(Cipher.DECRYPT_MODE, seedkey, fullFilePath);
	}

	public static void encryptFile(String seedkey, InputStream inputIs, File outputFile) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, seedkey, inputIs, outputFile);
	}

	public static void decryptFile(String seedkey, InputStream inputIs, File outputFile) throws Exception {
		doCrypto(Cipher.DECRYPT_MODE, seedkey, inputIs, outputFile);
	}

	public static void encryptFile(String seedkey, File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.ENCRYPT_MODE, seedkey, inputFile, outputFile);
	}

	public static void decryptFile(String seedkey, File inputFile, File outputFile) throws Exception {
		doCrypto(Cipher.DECRYPT_MODE, seedkey, inputFile, outputFile);
	}

	private static byte[] doCrypto(int cipherMode, String seedkey, String fullFilePath) throws Exception {

		File file = new File(fullFilePath);
		if (!file.exists()) {
			return null;
		}

		Key secretKey = new SecretKeySpec(seedkey.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
		cipher.init(cipherMode, secretKey);

		byte[] byteOut;
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			byte[] inputBytes = new byte[(int) file.length()];
			input.read(inputBytes);
			byteOut = cipher.doFinal(inputBytes);
		} catch (IOException e) {
			throw new IOException(e);
		}

		return byteOut;
	}

	private static void doCrypto(int cipherMode, String seedkey, InputStream input, File outputFile) throws Exception {

        OutputStream output = null;
        try {
    		
    		Key secretKey = new SecretKeySpec(seedkey.getBytes(), ALGORITHM);
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

	private static void doCrypto(int cipherMode, String seedkey, File inputFile, File outputFile) throws Exception {

        InputStream input = null;
        OutputStream output = null;
        try {
    		
    		Key secretKey = new SecretKeySpec(seedkey.getBytes(), ALGORITHM);
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
