package com.freedomPass.project.errorhandling;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class HandleGlobalException {

    @Autowired
    Logger logger;

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleCustomException(Exception ex) {
        Logger.ERROR(HandleGlobalException.class.getName() + ".handleCustomException" + "==>" + ex.getMessage(), "", "");
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED)
                .setHttpResponseEntityResultDescription("An error occured. Contact your service provider for help")
                .addHttpResponseEntityData("error", ex.getMessage())
                .returnClientResponse();
    }

    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity handleHibernateException(CannotCreateTransactionException ex) {
        Logger.ERROR(HandleGlobalException.class.getName() + ".handleHibernateException" + "==>" + ex.getMessage(), "", "");
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED)
                .setHttpResponseEntityResultDescription("Connection to Data Resources is lost! Contact your service provider for help.")
                .addHttpResponseEntityData("error", ex.getMessage())
                .returnClientResponse();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleError404(HttpServletRequest request, Exception e) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.NOT_FOUND)
                .setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED)
                .setHttpResponseEntityResultDescription("Resource not found. Check your enquiry details again or contact your service provider for help.")
                .addHttpResponseEntityData("error", e.getMessage())
                .returnClientResponse();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //@ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity handleUnrecognizedPropertyException(HttpServletRequest request, Exception e) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED)
                .setHttpResponseEntityResultDescription("You might have submitted invalid input. Check your enquiry details again or contact your service provider for help.")
                .addHttpResponseEntityData("error", e.getMessage())
                .returnClientResponse();
    }

}
