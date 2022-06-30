package io.utility.security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CipherCompressUtilityTest {

	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is the first excuted");
	}

	@Before
	public void before() {
		System.out.println("Before");
	}

	// @Test
	public void testOne() {

		String key = "tet@abilists.com";

		try {
//			CipherCompressUtility ccu = new CipherCompressUtility();
//			byte[] encrypted = ccu.encryptCompress("data", key);
//			ccu.decompressDecrypt(encrypted, key);

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
