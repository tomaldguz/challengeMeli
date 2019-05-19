package com.challenge.exception;

import com.challenge.util.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Locale;

public class ServiceException extends RuntimeException {

    public static final String TRANS_ARG = "TRANS_ARG:";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public enum Code{
        UNKNOWN_ERROR,
        DAOEXCEPTION,
        OBJECT_NOT_FOUND
    }

    @JsonView(View.Exception.class)
    private Code code;

    @JsonView(View.Exception.class)
    private String title;

    @JsonView(View.Exception.class)
    private String detail;

    @JsonView(View.Exception.class)
    private HttpStatus status;

    @JsonIgnore
    private Object[] args;

    public ServiceException(Code code, HttpStatus status, Throwable cause, String title, String detail, Object ... args) {
        super(detail, cause);
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.args = args;
    }

    public ServiceException(Code code, HttpStatus status, String title, String detail, Object ... args ) {
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.args = args;
    }

    public String getCode() {
        return Integer.toString(code.ordinal());
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {return detail;}

    public String getStatus() {return Integer.toString(status.value());}

    public Object[] getArgs() {
        return args;
    }

    public void translate(MessageSource messageSource, Locale locale) {
        title = messageSource.getMessage(title, null, locale );
        ArrayList<Object> translatedArgs = new ArrayList<>();
        for (Object o : args) {
            if ( o instanceof String) {
                String s = (String)o;
                if (s.startsWith(TRANS_ARG)) {
                    o = messageSource.getMessage(s.substring(TRANS_ARG.length()), null, locale);
                }
            }
            translatedArgs.add(o);
        }
        detail = messageSource.getMessage(detail, translatedArgs.toArray(), locale );
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() { return status;}

}
