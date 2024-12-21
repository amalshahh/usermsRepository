package com.infy.Login.Service;

import com.infy.Login.Exceptions.UserNotFoundException;
import com.infy.Login.RequestModel.AuthRequest;
import com.infy.Login.RequestModel.SignUpDTO;
import com.infy.Login.RequestModel.UserDTO;
import com.infy.Login.RequestModel.UserUpdateRequest;
import com.infy.Login.ResponseModel.LoginResponseModel;
import com.infy.Login.ResponseModel.UserMessageResponseModel;
import com.infy.Login.ResponseModel.UserResponseModel;

public interface UserService {

	public UserResponseModel UserRegistration(SignUpDTO signUpDTO) throws Exception;

	public LoginResponseModel UserLogin(AuthRequest authRequest) throws Exception;

	public UserDTO loadUser(String userInput) throws UserNotFoundException, Exception;

	public UserResponseModel updateUserDetails(UserUpdateRequest userupdate) throws Exception;
	
	public UserMessageResponseModel deleteuser(String emailId)throws Exception;

}