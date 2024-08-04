package com.empirestateids.Icd10;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.empirestateids.common.SecurityUtil;


@SpringBootTest
class Icd10ApplicationTests {

	@Test
	void TestEncryption() {
		String password = "Pass4Atlas";
	    String encryptedPass = SecurityUtil.encryptPassword(password);
	    assertNotNull(encryptedPass);
	}
	

}
