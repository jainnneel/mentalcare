package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.exceptionHandle.ExceptionOccured;

@Service("emailSenderService")
public class EmailSender {
	
	 private JavaMailSender javaMailSender;

	  @Autowired
	  public EmailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	  }
	  
	  @Async
	  public void sendEmail(SimpleMailMessage email) {
		try {
			  javaMailSender.send(email);
		} catch (Exception e) {
			throw new ExceptionOccured();
		}
		
	  }
}
