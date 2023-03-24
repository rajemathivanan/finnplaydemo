package com.finnplay.demo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.finnplay.demo.app.dao.AppUserRepository;
import com.finnplay.demo.app.entity.AppUser;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findUserByEmail(username);
        //TODO roles can be updated in table as well. For simplicity provided user role
        return User.builder().username(appUser.getEmail()).password(appUser.getPassword())
                .authorities("USER").build();
         
    }

}
