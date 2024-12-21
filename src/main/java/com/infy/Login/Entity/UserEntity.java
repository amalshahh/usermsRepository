package com.infy.Login.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class UserEntity {

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "first_name")
	@NotBlank
	private String firstName;

	@Column(name = "last_name")
	@NotBlank
	private String lastName;

	@Column(name = "job_role")
	@NotBlank
	private String jobRole;
	

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	
	@Column(name = "user_status")
	private String userStatus;
	
	@Column(name = "createdAt")
	private LocalDateTime createdAt = LocalDateTime.now() ;
	
	
	@Column(name = "updatedAt")
	private LocalDateTime updatedAt;

}
