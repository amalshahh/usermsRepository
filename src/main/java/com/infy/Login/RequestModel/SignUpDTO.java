package com.infy.Login.RequestModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.infy.Login.Utilities.UserConstants;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpDTO {
	
	@Pattern(regexp = UserConstants.FIRST_NAME_REGEX, message = UserConstants.First_Name)
	@NotNull
	private String firstName;

	@Pattern(regexp = UserConstants.LAST_NAME_REGEX, message = UserConstants.Last_Name)
	@NotNull
	private String lastName;

//	@Pattern(regexp = UserConstants.JOB_ROLE_REGEX, message = UserConstants.JOB_ROLE_MESSAGE)
	@NotNull
	private String jobRole;

//	@Pattern(regexp = UserConstants.PHONE_NUMBER_REGEX, message = UserConstants.Phone_Number)
	private String phoneNo;

	@Pattern(regexp = UserConstants.EMAIL_REGEX, message = UserConstants.Email_Address)
	@NotNull
	private String emailId;

	@NotNull
	private String password;

	@Hidden
	private String userStatus;

}
