package com.exceptionHandle;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class exceptionHandle {
	
	@ExceptionHandler(value = RuntimeException.class)
	public String runtime(RuntimeException e,Model m) {
		System.out.println(e.getMessage());
		m.addAttribute("message",e.getMessage());
		return "errorpage";
	}
}
