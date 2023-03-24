package com.finnplay.demo.app.service;

import org.springframework.boot.test.context.SpringBootTest;

import com.finnplay.demo.app.aop.AppException;
import com.finnplay.demo.app.entity.AppUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class AppUserServiceTest {

    

    @Autowired
    AppUserService appUserService;

    @Test
	public void registerNewUser() throws Exception {
		AppUser appUser = new AppUser();
		appUser.setFirstName("Test");
		appUser.setLastName("Test");
		appUser.setEmail(Math.random()*100+"@test.com");
		appUser.setPassword("P@$$word123");
		appUser.setBirthDate(new Date(System.currentTimeMillis()));
        AppUser dbUser = appUserService.saveUser(appUser);
        assertNotEquals(0,dbUser.getUserId());
    }

    @Test
	public void registerNewUserWithSameEmail() throws Exception {
		AppUser appUser = new AppUser();
		appUser.setFirstName("Test");
		appUser.setLastName("Test");
		appUser.setEmail(Math.random()*100+"@test.com");
		appUser.setPassword("P@$$word123");
		appUser.setBirthDate(new Date(System.currentTimeMillis()));
        AppUser dbUser = appUserService.saveUser(appUser);
        assertThrows(AppException.class, ()-> { appUserService.saveUser(appUser);}, "User Already Exist");
    }

    @Test
	public void updateUser() throws Exception {
        AppUser appUser = new AppUser();
		appUser.setFirstName("Test");
		appUser.setLastName("Test");
        String emailID = Math.random()*100+"@test.com";
		appUser.setEmail(emailID);
		appUser.setPassword("P@$$word123");
		appUser.setBirthDate(new Date(System.currentTimeMillis()));
        AppUser dbUser = appUserService.saveUser(appUser);
		AppUser existingUser  = appUserService.fetchUserDetailsForEmail(emailID);
        if(existingUser.getUserId() != null) {
            existingUser.setFirstName("Testing");
            existingUser.setLastName("Testing");
            AppUser db2User = appUserService.updateUser(existingUser);
            assertEquals("Testing", db2User.getFirstName());
        } 
        
       
    }
}
