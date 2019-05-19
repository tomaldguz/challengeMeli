package com.challenge.exception;

import com.challenge.util.View;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;

import static com.challenge.exception.ServiceException.Code.UNKNOWN_ERROR;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, DAOException.class, ServiceException.class, JDBCConnectionException.class, NullPointerException.class, ConstraintViolationException.class, TransientObjectException.class, Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        String bodyOfResponse="";
        ExceptionResponse response = new ExceptionResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

        if (! (ex instanceof ServiceException)) {
            ex.printStackTrace();
            ex = new ServiceException(UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, "exception.title.unknown.error", "exception.detail.unknown.error");
        }

        ServiceException srvEx = (ServiceException) ex;
        srvEx.translate(messageSource, request.getLocale());
        response.add(srvEx);
        try {
            bodyOfResponse = mapper.writerWithView(View.Exception.class).writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return handleExceptionInternal(ex, bodyOfResponse,headers, srvEx.getHttpStatus(), request);
    }
}