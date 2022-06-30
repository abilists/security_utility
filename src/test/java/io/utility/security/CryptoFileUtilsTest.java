package io.utility.security;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CryptoFileUtilsTest {

	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is the first excuted");
	}

	@Before
	public void before() {
		System.out.println("Before");
	}

	@Test
	public void testOne() {

		String key = "tet@abilists.com";
		
		try {
			File inputFile = new File("test1.xlsx");
			File encryptedFile = new File("document.encrypted");
			File decryptedFile = new File("test2.xlsx");

			CryptoFileUtils cryptoFileUtils = new CryptoFileUtils();
			cryptoFileUtils.encryptFile(key, inputFile, encryptedFile);
			cryptoFileUtils.decryptFile(key, encryptedFile, decryptedFile);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("This is the test");
	}

	@After
	public void after() {
		System.out.println("Before");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("This is the end excuted");
	}

}
