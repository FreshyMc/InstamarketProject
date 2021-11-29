package com.example.instamarket.web;

import com.example.instamarket.exception.OfferNotFoundException;
import com.example.instamarket.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, OfferNotFoundException.class})
    public ModelAndView showNotFoundPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("error/not-found");
        model.setStatus(HttpStatus.NOT_FOUND);

        return model;
    }
}
