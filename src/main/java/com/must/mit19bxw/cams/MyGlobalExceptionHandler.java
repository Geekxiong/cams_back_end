package com.must.mit19bxw.cams;

import com.must.mit19bxw.cams.bean.ResponseBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseBean customException(Exception e) {
        ResponseBean response = ResponseBean.newResponse();
        response.isFail();
        response.setMsg(e.getMessage());
        return response;
    }
}