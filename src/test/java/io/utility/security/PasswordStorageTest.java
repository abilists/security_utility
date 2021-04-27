package io.utility.security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordStorageTest {

	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is the first excuted");
	}

	@Before
	public void before() {
		System.out.println("Before");
	}

	@Test
	public void testCreateHash() {

		try {
			String decrypt = PasswordStorage.createHash("Test12345");
			System.out.println("testCreateHash >>> " + decrypt);

			if(PasswordStorage.verifyPassword("Test12345", decrypt)) {
				System.out.println("true");	
			} else {
				System.out.println("false");
			}
			

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
