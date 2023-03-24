package com.finnplay.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finnplay.demo.app.aop.AppException;
import com.finnplay.demo.app.entity.AppUser;
import com.finnplay.demo.app.entity.AppUserValidator;
import com.finnplay.demo.app.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AppUserController {

    @Autowired
    AppUserService userService;

    @Autowired
    private AppUserValidator personValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(personValidator);
    }

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/signup")
    String signUp(Model model) {
        model.addAttribute("formData", new AppUser());
        return "signup";
    }

    @RequestMapping("/signupsuccess")
    String successForward() {
        return "success";
    }

    @RequestMapping("/userInfo")
    public String userInfo(HttpServletRequest httpRequest, Model model) {
        String email = httpRequest.getUserPrincipal().getName();
        AppUser availableUser = userService.fetchUserDetailsForEmail(email);
        if (availableUser != null) {
            model.addAttribute("formData", availableUser);
            return "userInfo";
        } else {
            model.addAttribute("formData", new AppUser());
            return "index";
        }
    }

    @PostMapping("/register")
    String register(@ModelAttribute("formData") @Validated AppUser appUser, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "signup";
        }
        try {
            AppUser persisted = userService.saveUser(appUser);
            return "success";

        } catch (AppException appException) {
            model.addAttribute("errorMessage", appException.getMessage());
            return "signup";

        }

    }

    @PostMapping("/updateUserInfo")
    String updateUserInfo(@ModelAttribute("formData") AppUser appUser, BindingResult result, ModelMap model)
            throws AppException {
        AppUser persisted = userService.updateUser(appUser);
        model.addAttribute("message", "Details Updated");
        return "userinfo";
    }

}
