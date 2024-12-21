package com.infy.Login.Service;

import com.infy.Login.RequestModel.ForgotPasswordRequest;
import com.infy.Login.RequestModel.ResetPasswordRequest;
import com.infy.Login.ResponseModel.UserMessageResponseModel;

public interface AuthenticationService {
	
	
	public UserMessageResponseModel forgotPasswordStageOne(String email)throws Exception;
	public UserMessageResponseModel validateOtpAndChangePassword(ForgotPasswordRequest request) throws Exception;
	
	
	public UserMessageResponseModel resetPasswordHandler(ResetPasswordRequest request)throws Exception;

}
