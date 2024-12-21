package com.infy.Login.RequestModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.infy.Login.Utilities.UserConstants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class UserUpdateRequest {
	
	@Pattern(regexp = UserConstants.EMAIL_REGEX, message = UserConstants.Email_Address)
	@NotNull
	private String emailId;
	
	@Pattern(regexp = UserConstants.FIRST_NAME_REGEX, message = UserConstants.First_Name)
	private String firstName;

	@Pattern(regexp = UserConstants.LAST_NAME_REGEX, message = UserConstants.Last_Name)
	private String lastName;

//	@Pattern(regexp = UserConstants.JOB_ROLE_REGEX, message = UserConstants.JOB_ROLE_MESSAGE)
	private String jobRole;

	@NotNull
	@Pattern(regexp = UserConstants.USER_TYPE_REGEX, message = UserConstants.USER_TYPE_MSG)
	private String userType;

//	@Pattern(regexp = UserConstants.PHONE_NUMBER_REGEX, message = UserConstants.Phone_Number)
	private String phoneNo;

	
	private String securityQuestionOne;
	private String securityAnswerOne;

}
