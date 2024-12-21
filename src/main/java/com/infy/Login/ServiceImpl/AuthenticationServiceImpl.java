package com.infy.Login.ServiceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.Login.Exceptions.InvalidOtpException;
import com.infy.Login.Exceptions.UserNotFoundException;
import com.infy.Login.Exceptions.UserPasswordException;
import com.infy.Login.Repository.UserRepository;
import com.infy.Login.RequestModel.ForgotPasswordRequest;
import com.infy.Login.RequestModel.ResetPasswordRequest;
import com.infy.Login.ResponseModel.UserMessageResponseModel;
import com.infy.Login.Service.AuthenticationService;
import com.infy.Login.Utilities.CommonConstantMethods;
import com.infy.Login.Utilities.UserConstants;

@Service("authService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	BCryptPasswordEncoder passEncoder;

	public Map<String, String> otpStorage = new HashMap<>();
	public Map<String, LocalDateTime> otpTimestampStorage = new HashMap<>();

	@Override
	public UserMessageResponseModel forgotPasswordStageOne(String email) throws Exception {
		UserMessageResponseModel response = new UserMessageResponseModel();

		userRepo.findByemailId(email).ifPresentOrElse((user) -> {
			String generatedOtp = otpGenerator();
			otpStorage.put(email, passEncoder.encode(generatedOtp));
			otpTimestampStorage.put(email, LocalDateTime.now());
			sendOTPviaEmail(user.getEmailId(), generatedOtp);

			response.setResponseCode(HttpStatus.OK.toString());
			response.setResponseMessage(UserConstants.OTP_SENT_SUCCESS);
		}, () -> {
			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		});
		;

		return response;
	}

	public void sendOTPviaEmail(String email, String otpGenerated) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(UserConstants.ESTIMO_EMAIL);
		mailMessage.setTo(email);
		mailMessage.setSubject(UserConstants.EMAIL_SUBJECT);
		mailMessage.setText(UserConstants.OTP_TEXT(otpGenerated));
		mailSender.send(mailMessage);

	}

	public String otpGenerator() {
		Random random = new Random();
		int otp = 1000 + random.nextInt(9000);
		return String.valueOf(otp);
	}

	@Override
	public UserMessageResponseModel validateOtpAndChangePassword(ForgotPasswordRequest request) throws Exception {
		UserMessageResponseModel response = new UserMessageResponseModel();
		userRepo.findByemailId(request.getEmail()).ifPresentOrElse((user) -> {
			String storedOtp = otpStorage.get(user.getEmailId());
			LocalDateTime otpTimeStamp = otpTimestampStorage.get(user.getEmailId());
			if (storedOtp != null
					&& CommonConstantMethods.validatePassword(request.getOtp(), otpStorage.get(user.getEmailId()))) {
				if (otpTimeStamp != null && (!otpTimeStamp.plusMinutes(2).isAfter(LocalDateTime.now()))) {
					throw new InvalidOtpException(UserConstants.EXPIRED_OTP);
				}
				user.setPassword(passEncoder.encode(request.getNewpassword()));
				userRepo.save(user);
				otpStorage.remove(user.getEmailId());
				otpTimestampStorage.remove(user.getEmailId());
				response.setResponseCode(HttpStatus.OK.toString());
				response.setResponseMessage(UserConstants.Password_Change);

			} else {
				throw new InvalidOtpException(UserConstants.INVALID_OTP);
			}
		}, () -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		return response;
	}

	@Override
	public UserMessageResponseModel resetPasswordHandler(ResetPasswordRequest request) throws Exception {
		UserMessageResponseModel response = new UserMessageResponseModel();

		userRepo.findByemailId(request.getIdentity()).ifPresentOrElse((user) -> {
			if (!CommonConstantMethods.validatePassword(request.getOldPassword(), user.getPassword())) {
				throw new UserPasswordException(UserConstants.Password_Mismatch);
			}
			user.setPassword(passEncoder.encode(request.getNewPassword()));
			userRepo.save(user);
			response.setResponseCode(HttpStatus.OK.toString());
			response.setResponseMessage(UserConstants.PASSWORD_RESET_SUCCESS);

		}, () -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		return response;
	}

}
