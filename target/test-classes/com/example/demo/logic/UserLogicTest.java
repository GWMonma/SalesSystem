package com.example.demo.logic;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

class UserLogicTest {
	@Rule
	UserLogic userlogic =  new UserLogic();

	@Test
	void testLogout() {
		userlogic.logout();


	}

}
