package com.finnplay.demo.app.aop;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ModelAndView handleAppException(AppException appException) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", appException.getMessage());
        return mav;
    }


}
