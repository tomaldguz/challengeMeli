package com.challenge.exception;

import com.challenge.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.Collection;

public class ExceptionResponse {

    @JsonView(View.Exception.class)
    private Collection<ServiceException> errors;

    public ExceptionResponse(){
        errors = new ArrayList<ServiceException>();
    }

    public void add(ServiceException ex){
        errors.add(ex);
    }

    public Collection<ServiceException> getErrors() {
        return errors;
    }



}
