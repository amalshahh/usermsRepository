package com.infy.Login.RequestModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {

	private String email;
	private String otp;
	private String newpassword;

}
