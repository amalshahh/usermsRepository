package com.infy.Login.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommonConstantMethods {

	@Autowired
	public JwtUtil jwtUtil;

	public boolean authTokenValidator(String auth, String requestEmail) {
		boolean status = false;
		if (auth != null & auth.startsWith("Bearer ")) {
			String jwt = auth.substring(7); // Remove "Bearer " prefix
			String emailFromToken = jwtUtil.extractUsername(jwt);
			String requestUsername = requestEmail.split("@")[0];
			String usernameFromToken = requestEmail.split("@")[0];

			if (requestEmail.equalsIgnoreCase(emailFromToken) || usernameFromToken.equalsIgnoreCase(requestUsername)) {
				status = true;
				return status;
			}
		}
		return status;
	}

	public static boolean validatePassword(String rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}

	public static boolean userIdExtractor(String input) {
		if (input.matches(UserConstants.EMAIL_REGEX)) {
			return false;
		}
		return true;
	}
}
