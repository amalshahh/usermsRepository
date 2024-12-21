package com.infy.Login.APIs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.infy.Login.Exceptions.AuthorizationException;
import com.infy.Login.RequestModel.UserDTO;
import com.infy.Login.RequestModel.UserUpdateRequest;
import com.infy.Login.Service.UserService;
import com.infy.Login.Utilities.CommonConstantMethods;
import com.infy.Login.Utilities.UserConstants;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("userms/user/")
@Validated
@CrossOrigin({ "http://localhost:3000", UserConstants.UI_PORT_ADDRESS , "http://localhost:8800"})
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	CommonConstantMethods commonmethods;

	@Operation(summary = "Load user by email or userId", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("loadUser/")
	public ResponseEntity<UserDTO> loadUser(@RequestHeader(name = "Authorization", required = true) String auth,
			@RequestParam("identity") String userInput) throws Exception {
		if (userInput.isBlank() || userInput.isEmpty()) {
			throw new Exception(UserConstants.EMPTY_EMAIL_ADDRESS);
		}
		if (!commonmethods.authTokenValidator(auth, userInput)) {
			throw new AuthorizationException(UserConstants.AUTHENTICATION_FAILED);
		}
		return new ResponseEntity<>(userService.loadUser(userInput), HttpStatus.OK);

	}

	@Operation(summary = "Update user by email or userId", security = @SecurityRequirement(name = "bearerAuth"))
	@PutMapping("updateuser")
	public ResponseEntity<?> updateUser(@RequestHeader(name = "Authorization", required = true) String auth,
			@RequestBody UserUpdateRequest userReq) throws Exception {

		if (!ObjectUtils.isEmpty(userReq)) {

			if (!commonmethods.authTokenValidator(auth, userReq.getEmailId())) {
				throw new AuthorizationException(UserConstants.AUTHENTICATION_FAILED);
			}
			return new ResponseEntity<>(userService.updateUserDetails(userReq), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(UserConstants.USER_UPDATE_FAILED, HttpStatus.BAD_REQUEST);

	}
	@Operation(summary = "Delete a  user by email or userId", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("deleteuser/{identity}")
	public ResponseEntity<?> deleteUser(@RequestHeader(name = "Authorization", required = true) String auth,
			String identity) throws Exception {
		if (identity.isBlank() || identity.isEmpty()) {
			throw new Exception(UserConstants.EMPTY_EMAIL_ADDRESS);
		}
		if (!commonmethods.authTokenValidator(auth, identity)) {
			throw new AuthorizationException(UserConstants.AUTHENTICATION_FAILED);
		}

		return new ResponseEntity<>(userService.deleteuser(identity), HttpStatus.CREATED);
	}

}
