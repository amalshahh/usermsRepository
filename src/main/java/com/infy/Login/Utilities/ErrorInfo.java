package com.infy.Login.Utilities;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorInfo {
	
	private String errorMessage;
	private Integer errorCode;
	private LocalDateTime timeStamp = LocalDateTime.now();

}