package com.infy.Login.Utilities;

public class UserConstants {

	public static final String UI_PORT_ADDRESS = "http://192.168.29.224:3000/";
	public static final String PREFIX = "userms";
	public static final String ESTIMO_EMAIL = "estimocare@gmail.com";
	public static final String EMAIL_SUBJECT = "Recovery OTP";

	public static final String User_Absent = "User_Not_Found_With_This_User_Id";
	public static final String UNAUTHORIZED_ = "Your Login failed,Please check your credentials";
	public static final String Password_Mismatch = "Please_Check_The_Password";
	public static final String Password_Change = "Password_Changed_Successfully";
	public static final String Answer_Mismatch = "Answer is incorrect";
	public static final String EmailId_Incorrect = "Your_Entered_emailId_is_Incorrect";
	public static final String Question_Mismatch = "You Selected the Incorrect Question";
	public static final String Last_Name = "last name must contain only letters";
	public static final String First_Name = " First name must contain only letters";
	public static final String Phone_Number = "Phone Number must be in valid form";
	public static final String Email_Address = "Enter a valid Email Address";
	public static final String EMPTY_EMAIL_ADDRESS = "Email Address filed is empty or Blank";

	public static final String Password = "Password must atleast 8-16 characters long and contain atleast one UpperCase, one lowerCase, one number and contains special charecters like @~#^!%*?&";
	public static final String Designation = "Please enter the correct Designation";
	public static final String EMAIL_EXISTS = "A user with email: '%s' already exists";
	public static final String No_Password = "Enter the Password";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String USER_NOT_EXIST = "user id not matches";
	public static final String USER_NOT_EXIST2 = "userName and id not matches";
	public static final String USER_VALUE = "User not Exist";
	public static final String REQUEST_PAYLOAD_EMPTY = "Request Payload IS EMPTY";
	public static final String USER_UPDATE_FAILED = "User Update Failed";

	public static final String USER_INACTIVE = "The User with email: %s is inactive";
	public static final String MODEL_MAPPING_FAILED = "Failed to map data from DTO to UserEntity";
	public static final String USER_DELETED_SUCCESS = "User removed successfully";
	public static final String USERTYPE_FORMAT_INCORRECT = "The usertype must be active or inactive";

	public static final String INVALID_OTP = "Invalid otp";
	public static final String EXPIRED_OTP = "Otp expired";
	public static final String OTP_SENT_SUCCESS = "Otp sent success, valid only upto 2 minutes";

	public static final String OTP_TEXT(String otpGenerated) {
		return "Your Otp for password recovery  is" + otpGenerated;

	}

	public static final String PASSWORD_RESET_SUCCESS = "Password changed successfully";
	public static final String AUTHENTICATION_FAILED = "You dont have the authorization";

	// UserType Constants:

	public static final String REVIEWER = "reviewer";
	public static final String ADMIN = "admin";
	public static final String ESTIMATOR = "estimator";

	public static final String REVIEWER_PREFIX = "stu";
	public static final String ADMIN_PREFIX = "admin";
	public static final String ESTIMATOR_PREFIX = "est";


	public static final String ACTIVE = "active";
	public static final String INACTIVE = "inactive";

	public static final String FIRST_NAME_REGEX = "^[a-zA-Z]{1,20}$";

	public static final String LAST_NAME_REGEX = "^[A-Za-z][A-Za-z ]{0,15}$";

//	public static final String JOB_ROLE_REGEX = "";

	public static final String JOB_ROLE_MESSAGE = "Please use a valid Job Role";

	public static final String USER_TYPE_REGEX = "(staffuser|superadmin)";

	public static final String USER_TYPE_MSG = "Please use a valid User Type";

	public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@infosys.com$";

	public static final String PHONE_NUMBER_REGEX = "^\\+\\d{1,3}\\d{10}$";

	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@~#^+$!%*?&])[A-Za-z\\d$@~#^!%*?&]{8,16}";

}
