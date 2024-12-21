package com.infy.Login.Service;

import java.util.List;

import com.infy.Login.ResponseModel.UserResponseModel;

public interface AdminService {
	
	
	
	

	public List<UserResponseModel> loadAllUsers(String userType) throws Exception;
//	public List<UserEntity> searchUser(String search);
//	public UserEntity findByEmail(String email);

	
		

}
