package com.ocbc.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author PraffulD
 */
public class ApplicationResponse<T> {

    private String responseCode = "200";

    private String message = "SUCCESS";

    public ApplicationResponse(T t){
        this.response = t;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
