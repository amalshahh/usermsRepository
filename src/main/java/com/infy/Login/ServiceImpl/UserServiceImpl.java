package com.infy.Login.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.infy.Login.Entity.UserEntity;
import com.infy.Login.Exceptions.AuthorizationException;
import com.infy.Login.Exceptions.UserConflictExeption;
import com.infy.Login.Exceptions.UserInactiveException;
import com.infy.Login.Exceptions.UserNotFoundException;
import com.infy.Login.Repository.UserRepository;
import com.infy.Login.RequestModel.*;
import com.infy.Login.ResponseModel.LoginResponseModel;
import com.infy.Login.ResponseModel.UserMessageResponseModel;
import com.infy.Login.ResponseModel.UserResponseModel;
import com.infy.Login.Service.UserService;
import com.infy.Login.Utilities.CommonConstantMethods;
import com.infy.Login.Utilities.JwtUtil;
import com.infy.Login.Utilities.UserConstants;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public UserResponseModel UserRegistration(SignUpDTO signUpDTO) throws Exception {
		Optional<UserEntity> userDataOptl = userRepo.findByemailId(signUpDTO.getEmailId());
		if (userDataOptl.isPresent()) {
			throw new UserConflictExeption(String.format(UserConstants.EMAIL_EXISTS, signUpDTO.getEmailId()));
		}
		securityEncoder(signUpDTO);
		UserEntity userModel = modelMapper.map(signUpDTO, UserEntity.class);
		if (userModel == null) {
			throw new Exception(UserConstants.MODEL_MAPPING_FAILED);

		}
		String userId = signUpDTO.getEmailId().split("@")[0];
		userModel.setUserId(userId);
		userModel.setUserStatus(UserConstants.ACTIVE);
		return modelMapper.map(userRepo.save(userModel), UserResponseModel.class);

	}

	public void securityEncoder(SignUpDTO signUpDTO) {
		signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
	}

	@Override
	public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
		
		Optional<UserEntity> userDataOpt = CommonConstantMethods.userIdExtractor(identity)
				? userRepo.findById(identity)
				: userRepo.findByemailId(identity);	
		
		UserEntity userData = userDataOpt
				.orElseThrow(() -> new UsernameNotFoundException(UserConstants.USER_NOT_FOUND));
		return new org.springframework.security.core.userdetails.User(userData.getEmailId(), userData.getPassword(),
				new ArrayList<>());
	}

	@Override
	public UserDTO loadUser(String userInput) throws UserNotFoundException, UserInactiveException {
		Optional<UserEntity> userOptional = CommonConstantMethods.userIdExtractor(userInput)
				? userRepo.findById(userInput)
				: userRepo.findByemailId(userInput);
		return userOptional.map(user -> modelMapper.map(user, UserDTO.class))
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

	}

	@Override
	public LoginResponseModel UserLogin(AuthRequest authRequest) throws Exception {
		
		Optional<UserEntity> userOptional = CommonConstantMethods.userIdExtractor(authRequest.getIdentity())
				? userRepo.findById(authRequest.getIdentity())
				: userRepo.findByemailId(authRequest.getIdentity());

		UserEntity foundUser = userOptional.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		if (passwordEncoder.matches(authRequest.getPassword(), foundUser.getPassword())) {
			String authToken = jwtUtil.generateToken(foundUser.getEmailId());
			return LoginResponseModel.builder().responseCode(HttpStatus.OK.toString()).authToken(authToken).build();
		}
		throw new AuthorizationException(UserConstants.UNAUTHORIZED_);

	}

	@Override
	public UserResponseModel updateUserDetails(UserUpdateRequest userupdate) throws Exception {
		Optional<UserEntity> userOptional = userRepo.findByemailId(userupdate.getEmailId());
		UserEntity userDetails = userOptional
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		if (!userDetails.getUserStatus().equalsIgnoreCase(UserConstants.ACTIVE)) {
			throw new UserInactiveException(String.format(UserConstants.USER_INACTIVE, userDetails.getEmailId()));
		}
		UserEntity UpdatedUser = updateEnabler(userupdate, userDetails);
		return modelMapper.map(userRepo.save(UpdatedUser), UserResponseModel.class);

	}

	public UserEntity updateEnabler(UserUpdateRequest userRequest, UserEntity userFromDB) {

		if (!ObjectUtils.isEmpty(userRequest.getFirstName())) {
			userFromDB.setFirstName(userRequest.getFirstName());
		}
		if (!ObjectUtils.isEmpty(userRequest.getLastName())) {
			userFromDB.setLastName(userRequest.getLastName());
		}
		if (!ObjectUtils.isEmpty(userRequest.getPhoneNo())) {
			userFromDB.setPhoneNo(userRequest.getPhoneNo());
		}

		if (!ObjectUtils.isEmpty(userRequest.getJobRole())) {
			userFromDB.setJobRole(userRequest.getJobRole());
		}

		if (!ObjectUtils.isEmpty(userRequest.getUserType())) {
			userFromDB.setJobRole(userRequest.getUserType());
		}

		userFromDB.setUpdatedAt(LocalDateTime.now());

		return userFromDB;

	}

	@Override
	public UserMessageResponseModel deleteuser(String emailId) throws Exception {
		UserMessageResponseModel response = new UserMessageResponseModel();
		userRepo.findByemailId(emailId).ifPresentOrElse((user) -> {
			user.setUserStatus(UserConstants.INACTIVE);
			userRepo.save(user);
			response.setResponseCode(HttpStatus.CREATED.toString());
			response.setResponseMessage(UserConstants.USER_DELETED_SUCCESS);

		}, () -> {
			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		});
		return response;
	}

}
