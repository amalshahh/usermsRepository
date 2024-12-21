package com.infy.Login.Utilities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infy.Login.Exceptions.AuthorizationException;
import com.infy.Login.Exceptions.LoginException;
import com.infy.Login.Exceptions.UserConflictExeption;
import com.infy.Login.Exceptions.UserInactiveException;
import com.infy.Login.Exceptions.UserNotFoundException;
import com.infy.Login.Exceptions.UserPasswordException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorInfo> infyInternExceptionHandler(LoginException exception) {
		LOGGER.error(exception.getMessage(), exception);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(exception.getMessage());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(exception.getMessage());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(AuthorizationException exception) {
		LOGGER.error(exception.getMessage(), exception);
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		errorInfo.setErrorMessage(exception.getMessage());
		return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ UserConflictExeption.class, UserNotFoundException.class, UserInactiveException.class,
			ConstraintViolationException.class, UserPasswordException.class, })
	ResponseEntity<ErrorInfo> customRuntimeExceptionHandler(RuntimeException ex) {
		LOGGER.error(ex.getMessage(), ex);
		ErrorInfo errorInfo = new ErrorInfo();

		if (ex instanceof UserConflictExeption) {
			errorInfo.setErrorCode(HttpStatus.CONFLICT.value());
			errorInfo.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);

		} else if (ex instanceof UserNotFoundException) {
			errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
			errorInfo.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);

		} else if (ex instanceof UserInactiveException) {
			errorInfo.setErrorCode(HttpStatus.FORBIDDEN.value());
			errorInfo.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);

		} else if (ex instanceof ConstraintViolationException) {
			errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
			errorInfo.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
		} else if (ex instanceof UserPasswordException) {
			errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
			errorInfo.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
		} else {
			errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorInfo.setErrorMessage( ex.getMessage());
			return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

		Map<String, Object> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("errorMessage", "Validation failed");
		errorDetails.put("errorCode", HttpStatus.BAD_REQUEST.value());
		errorDetails.put("errors", errors);
		errorDetails.put("timeStamp", LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

}
