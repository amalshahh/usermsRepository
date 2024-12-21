package com.infy.Login.RequestModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.infy.Login.Utilities.UserConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

	
	private String identity;

	@NotBlank
	@Pattern(regexp = UserConstants.PASSWORD_REGEX, message = UserConstants.Password)
	private String oldPassword;

	@NotBlank
	@Pattern(regexp = UserConstants.PASSWORD_REGEX, message = UserConstants.Password)
	private String newPassword;

}
