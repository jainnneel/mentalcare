package com.helper;

import com.exceptionHandle.ExceptionOccured;

public class EmailSend {
	
	
	public boolean sendEmail() {
	try {
		return true;
	} catch (Exception e) {
		throw new ExceptionOccured();
	}
	}
}
