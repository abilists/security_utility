package io.utility.security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CipherUtilityTest {

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
			CipherUtility cipherUtility = new CipherUtility(key); // key 16 digit  tet@abilists.com
			String encrypted = cipherUtility.encrypt("TestTestTEsta");

			System.out.println("encrypted = " + encrypted);
			System.out.println("decrypted = " + cipherUtility.decrypt(encrypted));

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
