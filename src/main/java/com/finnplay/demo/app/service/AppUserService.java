package com.finnplay.demo.app.service;

import com.finnplay.demo.app.aop.AppException;
import com.finnplay.demo.app.entity.AppUser;

public interface AppUserService {
    AppUser fetchUserDetailsForEmail(String email);

    AppUser saveUser(AppUser user) throws AppException;

    AppUser updateUser(AppUser user);

    AppUser findUserById(Long id);
}
