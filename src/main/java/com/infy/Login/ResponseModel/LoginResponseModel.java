package com.infy.Login.ResponseModel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseModel {
	
	private String responseCode;
	private String authToken;
	
	
	@Builder
    public static LoginResponseModel create(String responseCode, String authToken) {
        return new LoginResponseModel(responseCode, authToken);
    }
}
