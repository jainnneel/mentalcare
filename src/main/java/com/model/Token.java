package com.model;


import java.sql.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String emailToken;
	
	private Date doc;
	
	@OneToOne
    private	Users u; 
	
	public Token() {
		super();
	}

	public Token(Users u) {
		super();
		this.u = u;
		this.emailToken = UUID.randomUUID().toString();
		this.doc = new Date(new java.util.Date().getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public Date getDoc() {
		return doc;
	}

	public void setDoc(Date doc) {
		this.doc = doc;
	}

	public Users getU() {
		return u;
	}

	public void setU(Users u) {
		this.u = u;
	}
	
	
}
