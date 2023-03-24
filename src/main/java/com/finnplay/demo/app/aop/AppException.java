package com.finnplay.demo.app.aop;

public class AppException extends Exception{

    private String errorMessage;
    private String errorCode;
    public AppException(String arg0) {
        super(arg0);
    }

    public AppException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
   
}
