package com.cybersoft.demosecurity21.repository;

import com.cybersoft.demosecurity21.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository <UserEntity,Integer> {

    UserEntity findByEmail(String email);

}
