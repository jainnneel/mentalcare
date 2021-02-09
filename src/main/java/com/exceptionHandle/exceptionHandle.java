package com.exceptionHandle;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class exceptionHandle {
	
	@ExceptionHandler(value = ExceptionOccured.class)
	public String runtime(ExceptionOccured e,Model m) {
		m.addAttribute("message",e.getMessage());
		return "errorpage";
	}
}
