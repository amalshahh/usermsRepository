package com.infy.Login.APIs;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.infy.Login.Exceptions.AuthorizationException;
import com.infy.Login.RequestModel.AuthRequest;
import com.infy.Login.RequestModel.ForgotPasswordRequest;
import com.infy.Login.RequestModel.ResetPasswordRequest;
import com.infy.Login.RequestModel.SignUpDTO;
import com.infy.Login.ResponseModel.LoginResponseModel;
import com.infy.Login.ResponseModel.UserResponseModel;
import com.infy.Login.Service.AuthenticationService;
import com.infy.Login.ServiceImpl.UserServiceImpl;
import com.infy.Login.Utilities.CommonConstantMethods;
import com.infy.Login.Utilities.UserConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@Validated
@RequestMapping("/api/auth")
@CrossOrigin({ "http://localhost:3000", UserConstants.UI_PORT_ADDRESS })
public class AuthenticationController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	CommonConstantMethods commonmethods;

	@PostMapping("/login")
	@Operation(summary = "Login using email or userId", security = @SecurityRequirement(name = "bearerAuth"))
	public LoginResponseModel login(@RequestBody AuthRequest user) throws Exception {

		if (ObjectUtils.isEmpty(user)) {
			throw new Exception(UserConstants.REQUEST_PAYLOAD_EMPTY);
		}
		return userService.UserLogin(user);

	}

	@PostMapping(value = "/register")
	@Operation(summary = "Register a User", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signUpDTO) throws Exception {

		if (ObjectUtils.isEmpty(signUpDTO)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserConstants.REQUEST_PAYLOAD_EMPTY);
		}

		UserResponseModel userCreated = userService.UserRegistration(signUpDTO);
		if (ObjectUtils.isEmpty(userCreated)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(userCreated, HttpStatus.CREATED);

	}

	@GetMapping("/forgotpassword/generateOTP/{email}")
	@Operation(summary = "Forgot password: generate OTP", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> forgotPasswordOtpHandler(@PathVariable String email) throws Exception {
		return new ResponseEntity<>(authService.forgotPasswordStageOne(email), HttpStatus.OK);
	}

	@PatchMapping("/forgotpassword/validateOTP")
	@Operation(summary = "Forgot password: validate OTP", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> validateOtpHandler(@RequestBody ForgotPasswordRequest request) throws Exception {
		if (request != null) {
			return new ResponseEntity<>(authService.validateOtpAndChangePassword(request), HttpStatus.OK);
		}
		return new ResponseEntity<>("Payload is empty", HttpStatus.BAD_REQUEST);

	}

	@PatchMapping("/resetpassword")
	@Operation(summary = "Reset password", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> resetPasswordHandler(@RequestHeader(name = "Authorization", required = true) String auth,@RequestBody ResetPasswordRequest request) throws Exception {
		if (request == null) {
			return new ResponseEntity<>("payload is empty", HttpStatus.BAD_REQUEST);

		}
		if (!commonmethods.authTokenValidator(auth, request.getIdentity())) {
			throw new AuthorizationException(UserConstants.AUTHENTICATION_FAILED);
		}
		return new ResponseEntity<>(authService.resetPasswordHandler(request), HttpStatus.OK);

	}

}
