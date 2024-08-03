package com.empirestateids.main;

import com.empirestateids.common.SecurityUtil;

public class Testencryption {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String password = "Pass4Atlas";
	    String encryptedPass = SecurityUtil.encryptPassword(password);
	    System.out.println(encryptedPass);

	}

}
