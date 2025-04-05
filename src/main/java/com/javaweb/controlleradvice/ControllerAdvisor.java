package com.javaweb.controlleradvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.beans.ErrorResponseBean;
import com.javaweb.customexceptions.InvalidDataException;

@ControllerAdvice  // Khi chuong trinh xay ra exception thi se nhay vao class co @ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex){
    	ErrorResponseBean errorResponseBean = new ErrorResponseBean();
    	errorResponseBean.setError(ex.getMessage());
    	List<String> details = new ArrayList<String>();
    	details.add("thieu data cua name hoac rentprice nhe");
    	errorResponseBean.setDetails(details);
    	return new ResponseEntity<Object>(errorResponseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> handleArray(InvalidDataException ex){
    	ErrorResponseBean errorResponseBean = new ErrorResponseBean();
    	errorResponseBean.setError(ex.getMessage());
    	List<String> details = new ArrayList<String>();
    	details.add("thieu data cua name");
    	errorResponseBean.setDetails(details);
    	return new ResponseEntity<>(errorResponseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
