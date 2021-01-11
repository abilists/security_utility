package io.utility.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class CipherUtility {

	private static final String CIPHER_PROVIDER = "BC";
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
	private static final String KEY_ALGORITHM = "AES";

	private String seedKey;

	private Cipher encrypter;
	private Cipher decrypter;

	public CipherUtility (String keyString) {
		this.seedKey = keyString;
		init();
	}

	public void init() {

		if (Security.getProvider(CIPHER_PROVIDER) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}

		try {
			// 2. the other way
			SecretKeySpec secretKey = new SecretKeySpec(this.seedKey.getBytes(), KEY_ALGORITHM);

			encrypter = Cipher.getInstance(CIPHER_ALGORITHM, CIPHER_PROVIDER);
			encrypter.init(Cipher.ENCRYPT_MODE, secretKey);
	
			decrypter = Cipher.getInstance(CIPHER_ALGORITHM, CIPHER_PROVIDER);
			decrypter.init(Cipher.DECRYPT_MODE, secretKey);

		} catch (Exception e) {
			System.err.println("Caught an exception:" + e);
			throw new AssertionError(e);
		}

	}

	public String encrypt(String normalData) throws Exception {

		 if (normalData == null) {
			return null;
		}

		byte[] encryptedData = new byte[1024];
		try {
			encryptedData = encrypter.doFinal(normalData.getBytes());
		} catch (Exception e) {
			throw new Exception(e);
		}

		return new String(Base64.encode(encryptedData));
	}

	public String decrypt(String encryptedData) throws Exception {
		if (encryptedData == null) {
			return null;
		}

		byte[] decryptedData = Base64.decode(encryptedData);
		try {
			return new String(decrypter.doFinal(decryptedData));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public void encryptFile(InputStream isSource, File dest) throws Exception {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = isSource;
            output = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                output.write(encrypter.update(buffer, 0, read));
            }
            output.write(encrypter.doFinal());
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

	public void encryptFile(File source, File dest) throws Exception {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            output = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                output.write(encrypter.update(buffer, 0, read));
            }
            output.write(encrypter.doFinal());
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

	public byte[] decryptFile(String fullFilePath) throws Exception {

		File file = new File(fullFilePath);
		if (!file.exists()) {
			return null;
		}

		// Set length of file
		long fileLength = file.length();

		// Create the byte array to hold the data
		byte[] byteOut = new byte[(int) fileLength];
		int read = -1;
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			while ((read = input.read(byteOut)) != -1) {
				byteOut = decrypter.update(byteOut, 0, read);
			}
			decrypter.doFinal();
		} catch (IOException e) {
			throw e;
		}

		return byteOut;
	}

	public void decryptFile(File source, File dest) throws Exception {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            output = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                output.write(decrypter.update(buffer, 0, read));
            }
            output.write(decrypter.doFinal());
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

	public Cipher getEncrypter() {
		return encrypter;
	}

	public Cipher getDecrypter() {
		return decrypter;
	}

	public void setSeedKey(String seedKey) {
		this.seedKey = seedKey;
	}

}