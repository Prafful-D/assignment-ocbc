package com.ocbc.assignment.repo;

import com.ocbc.assignment.enitity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author PraffulD
 */
@Repository
public interface UsersRepository extends JpaRepository<UserInfo, UUID> {

    UserInfo findByUserName(String userName);
}
