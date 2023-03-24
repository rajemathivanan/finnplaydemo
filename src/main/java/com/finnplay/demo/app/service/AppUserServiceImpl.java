package com.finnplay.demo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.finnplay.demo.app.aop.AppException;
import com.finnplay.demo.app.dao.AppUserRepository;
import com.finnplay.demo.app.entity.AppUser;
import com.finnplay.demo.app.utils.AppUserUtils;

@Component
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public AppUser fetchUserDetailsForEmail(String email) {
        return appUserRepository.findUserByEmail(email);
    }

    @Override
    public AppUser saveUser(AppUser user) throws AppException {
        boolean isEmailExist = appUserRepository.existsUserByEmail(user.getEmail());
        user.setPassword(encodePassword(user.getPassword()));
        if (isEmailExist)
            throw new AppException("User Already Exist");

        return appUserRepository.save(user);
    }

    private String encodePassword(String originalPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        return encodedPassword;
    }

    @Override
    public AppUser updateUser(AppUser user) {
        String password = user.getPassword();
        if (password != null && StringUtils.hasText(password.trim())) {
            user.setPassword(encodePassword(user.getPassword()));
        }
        AppUser dbAppUser = this.findUserById(user.getUserId());
        AppUserUtils.copyNonNullProperties(user, dbAppUser);
        return appUserRepository.save(dbAppUser);
    }

    @Override
    public AppUser findUserById(Long id) {
        return appUserRepository.findById(id).get();
    }

}
