package com.model;

import java.util.Map;

public class Response {
	
	private String status;
	private Map<String, String> errorMessages;
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Response(String status, Map<String, String> errorMessages) {
		super();
		this.status = status;
		this.errorMessages = errorMessages;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
	
	
}
