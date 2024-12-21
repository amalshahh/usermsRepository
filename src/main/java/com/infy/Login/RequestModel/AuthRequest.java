package com.infy.Login.RequestModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

	// @JsonProperty("emailId")
	// @NotBlank
	// @Email(message=UserConstants.Email_Address )
	// @Pattern(regexp="[A-Za-z0-9._%+-]+@[a-z0-9.-]+\\.(com|in|co.in)",message=
	// UserConstants.Email_Address )
	private String identity;
	
	

	// @NotBlank
	// @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message=
	// UserConstants.Password)
	private String password;

}
