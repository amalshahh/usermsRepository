package com.infy.Login.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infy.Login.Entity.UserEntity;
import com.infy.Login.Repository.UserRepository;
import com.infy.Login.ResponseModel.UserResponseModel;
import com.infy.Login.Service.AdminService;
import com.infy.Login.Utilities.UserConstants;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepo;

	private final ModelMapper modelMapper = new ModelMapper();

//	@Override
//	public List<UserEntity> searchUser(String search) {
//		List<UserEntity>searchList = new ArrayList<>();
//	    LoginEntity login= new LoginEntity();
//		UserEntity user = new UserEntity();
//		if(search.matches("^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,4}$")) {
//			login=loginrepo.findByemailId(search);
//
//			if(ObjectUtils.isEmpty(login)) {
//				
//		//	user= userRepo.findByloginId(login.getLoginId());
//				Optional<UserEntity>useroptional= userRepo.findById(login.getLoginId());
//				UserEntity uv = useroptional.get();
//				searchList.add(uv);
//			}
//		}else if(search.matches("^[A-Za-z]+$")) {
//			 if(!ObjectUtils.isEmpty(userRepo.findByfirstName(search))){
//				 searchList= userRepo.findByfirstName(search);
//			 }
//		}
//		return searchList;
//		
//		
//	}
//	
//	

	@Override
	public List<UserResponseModel> loadAllUsers(String userType) throws Exception {
		List<UserResponseModel> allUsersList = new ArrayList<>();
		userRepo.findAll().forEach(user -> {
			if (userType == null) {
				userAddingMethod(user, allUsersList);
			} else if (userType.equalsIgnoreCase(UserConstants.ACTIVE)
					&& user.getUserStatus().equalsIgnoreCase(UserConstants.ACTIVE)) {
				userAddingMethod(user, allUsersList);
			}else if (userType.equalsIgnoreCase(UserConstants.INACTIVE)
					&& user.getUserStatus().equalsIgnoreCase(UserConstants.INACTIVE)) {
				userAddingMethod(user, allUsersList);
			}
		});
		return allUsersList;
	}

	public void userAddingMethod(UserEntity user, List<UserResponseModel> allUsersList) {
		UserResponseModel userResponse = new UserResponseModel();
		userResponse = modelMapper.map(user, UserResponseModel.class);
		allUsersList.add(userResponse);
	}

}
