package com.finnplay.demo.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finnplay.demo.app.entity.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    @Query("select u from AppUser u where u.email = ?1 and password =?2")
    AppUser findUserByEmailIdAndPassword(String email, String password);

    boolean existsUserByEmail(String email);

    AppUser findUserByEmail(String email);

}
