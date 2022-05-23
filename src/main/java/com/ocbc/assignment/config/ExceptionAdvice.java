package com.ocbc.assignment.config;

import com.ocbc.assignment.dto.ApplicationResponse;
import com.ocbc.assignment.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author PraffulD
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse exception(Exception ex) {

        LOGGER.error("Error in processing request {}", ex.getCause());
        LOGGER.error("Exception {}", ex);

        ApplicationResponse baseResponse = new ApplicationResponse(null);
        if (ex instanceof ApplicationException) {
            ApplicationException exception = (ApplicationException) ex;
            baseResponse.setResponseCode(exception.getErrorCode());
            baseResponse.setMessage(exception.getErrorMessage());
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            baseResponse.setResponseCode("400");
            baseResponse.setMessage(exception.getFieldError().getDefaultMessage());

        } else {
            baseResponse.setResponseCode("500");
            baseResponse.setMessage("FAILED");
        }

        return baseResponse;
    }
}
