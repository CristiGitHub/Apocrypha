package com.Apocrypha.Apocrypha.utils;

import org.springframework.http.HttpStatus;

public class SuccessDto {

    private final Integer statusCode = HttpStatus.OK.value();

    private final String statusMessage = HttpStatus.OK.getReasonPhrase();

    public SuccessDto() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
