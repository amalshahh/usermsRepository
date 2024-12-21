package com.infy.Login.APIs;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.Login.Exceptions.AuthorizationException;
import com.infy.Login.Service.AdminService;
import com.infy.Login.Service.UserService;
import com.infy.Login.Utilities.CommonConstantMethods;
import com.infy.Login.Utilities.UserConstants;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@RestController
@RequestMapping(UserConstants.PREFIX + "/admin")
@CrossOrigin({ "http://localhost:3000", UserConstants.UI_PORT_ADDRESS })
@OpenAPIDefinition(info = @Info(title = "USER MICROSERVICE APIS", version = "3.14"))

public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	AdminService adminUserService;

	@Autowired
	CommonConstantMethods commonmethods;

	@GetMapping("loadAllUsers")
	public List<?> loadAllUser(@RequestHeader(name = "Authorization", required = true) String auth,
			@RequestParam String email,
			@Valid @RequestParam(name = "userstatus", required = false) @Pattern(regexp = "active|inactive", message = UserConstants.USERTYPE_FORMAT_INCORRECT) String userStatus)
			throws Exception {
		if (!commonmethods.authTokenValidator(auth, email)) {
			throw new AuthorizationException(UserConstants.AUTHENTICATION_FAILED);
		}
		if (userStatus == null || (userStatus != null && userStatus.equalsIgnoreCase(UserConstants.ACTIVE)
				|| userStatus.equalsIgnoreCase(UserConstants.INACTIVE))) {
			return adminUserService.loadAllUsers(userStatus);
		}
		throw new Exception(UserConstants.USERTYPE_FORMAT_INCORRECT);
	}

}
