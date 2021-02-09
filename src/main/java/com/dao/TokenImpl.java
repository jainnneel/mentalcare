package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptionHandle.ExceptionOccured;
import com.model.Token;
import com.repository.tokenservice;

@Service
public class TokenImpl{
	
	@Autowired
	tokenservice ts;
	
	public void createToken(Token t) {
	try {
		ts.save(t);
	} catch (Exception e) {
		throw new ExceptionOccured();
	}
	}
	
	public Token findToken(String t) {
		try {
			return ts.findByemailToken(t);
		} catch (Exception e) {
			throw new ExceptionOccured();
		}
		
	}

	public void deletetoken(Token t) {
		try {
			ts.delete(t);
		} catch (Exception e) {
			throw new ExceptionOccured();
		}
		
	}

}
