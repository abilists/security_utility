package io.utility.security;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TokenUtilityTest {

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

		try {
			String token = TokenUtility.generateToken(TokenUtility.SHA_256);
			System.out.println(">>>" + token);

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
