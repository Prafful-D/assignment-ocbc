package com.ocbc.assignment.config;

import com.ocbc.assignment.dto.ApplicationResponse;
import com.ocbc.assignment.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author PraffulD
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse exception(Exception ex) {

        ApplicationResponse baseResponse = new ApplicationResponse(null);
        if (ex instanceof ApplicationException) {
            ApplicationException exception = (ApplicationException) ex;
            baseResponse.setResponseCode(exception.getErrorCode());
            baseResponse.setMessage(exception.getErrorMessage());
        } else {
            baseResponse.setResponseCode("400");
            baseResponse.setMessage("Failed");
        }

        return baseResponse;
    }
}
