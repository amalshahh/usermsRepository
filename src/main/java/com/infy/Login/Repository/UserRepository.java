package com.infy.Login.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infy.Login.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	public List<UserEntity> findByfirstName(String search);

	public Optional<UserEntity> findByemailId(String emailId);
	

}
